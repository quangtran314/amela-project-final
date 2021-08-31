package com.amela.service.contract;

import com.amela.model.Contract;
import com.amela.model.house.House;
import com.amela.model.user.User;
import com.amela.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IContractService extends IGeneralService<Contract> {

    float getTotalPrice(float unitPrice, LocalDate startDay, LocalDate endDay);

    Page<Contract> findAll(Pageable pageable);

    Page<Contract> findAllByUser(User user, Pageable pageable);

    Page<Contract> findAllByHouse(House house, Pageable pageable);

    Page<Contract> findContractByHouseNameContaining(String houseName, Pageable pageable);
}
