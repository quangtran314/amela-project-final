package com.amela.controller;


import com.amela.model.house.House;
import com.amela.model.house.Type;
import com.amela.service.house.IHouseService;
import com.amela.service.house.IHouseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class HouseController {

    @Autowired
    private IHouseService houseService;

    @Autowired
    private IHouseTypeService houseTypeService;

    @ModelAttribute("houseTypes")
    public Iterable<Type> houseTypes(){
        Iterable<Type> types = houseTypeService.findAll();
        return types;
    }

    @GetMapping("/houses")
    public ModelAndView listHouses(){
        Iterable<House> houses;
        houses = houseService.findAll();
        ModelAndView modelAndView = new ModelAndView("/house/list");
        modelAndView.addObject("houses", houses);
        return modelAndView;
    }

    @GetMapping("/houses/{id}")
    public ModelAndView detailHouse(@PathVariable Long id){
        Optional<House> house = houseService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/house/detail");
        modelAndView.addObject("house", house.get());
        return modelAndView;
    }

    @GetMapping("/create-house")
    public ModelAndView showCreateHouse(){
        ModelAndView modelAndView = new ModelAndView("/house/create");
        modelAndView.addObject("house", new House());
        Iterable<Type> temp = houseTypes();
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
}
