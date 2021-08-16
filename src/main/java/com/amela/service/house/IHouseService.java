package com.amela.service.house;

import com.amela.model.house.House;
import com.amela.model.house.Type;
import com.amela.service.IGeneralService;

public interface IHouseService extends IGeneralService<House> {

    Iterable<House> findAllByType(Type houseType);
}
