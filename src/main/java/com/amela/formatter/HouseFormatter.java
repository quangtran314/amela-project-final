package com.amela.formatter;

import com.amela.model.house.House;
import com.amela.service.house.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class HouseFormatter implements Formatter<House> {

    private final IHouseService houseService;

    @Autowired
    public HouseFormatter(IHouseService houseService) {
        this.houseService = houseService;
    }

    @Override
    public House parse(String text, Locale locale) throws ParseException {
        Optional<House> houseOptional = houseService.findById(Long.parseLong(text));
        return houseOptional.orElse(null);
    }

    @Override
    public String print(House house, Locale locale) {
        return new StringBuilder().append("[").append(house.getHouse_name()).append("]").toString();
    }
}
