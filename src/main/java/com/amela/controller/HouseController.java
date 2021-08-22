package com.amela.controller;

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
import com.amela.model.house.*;
import com.amela.service.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.IOException;
import java.util.List;
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

    @ModelAttribute("house")
    public House initHouse()
    {
        return new House();
    }

    private IContractService contractService;

    @Autowired
    private IImageService imageService;


    @ModelAttribute("user")
    public Iterable<User> initHouseTypeList()
    {
        return userService.findAll();
    }
    @ModelAttribute("houseTypes")
    public Iterable<Type> initUser()
    {
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
    public Iterable<Image> initHouseImages(){
        return imageService.findAll();
    }

    //List
    @GetMapping("/houses")
    public ModelAndView listHouses(){
        Iterable<House> houses = houseService.findAll();
        ModelAndView modelAndView = new ModelAndView("/house/list");
        modelAndView.addObject("houses", houses);
        modelAndView.addObject("house", new House());
        modelAndView.addObject("all_type", "0");
        return modelAndView;
    }

    @PostMapping("/houses")
    public ModelAndView listHouses(@ModelAttribute("house") House house, @RequestParam("price_from") Optional<Float> price_from, @RequestParam("price_to") Optional<Float> price_to){
        ModelAndView modelAndView = new ModelAndView("/house/list");
        Iterable<House> houseList =null;
        try{
            Optional<Type> house_type = houseTypeService.findById(house.getType().getType_id());
            houseList = houseService.findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqualAndType(house.getAddress(), price_from.isPresent()?price_from.get():0, price_to.isPresent()?price_to.get():99999999, house_type.get());
        }catch(NullPointerException e)
        {
            houseList = houseService.findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(house.getAddress(), price_from.isPresent()?price_from.get():0, price_to.isPresent()?price_to.get():5000);
        }
        modelAndView.addObject("houses", houseList);
        return modelAndView;
    }


    //Detail
    @GetMapping("/houses/{id}")
    public ModelAndView detailHouse(@PathVariable Long id){
        Optional<House> house = houseService.findById(id);
        Iterable<Image> images = imageService.findAllByHouse(house.get());
        Iterable<Feedback> feedbacks = feedbackService.findAllByHouse(house.get());
        ModelAndView modelAndView = new ModelAndView("/house/detail");
        modelAndView.addObject("feedbacks", feedbacks);
        modelAndView.addObject("house", house.get());
        modelAndView.addObject("images", images);
        return modelAndView;
    }

    //Create
    @GetMapping("/create-house")
    public ModelAndView showCreateHouse(){
        Optional<User> user = userService.findByEmail(getPrincipal());
        ModelAndView modelAndView = new ModelAndView("/house/create");
        modelAndView.addObject("houseForm", new HouseForm());
        modelAndView.addObject("user", user.get());
        return modelAndView;
    }

    @PostMapping("/create-house")
    public ModelAndView saveHouse(@Validated @ModelAttribute("houseForm") HouseForm houseForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
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
        House house = new House(houseForm.getHouse_id(),houseForm.getHouse_name(),houseForm.getAddress(),houseForm.getNumBedrooms(),
                houseForm.getNumBathrooms(),houseForm.getDes(),houseForm.getPrice(),houseForm.getType(), fileName, houseForm.getOwner());
        houseService.save(house);
        ModelAndView modelAndView = new ModelAndView("redirect:/houses");
        modelAndView.addObject("message", "New note created successfully");
        return modelAndView;
    }

    @GetMapping("/house/{id}/renting")
    public ModelAndView showRentingForm(@PathVariable("id") Long house_id){
        Optional<House> house = houseService.findById(house_id);
        if(house.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/house/rentingForm");
            modelAndView.addObject("house_id", house_id);
            modelAndView.addObject("contract_form", new ContractForm());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error/accessDenied");
            return modelAndView;
        }
    }

    @PostMapping("/house/{id}/renting")
    public ModelAndView renting(@Validated @ModelAttribute("contract_form") ContractForm contractForm, @PathVariable("id") Long house_id, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("/house/rentingForm");
            return modelAndView;
        }
        Optional<User> user = userService.findByEmail(getPrincipal());
        Optional<House> house = houseService.findById(house_id);
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
        return modelAndView;
    }

    @PostMapping("/house/{id}/renting-confirm")
    public ModelAndView rentingConfirm(@Validated @ModelAttribute("contract") Contract contract, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("/error/accessDenied");
            return modelAndView;
        }
        contractService.save(contract);
        ModelAndView modelAndView = new ModelAndView("redirect:/houses");
        return modelAndView;
    }

    //Upload image
    @GetMapping("/upload-image/{id}")
    public ModelAndView showImageForm(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/house/image");
        modelAndView.addObject("imageForm", new ImageForm());
        modelAndView.addObject("house_id", id);
        return modelAndView;
    }

    @PostMapping("/upload-image/{id}")
    public ModelAndView updateImage(@PathVariable Long id , @ModelAttribute ImageForm imageForm ) {
        MultipartFile multipartFile = imageForm.getSourcePath();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(imageForm.getSourcePath().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Image image = new Image(imageForm.getImage_id(),imageForm.getName(),
                fileName , imageForm.getDes() , imageForm.getHouse());
        imageService.save(image);
        ModelAndView modelAndView = new ModelAndView("/house/image");
        modelAndView.addObject("imageForm", imageForm);
        modelAndView.addObject("house_id", id);
        modelAndView.addObject("message", "Update successfully !");
        return modelAndView;
    }
}