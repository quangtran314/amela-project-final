package com.amela.repository;

import com.amela.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConTractRepository extends JpaRepository<Contract,Long> {
}
