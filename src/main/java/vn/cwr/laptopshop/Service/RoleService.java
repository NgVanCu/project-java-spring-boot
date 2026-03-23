package vn.cwr.laptopshop.service;

import vn.cwr.laptopshop.domain.Role;
import vn.cwr.laptopshop.dto.RoleDTO;

public interface RoleService {
    Role addToRole(RoleDTO roleDTO);

}
