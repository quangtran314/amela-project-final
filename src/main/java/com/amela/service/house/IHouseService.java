package com.amela.service.house;

import com.amela.model.house.House;
import com.amela.model.house.Type;
import com.amela.service.IGeneralService;
import org.springframework.data.repository.query.Param;

public interface IHouseService extends IGeneralService<House> {

    Iterable<House> findAllByType(Type houseType);

    Iterable<House> findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(String address, float price_from, float price_to);
    Iterable<House> findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqualAndType(String address, float price_from, float price_to, Type houseType);
}
