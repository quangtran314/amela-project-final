package com.amela.repository;

import com.amela.model.house.House;
import com.amela.model.house.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHouseRepository extends JpaRepository<House, Long> {

    Iterable<House> findAllByType(Type houseType);
}
