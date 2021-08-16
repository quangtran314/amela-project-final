package com.amela.service.house;

import com.amela.model.house.House;
import com.amela.model.house.Type;
import com.amela.repository.IHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HouseService implements IHouseService {

    private final IHouseRepository houseRepository;

    @Autowired
    public HouseService(IHouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public Iterable<House> findAll() {
        return houseRepository.findAll();
    }

    @Override
    public Optional<House> findById(Long id) {
        return houseRepository.findById(id);
    }

    @Override
    public void save(House house) {
        houseRepository.save(house);
    }

    @Override
    public void remove(Long id) {
        houseRepository.deleteById(id);
    }

    @Override
    public Iterable<House> findAllByType(Type houseType) {
        return houseRepository.findAllByType(houseType);
    }
}
