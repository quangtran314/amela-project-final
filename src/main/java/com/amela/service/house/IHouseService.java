package com.amela.service.house;

import com.amela.model.house.House;
import com.amela.model.house.Type;
import com.amela.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface IHouseService extends IGeneralService<House> {

    Iterable<House> findAllByType(Type houseType);
    Page<House> findAll(Pageable pageable);

    Iterable<House> findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(String address, float price_from, float price_to);
    Iterable<House> findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqualAndType(String address, float price_from, float price_to, Type houseType);

    Page<House> findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(Pageable pageable, String address, float price_from, float price_to);
    Page<House> findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqualAndType(Pageable pageable, String address, float price_from, float price_to, Type houseType);
}
