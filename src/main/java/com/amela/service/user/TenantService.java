package com.amela.service.user;

import com.amela.model.user.User;
import com.amela.repository.ITenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TenantService implements ITenantService{

    @Autowired
    private ITenantRepository tenantRepository;

    @Override
    public Iterable<User> findAll() {
        return tenantRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return tenantRepository.findById(id);
    }

    @Override
    public void save(User user) {
        tenantRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        tenantRepository.deleteById(id);
    }
}
