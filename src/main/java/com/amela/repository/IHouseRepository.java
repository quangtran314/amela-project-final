package com.amela.repository;

import com.amela.model.house.House;
import com.amela.model.house.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IHouseRepository extends JpaRepository<House, Long> {

    Iterable<House> findAllByType(Type houseType);

    Iterable<House> findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(String address, float price_from, float price_to);
    Iterable<House> findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqualAndType(String address, float price_from, float price_to, Type type);
}
