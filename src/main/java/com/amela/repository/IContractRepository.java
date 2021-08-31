package com.amela.repository;

import com.amela.model.Contract;
import com.amela.model.house.House;
import com.amela.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IContractRepository extends JpaRepository<Contract, Long> {

    Page<Contract> findAllByUser(User user, Pageable pageable);

    Page<Contract> findAll(Pageable pageable);

    Page<Contract> findAllByHouse(House house, Pageable pageable);

//    @Query("select new house_renting(c.startDay, c.endDay, c.totalPrice, h.house_name) from contract c join house h on c.house_id = h.house_id where h.house_name like concat('%', :houseName,'%')")
//    Page<Contract> findContractByHouseNameContaining(@Param(name = "houseName") String houseName, Pageable pageable);

    Optional<Contract> findByIdAndUser(Long id, User user);

    Iterable<Contract> findAllByHouse(House house);
}
