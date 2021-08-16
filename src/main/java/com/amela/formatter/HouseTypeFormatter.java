package com.amela.formatter;

import com.amela.model.house.Type;
import com.amela.service.house.IHouseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class HouseTypeFormatter implements Formatter<Type> {

    private final IHouseTypeService service;

    @Autowired
    public HouseTypeFormatter(IHouseTypeService service) {
        this.service = service;
    }

    @Override
    public Type parse(String text, Locale locale) throws ParseException {
        Optional<Type> typeOptional = service.findById(Long.parseLong(text));
        return typeOptional.orElse(null);
    }

    @Override
    public String print(Type houseType, Locale locale) {
        return new StringBuilder().append("[").append(houseType.getName()).append("]").toString();
    }
}
