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

    @ModelAttribute("house")
    public House initHouse()
    {
        return new House();
    }
    @ModelAttribute("house_type")
    public Iterable<Type> initHouseTypeList()
    {
        return houseTypeService.findAll();
    }

    @GetMapping("/houses")
    public ModelAndView listHouses(){
        Iterable<House> houses;
        houses = houseService.findAll();
        ModelAndView modelAndView = new ModelAndView("/house/list");
        modelAndView.addObject("houses", houses);
        return modelAndView;
    }

    @PostMapping("/houses")
    public ModelAndView listHouses(@ModelAttribute("house") House house, @RequestParam("price_from") float price_from, @RequestParam("price_to") float price_to){
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

}
