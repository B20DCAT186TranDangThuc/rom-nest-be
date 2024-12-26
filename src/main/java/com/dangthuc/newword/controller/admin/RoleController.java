package com.dangthuc.newword.controller.admin;

import com.dangthuc.newword.dto.request.RoleForm;
import com.dangthuc.newword.dto.response.RestResponse;
import com.dangthuc.newword.dto.response.ResultPaginationDTO;
import com.dangthuc.newword.dto.response.RoleInfoDto;
import com.dangthuc.newword.entities.Role;
import com.dangthuc.newword.exceptions.ExitsException;
import com.dangthuc.newword.service.RoleService;
import com.turkraft.springfilter.boot.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/roles")
    public ResponseEntity<RestResponse<RoleInfoDto>> addRole(@RequestBody RoleForm request) throws ExitsException {
        Role role = roleService.create(request);
        if (role == null) {
            throw new ExitsException("Something went wrong!");
        }
        RestResponse<RoleInfoDto> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setData(role.toRoleInfoDto());
        response.setMessage("Role added successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/roles")
    public ResponseEntity<RestResponse<RoleInfoDto>> updateRole(@RequestBody RoleForm request) throws ExitsException {
        Role role = roleService.update(request);
        if (role == null) {
            throw new ExitsException("Role doesn't exist!");
        }
        RestResponse<RoleInfoDto> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(role.toRoleInfoDto());
        response.setMessage("Role added successfully!");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<RestResponse<Void>> deleteRole(@PathVariable("id") Long id) throws ExitsException {
        if (roleService.fetchRoleById(id) == null) {
            throw new ExitsException("Role doesn't exist!");
        }

        roleService.deleteRoleById(id);
        RestResponse<Void> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Role deleted successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<RestResponse<RoleInfoDto>> getRole(@PathVariable("id") Long id) throws ExitsException {
        Role role = roleService.fetchRoleById(id);
        if (role == null) {
            throw new ExitsException("Role doesn't exist!");
        }
        RestResponse<RoleInfoDto> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(role.toRoleInfoDto());
        response.setMessage("Role retrieved successfully!");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/roles")
    public ResponseEntity<RestResponse<ResultPaginationDTO>> getRoles(@Filter Specification<Role> spec, Pageable pageable) throws ExitsException {
        RestResponse<ResultPaginationDTO> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(roleService.fetchAllRoles(spec, pageable));
        response.setMessage("Users found");

        return ResponseEntity.ok(response);
    }
}
