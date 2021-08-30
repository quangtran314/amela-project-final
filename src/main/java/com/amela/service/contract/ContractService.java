package com.amela.service.contract;

import com.amela.model.Contract;
import com.amela.model.user.User;
import com.amela.repository.IConTractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
@Service
public class ContractService implements IContractService{

    @Autowired
    private IConTractRepository  conTractRepository;
    @Override
    public Iterable<Contract> findAll() {
        return conTractRepository.findAll();
    }

    @Override
    public Optional<Contract> findById(Long id) {
        return conTractRepository.findById(id);
    }

    @Override
    public void save(Contract contract) {
        conTractRepository.save(contract);
    }

    @Override
    public void remove(Long id) {
        conTractRepository.deleteById(id);
    }

    public long getDuration(LocalDate startDay, LocalDate endDay){
        long duration = startDay.until(endDay, ChronoUnit.DAYS);
        return duration;
    }


    @Override
    public float getTotalPrice(float unitPrice, LocalDate startDay, LocalDate endDay) {
        return unitPrice * getDuration(startDay, endDay);
    }

    @Override
    public Page<Contract> findAllByUser(User user, Pageable pageable) {
        return conTractRepository.findAllByUser(user,pageable);
    }

    @Override
    public Optional<Contract> findAllByEndDay(LocalDate date) {
        return conTractRepository.findAllByEndDay(date);
    }
}
