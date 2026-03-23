package vn.cwr.laptopshop.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import vn.cwr.laptopshop.domain.Role;
import vn.cwr.laptopshop.dto.RoleDTO;
import vn.cwr.laptopshop.repository.RoleRepository;
import vn.cwr.laptopshop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role addToRole(RoleDTO roleDTO) {
        if (roleRepository.existsByName(roleDTO.getName())) {
            throw new RuntimeException("Role này đã tồn tại");
        }
        Role newRole = Role.builder().name(roleDTO.getName()).description(roleDTO.getDescription()).build();
        return roleRepository.save(newRole);
    }
}
