package com.amela.controller;


import com.amela.model.Contract;
import com.amela.model.house.House;
import com.amela.model.house.Type;
import com.amela.model.user.User;
import com.amela.service.house.IHouseService;
import com.amela.service.house.IHouseTypeService;
import com.amela.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class HouseController {

    @Autowired
    private IHouseService houseService;

    @Autowired
    private IHouseTypeService houseTypeService;

    @Autowired
    private IUserService userService;

    @ModelAttribute("house")
    public House initHouse()
    {
        return new House();
    }

    @ModelAttribute("houseTypes")
    public Iterable<Type> initHouseTypeList()
    {
        return houseTypeService.findAll();
    }

//    @ModelAttribute("user")
//    public User user(){
//        Optional<User> user = userService.findByEmail(getPrincipal());
//        return user.get();
//    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

//List
    @GetMapping("/houses")
    public ModelAndView listHouses(){
        Iterable<House> houses = houseService.findAll();
        ModelAndView modelAndView = new ModelAndView("/house/list");
        modelAndView.addObject("houses", houses);
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
        ModelAndView modelAndView = new ModelAndView("/house/detail");
        modelAndView.addObject("house", house.get());
        return modelAndView;
    }

//Create
    @GetMapping("/create-house")
    public ModelAndView showCreateHouse(){
        ModelAndView modelAndView = new ModelAndView("/house/create");
        modelAndView.addObject("house", new House());
        return modelAndView;
    }

    @PostMapping("/create-house")
    public ModelAndView saveHouse(@Validated @ModelAttribute("house") House house, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("/house/create");
            return modelAndView;
        }
        houseService.save(house);
        ModelAndView modelAndView = new ModelAndView("redirect:/houses");
        modelAndView.addObject("message", "New note created successfully");
        return modelAndView;
    }

    @GetMapping("/renting")
    public ModelAndView renting(){
        ModelAndView modelAndView = new ModelAndView("");
        return modelAndView;
    }
}
