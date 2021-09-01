package com.amela.service.house;

import com.amela.model.house.House;
import com.amela.model.house.Type;
import com.amela.model.user.User;
import com.amela.repository.IHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public Page<House> findAll(Pageable pageable) {
        return houseRepository.findAll(pageable);
    }

    @Override
    public Page<House> findAllByOwner(Pageable pageable, User user) {
        return houseRepository.findAllByOwner(pageable,user);
    }

    @Override
    public Page<House> findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(Pageable pageable, String address, float price_from, float price_to) {
        return houseRepository.findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(pageable, address, price_from, price_to);
    }

    @Override
    public Page<House> findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqualAndType(Pageable pageable, String address, float price_from, float price_to, Type houseType) {
        return houseRepository.findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqualAndType(pageable, address, price_from, price_to, houseType);
    }


}
