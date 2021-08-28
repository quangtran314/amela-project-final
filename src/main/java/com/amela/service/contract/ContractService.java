package com.amela.service.contract;

import com.amela.model.Contract;
import com.amela.model.house.House;
import com.amela.model.user.User;
import com.amela.repository.IContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
@Service
public class ContractService implements IContractService{

    @Autowired
    private IContractRepository contractRepository;

    @Override
    public Iterable<Contract> findAll() {
        return contractRepository.findAll();
    }

    @Override
    public Optional<Contract> findById(Long id) {
        return contractRepository.findById(id);
    }

    @Override
    public void save(Contract contract) {
        contractRepository.save(contract);
    }

    @Override
    public void remove(Long id) {
        contractRepository.deleteById(id);
    }

    @Override
    public long getDuration(LocalDate startDay, LocalDate endDay){
        long duration = startDay.until(endDay, ChronoUnit.DAYS);
        return duration;
    }

    @Override
    public Iterable<Contract> findAllByHouse(House house) {
        return contractRepository.findAllByHouse(house);
    }

    @Override
    public Optional<Contract> findByIdAndUser(Long id, User user) {
        return contractRepository.findByIdAndUser(id, user);
    }

    @Override
    public boolean checkContractConflict(House house, LocalDate startDay, LocalDate endDay) {
        Iterable<Contract> rentedContracts = findAllByHouse(house);
        for(Contract eachContract : rentedContracts){
            if((startDay.compareTo(eachContract.getStartDay()) >= 0 && startDay.compareTo(eachContract.getEndDay()) <= 0) ||
                    (endDay.compareTo(eachContract.getStartDay()) >= 0 && endDay.compareTo(eachContract.getEndDay()) <= 0) ||
                    (startDay.compareTo(eachContract.getStartDay()) <= 0 && endDay.compareTo(eachContract.getEndDay()) >= 0) ||
                    (startDay.compareTo(LocalDate.now()) < 0)){
                return false;
            }
        }
        return true;
    }

    @Override
    public float getTotalPrice(float unitPrice, LocalDate startDay, LocalDate endDay) {
        return unitPrice * getDuration(startDay, endDay);
    }
}
