package com.amela.service.house;

import com.amela.model.house.Type;
import com.amela.repository.IHouseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HouseTypeService implements IHouseTypeService {

    private final IHouseTypeRepository houseTypeRepository;

    @Autowired
    public HouseTypeService(IHouseTypeRepository houseTypeRepository) {
        this.houseTypeRepository = houseTypeRepository;
    }

    @Override
    public Iterable<Type> findAll() {
        return houseTypeRepository.findAll();
    }

    @Override
    public Optional<Type> findById(Long id) {
        return houseTypeRepository.findById(id);
    }

    @Override
    public void save(Type type) {
        houseTypeRepository.save(type);
    }

    @Override
    public void remove(Long id) {
        houseTypeRepository.deleteById(id);
    }
}
