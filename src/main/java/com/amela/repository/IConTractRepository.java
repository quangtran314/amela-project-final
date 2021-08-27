package com.amela.repository;

import com.amela.model.Contract;
import com.amela.model.house.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConTractRepository extends JpaRepository<Contract,Long> {
    Iterable<Contract> findAllByHouse(House house);
}
