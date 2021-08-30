package com.amela.repository;

import com.amela.model.Contract;
import com.amela.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface IConTractRepository extends JpaRepository<Contract,Long> {
    Page<Contract> findAllByUser(User user, Pageable pageable);
    Optional<Contract> findAllByEndDay(LocalDate date);
}
