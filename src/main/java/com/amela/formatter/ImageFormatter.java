package com.amela.formatter;

import com.amela.model.Image;
import com.amela.service.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class ImageFormatter implements Formatter<Image> {

    private IImageService imageService;

    @Autowired
    public ImageFormatter(IImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public Image parse(String text, Locale locale) throws ParseException {
        Optional<Image> imageOptional = imageService.findById(Long.parseLong(text));
        return imageOptional.orElse(null);
    }

    @Override
    public String print(Image image, Locale locale) {
        return new StringBuilder().append("[").append(image.getName()).append("]").toString();
    }
}
