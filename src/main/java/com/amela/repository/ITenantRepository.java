package com.amela.repository;

import com.amela.model.user.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITenantRepository extends JpaRepository<Tenant,Long> {
}
