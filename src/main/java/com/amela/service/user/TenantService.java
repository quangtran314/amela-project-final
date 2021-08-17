package com.amela.service.user;

import com.amela.model.user.Tenant;
import com.amela.repository.ITenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TenantService implements ITenantService{

    @Autowired
    private ITenantRepository tenantRepository;

    @Override
    public Iterable<Tenant> findAll() {
        return tenantRepository.findAll();
    }

    @Override
    public Optional<Tenant> findById(Long id) {
        return tenantRepository.findById(id);
    }

    @Override
    public void save(Tenant tenant) {
        tenantRepository.save(tenant);
    }

    @Override
    public void remove(Long id) {
        tenantRepository.deleteById(id);
    }
}
