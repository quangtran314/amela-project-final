package com.amela.service.role;

import com.amela.model.user.Role;
import com.amela.service.IGeneralService;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(String name);
}
