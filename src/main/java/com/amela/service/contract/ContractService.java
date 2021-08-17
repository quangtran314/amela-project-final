package com.amela.service.contract;

import com.amela.model.Contract;
import com.amela.repository.IConTractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
