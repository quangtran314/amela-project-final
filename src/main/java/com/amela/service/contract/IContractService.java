package com.amela.service.contract;

import com.amela.model.Contract;
import com.amela.service.IGeneralService;

import java.time.LocalDate;

public interface IContractService extends IGeneralService<Contract> {
    float getTotalPrice(float unitPrice, LocalDate startDay, LocalDate endDay);
}
