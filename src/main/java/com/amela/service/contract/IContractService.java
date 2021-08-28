package com.amela.service.contract;

import com.amela.model.Contract;
import com.amela.model.house.House;
import com.amela.model.user.User;
import com.amela.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface IContractService extends IGeneralService<Contract> {
    float getTotalPrice(float unitPrice, LocalDate startDay, LocalDate endDay);
    Page<Contract> findAllByUser(User user, Pageable pageable);
    long getDuration(LocalDate startDay, LocalDate endDay);
    Iterable<Contract> findAllByHouse(House house);
    Optional<Contract> findByIdAndUser(Long id, User user);
    boolean checkContractConflict(House house, LocalDate startDay, LocalDate endDay);
}
