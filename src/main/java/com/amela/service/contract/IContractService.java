package com.amela.service.contract;

import com.amela.model.Contract;
import com.amela.model.house.House;
import com.amela.service.IGeneralService;

import java.time.LocalDate;

public interface IContractService extends IGeneralService<Contract> {
    float getTotalPrice(float unitPrice, LocalDate startDay, LocalDate endDay);
    long getDuration(LocalDate startDay, LocalDate endDay);
    Iterable<Contract> findAllByHouse(House house);
    boolean checkContractConflict(House house, LocalDate startDay, LocalDate endDay);
}
