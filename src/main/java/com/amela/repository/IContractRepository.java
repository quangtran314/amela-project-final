package com.amela.repository;

import com.amela.model.Contract;
import com.amela.model.house.House;
import com.amela.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface IContractRepository extends JpaRepository<Contract,Long> {
    Iterable<Contract> findAllByHouse(House house);
    Optional<Contract> findByIdAndUser(Long id, User user);
    Page<Contract> findAllByUser(User user, Pageable pageable);


}
