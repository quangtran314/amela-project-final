package com.amela.controller;

import com.amela.form.ContractForm;
import com.amela.model.Contract;
import com.amela.model.Feedback;
import com.amela.model.house.*;
import com.amela.model.user.User;
import com.amela.service.contract.IContractService;
import com.amela.service.feedback.IFeedbackService;
import com.amela.service.house.IHouseService;
import com.amela.service.house.IHouseTypeService;
import com.amela.service.image.IImageService;
import com.amela.service.user.IUserService;
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

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class HouseController {

    @Value("${file-upload}")
    private String fileUpload;

    private final IHouseService houseService;

    private final IHouseTypeService houseTypeService;

    private final IUserService userService;

    private final IFeedbackService feedbackService;

    public HouseController(IHouseService houseService, IHouseTypeService houseTypeService, IUserService userService, IFeedbackService feedbackService, IContractService contractService, IImageService imageService) {
        this.houseService = houseService;
        this.houseTypeService = houseTypeService;
        this.userService = userService;
        this.feedbackService = feedbackService;
        this.contractService = contractService;
        this.imageService = imageService;
    }

    @ModelAttribute("house")
    public House initHouse() {
        return new House();
    }

    private final IContractService contractService;

    private final IImageService imageService;

    @ModelAttribute("user")
    public Iterable<User> initHouseTypeList() {
        return userService.findAll();
    }

    @ModelAttribute("houseTypes")
    public Iterable<Type> initUser() {
        return houseTypeService.findAll();
    }

    private String getPrincipal() {
        String userName;
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

    @GetMapping("/")
    public String defaultPage() {
        return "redirect:/houses";
    }

    //List
    @GetMapping("/houses")
    public ModelAndView listHouses(@PageableDefault(value = 4) Pageable pageable,
                                   @RequestParam("address") Optional<String> address,
                                   @RequestParam("price_from") Optional<Float> price_from,
                                   @RequestParam("price_to") Optional<Float> price_to,
                                   @RequestParam("type") Optional<Long> type) {

        ModelAndView modelAndView = new ModelAndView("/house/list");
        modelAndView.addObject("all_type", "0");

        if (address.isEmpty() && type.isEmpty() && price_from.isEmpty() && price_to.isEmpty()) {
            Page<House> houses = houseService.findAll(pageable);
            modelAndView.addObject("houses", houses);
        } else {
            String address_val = address.orElse("");
            Long type_val = type.orElse(0L);
            float price_from_val = price_from.isPresent() ? price_from.get() : 0;
            float price_to_val = price_to.isPresent() ? price_to.get() : 99999999;

            Page<House> houseList;

            Optional<Type> house_type = houseTypeService.findById(type_val);
            if (house_type.isPresent()) {
                houseList = houseService.findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqualAndType(pageable, address_val, price_from_val, price_to_val, house_type.get());
                modelAndView.addObject("house_type", type_val);
            } else {
                houseList = houseService.findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(pageable, address_val, price_from_val, price_to_val);
                modelAndView.addObject("house_type", 0);
            }

            modelAndView.addObject("address", address_val);
            price_from.ifPresent(checkingPrice -> modelAndView.addObject("price_from", checkingPrice));
            price_to.ifPresent(checkingPrice -> modelAndView.addObject("price_to", checkingPrice));
            modelAndView.addObject("houses", houseList);
        }

        return modelAndView;
    }


    //Detail
    @GetMapping("/houses/{id}")
    public ModelAndView detailHouse(@PathVariable Long id) {
        Optional<House> house = houseService.findById(id);
        Iterable<Image> images = imageService.findAllByHouse(house.orElseThrow());
        Iterable<Feedback> feedbacks = feedbackService.findAllByHouse(house.get());

        ModelAndView modelAndView = new ModelAndView("/house/detail");
        modelAndView.addObject("feedbacks", feedbacks);
        modelAndView.addObject("house", house.get());
        modelAndView.addObject("images", images);
        modelAndView.addObject("feedback", new Feedback());

        return modelAndView;
    }

    @PostMapping("/houses/{id}")
    public ModelAndView saveFeedBack(@Validated @ModelAttribute("feedback") Feedback feedBack,
                                     BindingResult bindingResult,
                                     @PathVariable Long id, Principal principal,
                                     RedirectAttributes redirect) {
        ModelAndView modelAndView = new ModelAndView("/house/detail");
        Optional<House> house = houseService.findById(id);

        if (bindingResult.hasErrors()) {
            Iterable<Image> images = imageService.findAllByHouse(house.orElseThrow());
            Iterable<Feedback> feedbacks = feedbackService.findAllByHouse(house.orElseThrow());
            modelAndView.addObject("feedbacks", feedbacks);
            modelAndView.addObject("house", house.get());
            modelAndView.addObject("images", images);

            return modelAndView;
        }

        Optional<User> user = userService.findByEmail(principal.getName());
        feedBack.setOwner(user.orElseThrow());
        feedBack.setHouse(house.orElseThrow());
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
        ModelAndView modelAndView = new ModelAndView("/house/create");
        modelAndView.addObject("houseForm", new HouseForm());
        modelAndView.addObject("user", user.orElseThrow());

        return modelAndView;
    }

    @PostMapping("/create-house")
    public ModelAndView saveHouse(@Validated @ModelAttribute("houseForm") HouseForm houseForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/house/create");
        }

        MultipartFile multipartFile = houseForm.getSourcePath();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(houseForm.getSourcePath().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        House house = new House(houseForm.getHouse_id(), houseForm.getHouse_name(), houseForm.getAddress(), houseForm.getNumBedrooms(),
                houseForm.getNumBathrooms(), houseForm.getDes(), houseForm.getPrice(), houseForm.getType(), fileName, houseForm.getOwner());
        houseService.save(house);
        ModelAndView modelAndView = new ModelAndView("redirect:/houses");
        modelAndView.addObject("message", "New house created successfully");

        return modelAndView;
    }

    //Renting
    @GetMapping("/house/{id}/renting")
    public ModelAndView showRentingForm(@PathVariable("id") Long house_id) {
        Optional<House> house = houseService.findById(house_id);
        ModelAndView modelAndView;

        if (house.isPresent()) {
            modelAndView = new ModelAndView("/house/rentingForm");
            modelAndView.addObject("house_id", house_id);
            modelAndView.addObject("contract_form", new ContractForm());
        } else {
            modelAndView = new ModelAndView("/error/accessDenied");
        }

        return modelAndView;
    }

    @PostMapping("/house/{id}/renting")
    public ModelAndView renting(@Validated @ModelAttribute("contract_form") ContractForm contractForm,
                                @PathVariable("id") Long house_id,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/house/rentingForm");
        }

        Optional<User> user = userService.findByEmail(getPrincipal());
        Optional<House> house = houseService.findById(house_id);

        Contract contract = new Contract();
        contract.setStartDay(contractForm.getStartDay());
        contract.setEndDay(contractForm.getEndDay());
        contract.setDateContractSign(LocalDate.now());
        contract.setTotalPrice(contractService.getTotalPrice(house.orElseThrow().getPrice(), contractForm.getStartDay(), contractForm.getEndDay()));
        contract.setMaxPerson(contractForm.getMaxPerson());

        ModelAndView modelAndView = new ModelAndView("/house/renting-confirm");
        modelAndView.addObject("contract", contract);
        modelAndView.addObject("user", user.orElseThrow());
        modelAndView.addObject("house", house.orElseThrow());

        return modelAndView;
    }

    @PostMapping("/house/{id}/renting-confirm")
    public ModelAndView rentingConfirm(@Validated @ModelAttribute("contract") Contract contract,
                                       @PathVariable("id") long id,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/error/accessDenied");
        }

        contractService.save(contract);
        redirect.addFlashAttribute("message", "Thanks you for choosing our house to become the place you want to rest!");
        return new ModelAndView("redirect:/houses/" + id);
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
        String[] fileNames = new String[multipartFile.length];

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

        for (String fileName : fileNames) {
            Image image = new Image(imageForm.getImage_id(), imageForm.getName(),
                    fileName, imageForm.getDes(), imageForm.getHouse());
            imageService.save(image);
        }

        ModelAndView modelAndView = new ModelAndView("/house/image");
        modelAndView.addObject("imageForm", imageForm);
        modelAndView.addObject("house_id", id);
        modelAndView.addObject("message", "Update successfully !");
        return modelAndView;
    }
}
