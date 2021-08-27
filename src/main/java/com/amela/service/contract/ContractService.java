package com.amela.service.contract;

import com.amela.model.Contract;
import com.amela.model.house.House;
import com.amela.repository.IConTractRepository;
import com.amela.service.house.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @Override
    public long getDuration(LocalDate startDay, LocalDate endDay){
        long duration = startDay.until(endDay, ChronoUnit.DAYS);
        return duration;
    }

    @Override
    public Iterable<Contract> findAllByHouse(House house) {
        return conTractRepository.findAllByHouse(house);
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
