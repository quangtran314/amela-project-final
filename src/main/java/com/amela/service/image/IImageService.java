package com.amela.service.image;

import com.amela.model.house.House;
import com.amela.model.house.Image;
import com.amela.service.IGeneralService;

public interface IImageService extends IGeneralService<Image> {

    Iterable<Image> findAllByHouse(House house);
}
