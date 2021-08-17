package com.amela.repository;

import com.amela.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITenantRepository extends JpaRepository<User,Long> {
}
