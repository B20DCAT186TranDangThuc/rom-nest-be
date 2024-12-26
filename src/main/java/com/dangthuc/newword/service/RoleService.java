package com.dangthuc.newword.service;

import com.dangthuc.newword.dto.request.RoleForm;
import com.dangthuc.newword.dto.response.ResultPaginationDTO;
import com.dangthuc.newword.entities.Permission;
import com.dangthuc.newword.entities.Role;
import com.dangthuc.newword.repository.PermissionRepository;
import com.dangthuc.newword.repository.RoleRepository;
import com.dangthuc.newword.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    public Role create(RoleForm form) {
        try {
            List<Permission> permissions = permissionRepository.findAllById(form.getPermissionIds());
            Role role = new Role();
            role.setName(form.getName());
            role.setActive(form.isActive());
            role.setDescription(form.getDescription());
            role.setPermissions(permissions);

            return roleRepository.save(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Role update(RoleForm form) {
        Role currentRole = fetchRoleById(form.getId());
        if (currentRole == null) {
            return null;
        }
        currentRole.setName(form.getName());
        currentRole.setDescription(form.getDescription());
        currentRole.setActive(form.isActive());
        currentRole.setPermissions(permissionRepository.findAllById(form.getPermissionIds()));

        return roleRepository.save(currentRole);
    }

    public Role fetchRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public ResultPaginationDTO fetchAllRoles(Specification<Role> spec, Pageable pageable) {
        return PaginationService.handlePagination(spec, pageable, roleRepository);
    }

    @Transactional
    public void deleteRoleById(Long id) {
        userRepository.removeRoleFromUsers(id);
        roleRepository.deleteById(id);
    }
}
