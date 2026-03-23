package vn.cwr.laptopshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.cwr.laptopshop.domain.Role;
import vn.cwr.laptopshop.dto.RoleDTO;
import vn.cwr.laptopshop.service.RoleService;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> addToRole(@Valid @RequestBody RoleDTO roleDTO) {
        Role newRole = roleService.addToRole(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRole);
    }
}
