package com.dangthuc.newword.service;

import com.dangthuc.newword.dto.response.ResultPaginationDTO;
import com.dangthuc.newword.entities.Permission;
import com.dangthuc.newword.entities.Role;
import com.dangthuc.newword.exceptions.ExitsException;
import com.dangthuc.newword.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public Permission save(Permission permission) throws ExitsException {
        if (permission.getId() == null) {
            if (checkExistence(permission)) {
                throw new ExitsException("Permission already exists!");
            }
            return permissionRepository.save(permission);
        }

        // Lấy Permission trước đó từ DB
        Permission prePersistedPermission = permissionRepository.findById(permission.getId()).orElse(null);
        if (prePersistedPermission == null) {
            throw new ExitsException("Permission does not exist!");
        }

        // Kiểm tra logic cho update
        boolean isNameChanged = !permission.getName().equals(prePersistedPermission.getName());
        boolean isConflict = checkExistence(permission);

        if (isConflict && !isNameChanged) {
            throw new ExitsException("Permission with the same API path, method, and module already exists!");
        }

        // Cập nhật các thuộc tính
        prePersistedPermission.setName(permission.getName());
        prePersistedPermission.setApiPath(permission.getApiPath());
        prePersistedPermission.setMethod(permission.getMethod());
        prePersistedPermission.setModule(permission.getModule());

        return permissionRepository.save(prePersistedPermission);
    }

    public Permission fetchPermissionById(long id) {
        return permissionRepository.findById(id).orElse(null);

    }

    public boolean checkExistence(Permission permission) {
        return this.permissionRepository.existsByApiPathAndMethodAndModule(
                permission.getApiPath(),
                permission.getMethod(),
                permission.getModule()
        );
    }

    public ResultPaginationDTO fetchAllPermissions(Specification<Permission> spec, Pageable pageable) {
        return PaginationService.handlePagination(spec, pageable, permissionRepository);
    }

    public void deleteById(Long id) {

        Optional<Permission> permissionOptional = this.permissionRepository.findById(id);
        Permission currentPermission = permissionOptional.get();
        currentPermission.getRoles().forEach(role -> role.getPermissions().remove(currentPermission));
        // delete permission
        this.permissionRepository.delete(currentPermission);
    }
}
