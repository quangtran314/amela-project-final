package com.amela.controller;

import com.amela.exception.NotFoundException;
import com.amela.exception.UnauthorizedException;
import com.amela.form.HouseForm;
import com.amela.form.ImageForm;
import com.amela.model.Feedback;
import com.amela.model.house.*;
import com.amela.service.feedback.IFeedbackService;

import com.amela.form.ContractForm;
import com.amela.model.Contract;
import com.amela.model.house.House;
import com.amela.model.house.Type;
import com.amela.model.user.User;
import com.amela.service.contract.IContractService;

import com.amela.service.house.IHouseService;
import com.amela.service.house.IHouseTypeService;
import com.amela.service.user.IUserService;
import com.amela.service.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class HouseController {

    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IHouseService houseService;

    @Autowired
    private IHouseTypeService houseTypeService;

    @Autowired
    private IUserService userService;

    @Autowired

    private IFeedbackService feedbackService;

    @Autowired
    private IContractService contractService;

    @Autowired
    private IImageService imageService;


    @ModelAttribute("user")
    public Iterable<User> initHouseTypeList() {
        return userService.findAll();
    }

    @ModelAttribute("houseTypes")
    public Iterable<Type> initUser() {
        return houseTypeService.findAll();
    }

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @ModelAttribute("images")
    public Iterable<Image> initHouseImages() {
        return imageService.findAll();
    }

    //List
    @GetMapping("/houses")
    public ModelAndView listHouses(@PageableDefault(value = 4) Pageable pageable,
                                   @RequestParam("address") Optional<String> address,
                                   @RequestParam("price_from") Optional<Float> price_from,
                                   @RequestParam("price_to") Optional<Float> price_to,
                                   @RequestParam("type") Optional<Long> type
    ) {
        ModelAndView modelAndView = new ModelAndView("/house/list");
        modelAndView.addObject("all_type", "0");

        if (!address.isPresent() && !type.isPresent() && !price_from.isPresent() && !price_to.isPresent()) {
            Page<House> houses = houseService.findAll(pageable);
            modelAndView.addObject("houses", houses);
        } else {
            String address_val = address.orElse("");
            Long type_val = type.isPresent() ? type.get() : 0L;
            float price_from_val = price_from.isPresent() ? price_from.get() : 0;
            float price_to_val = price_to.isPresent() ? price_to.get() : 99999999;

            Page<House> houseList = null;

            Optional<Type> house_type = houseTypeService.findById(type_val);
            if (house_type.isPresent()) {
                houseList = houseService.findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqualAndType(pageable, address_val, price_from_val, price_to_val, house_type.get());
                modelAndView.addObject("house_type", type_val);
            } else {
                houseList = houseService.findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(pageable, address_val, price_from_val, price_to_val);
                modelAndView.addObject("house_type", 0);
            }

            modelAndView.addObject("address", address_val);
            if (price_from.isPresent())
                modelAndView.addObject("price_from", price_from.get());
            if (price_to.isPresent())
                modelAndView.addObject("price_to", price_to.get());
            modelAndView.addObject("houses", houseList);
        }
        return modelAndView;
    }


//Detail
    @GetMapping("/houses/{id}")
    public ModelAndView detailHouse(@PathVariable Long id) {
        Optional<House> house = houseService.findById(id);
        if(!house.isPresent()){
            throw new NotFoundException();
        }
        Iterable<Image> images = imageService.findAllByHouse(house.get());
        Iterable<Feedback> feedbacks = feedbackService.findAllByHouse(house.get());
        ModelAndView modelAndView = new ModelAndView("/house/detail");
        modelAndView.addObject("feedbacks", feedbacks);
        modelAndView.addObject("house", house.get());
        modelAndView.addObject("images", images);
        modelAndView.addObject("feedback", new Feedback());
        return modelAndView;
    }

    @PostMapping("/houses/{id}")
    public ModelAndView saveFeedBack(@Validated @ModelAttribute("feedback") Feedback feedBack, BindingResult bindingResult,
                                     @PathVariable Long id, Principal principal, RedirectAttributes redirect) {
        ModelAndView modelAndView = new ModelAndView("/house/detail");
        Optional<House> house = houseService.findById(id);
        if (bindingResult.hasErrors()) {
            Iterable<Image> images = imageService.findAllByHouse(house.get());
            Iterable<Feedback> feedbacks = feedbackService.findAllByHouse(house.get());
            modelAndView.addObject("feedbacks", feedbacks);
            modelAndView.addObject("house", house.get());
            modelAndView.addObject("images", images);
            return modelAndView;
        }
        Optional<User> user = userService.findByEmail(principal.getName());
        feedBack.setOwner(user.get());
        feedBack.setHouse(house.get());
        feedBack.setAmt_date(LocalDate.now());
        feedbackService.save(feedBack);
        redirect.addFlashAttribute("message", "Thank you your feed back!");
        modelAndView.setViewName("redirect:/houses/" + id);
        return modelAndView;
    }

//Create
    @GetMapping("/create-house")
    public ModelAndView showCreateHouse() {
        Optional<User> user = userService.findByEmail(getPrincipal());
        if(!user.isPresent()){
            throw new UnauthorizedException();
        }
        ModelAndView modelAndView = new ModelAndView("/house/create");
        modelAndView.addObject("houseForm", new HouseForm());
        modelAndView.addObject("user", user.get());
        return modelAndView;
    }

    @PostMapping("/create-house")
    public ModelAndView saveHouse(@Validated @ModelAttribute("houseForm") HouseForm houseForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/house/create");
            return modelAndView;
        }
        MultipartFile multipartFile = houseForm.getSourcePath();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(houseForm.getSourcePath().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        House house = new House(houseForm.getHouse_name(), houseForm.getAddress(), houseForm.getNumBedrooms(),
                houseForm.getNumBathrooms(), houseForm.getDes(), houseForm.getPrice(), houseForm.getType(), fileName, houseForm.getOwner(), houseForm.getCancelableTime());
        houseService.save(house);
        ModelAndView modelAndView = new ModelAndView("redirect:/houses");
        modelAndView.addObject("message", "New note created successfully");
        return modelAndView;
    }

//Renting
    @GetMapping("/house/{id}/renting")
    public ModelAndView showRentingForm(@PathVariable("id") Long houseId) {
        Optional<House> house = houseService.findById(houseId);
        if (house.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/house/rentingForm");
            modelAndView.addObject("house_id", houseId);
            modelAndView.addObject("contract_form", new ContractForm());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error/accessDenied");
            return modelAndView;
        }
    }

    @PostMapping("/house/{id}/renting")
    public ModelAndView renting(@Validated @ModelAttribute("contract_form") ContractForm contractForm,
                                @PathVariable("id") Long houseId,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/house/rentingForm");
            return modelAndView;
        }
        Optional<User> user = userService.findByEmail(getPrincipal());
        Optional<House> house = houseService.findById(houseId);
        if(contractService.checkContractConflict(house.get(), contractForm.getStartDay(), contractForm.getEndDay())){
            Contract contract = new Contract();
            contract.setStartDay(contractForm.getStartDay());
            contract.setEndDay(contractForm.getEndDay());
            contract.setDateContractSign(LocalDate.now());
            contract.setTotalPrice(contractService.getTotalPrice(house.get().getPrice(), contractForm.getStartDay(), contractForm.getEndDay()));
            contract.setMaxPerson(contractForm.getMaxPerson());
            ModelAndView modelAndView = new ModelAndView("/house/renting-confirm");
            modelAndView.addObject("contract", contract);
            modelAndView.addObject("user", user.get());
            modelAndView.addObject("house", house.get());
            modelAndView.addObject("message", "Renting success.");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/house/rentingForm");
            modelAndView.addObject("house_id", houseId);
            modelAndView.addObject("contract_form", new ContractForm());
            modelAndView.addObject("message", "This house is rented in this time.");
            return modelAndView;
        }
    }

    @PostMapping("/house/{id}/renting-confirm")
    public ModelAndView rentingConfirm(@Validated @ModelAttribute("contract") Contract contract, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/error/accessDenied");
            return modelAndView;
        }
        contractService.save(contract);
        ModelAndView modelAndView = new ModelAndView("redirect:/houses");
        return modelAndView;
    }

//Upload image
    @GetMapping("/upload-image/{id}")
    public ModelAndView showImageForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/house/image");
        modelAndView.addObject("imageForm", new ImageForm());
        modelAndView.addObject("house_id", id);
        return modelAndView;
    }

    @PostMapping("/upload-image/{id}")
    public ModelAndView updateImage(@PathVariable Long id, @ModelAttribute ImageForm imageForm) {
        MultipartFile[] multipartFile = imageForm.getSourcePath();
        String fileNames[] = new String[multipartFile.length];
        for (int i = 0; i < multipartFile.length; i++) {
            fileNames[i] = multipartFile[i].getOriginalFilename();
        }
        try {
            for (int i = 0; i < fileNames.length; i++) {
                FileCopyUtils.copy(imageForm.getSourcePath()[i].getBytes(), new File(fileUpload + fileNames[i]));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < fileNames.length; i++) {
            Image image = new Image(imageForm.getImage_id(), imageForm.getName(),
                    fileNames[i], imageForm.getDes(), imageForm.getHouse());
            imageService.save(image);
        }
        ModelAndView modelAndView = new ModelAndView("/house/image");
        modelAndView.addObject("imageForm", imageForm);
        modelAndView.addObject("house_id", id);
        modelAndView.addObject("message", "Update successfully !");
        return modelAndView;
    }

    @GetMapping("/rented-house/{id}")
    public ModelAndView showHouseRentedContract(@PathVariable("id") Long contract_id){
        Optional<Contract> contract = contractService.findById(contract_id);
        if(contract.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/house/rentedContract");
            modelAndView.addObject("contract", contract.get());
            return modelAndView;
        } else{
            throw new NotFoundException();
        }
    }

    @PostMapping("/rented-house/{id}/cancel")
    public ModelAndView cancelRentedHouse(@PathVariable("id") Long contract_id){
        Optional<Contract> contract = contractService.findById(contract_id);
        ModelAndView modelAndView = null;
        if(contract.isPresent()){
            LocalDate today = LocalDate.now();
            if(contractService.getDuration(today, contract.get().getStartDay()) > contract.get().getHouse().getHouse_id()){
                modelAndView = new ModelAndView("redirect:/houses");
                contractService.remove(contract_id);
            } else {
                modelAndView = new ModelAndView("/error/accessDenied");
            }
        } else{
            modelAndView = new ModelAndView("/error/accessDenied");
        }
        return modelAndView;
    }
}
