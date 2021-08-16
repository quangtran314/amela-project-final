package com.amela.controller;


import com.amela.model.house.House;
import com.amela.service.house.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class HouseController {

    @Autowired
    private IHouseService houseService;


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

}
