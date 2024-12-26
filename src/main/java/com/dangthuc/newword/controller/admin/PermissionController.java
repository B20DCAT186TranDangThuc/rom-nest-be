package com.dangthuc.newword.controller.admin;

import com.dangthuc.newword.dto.response.RestResponse;
import com.dangthuc.newword.dto.response.ResultPaginationDTO;
import com.dangthuc.newword.entities.Permission;
import com.dangthuc.newword.entities.User;
import com.dangthuc.newword.exceptions.ExitsException;
import com.dangthuc.newword.service.PermissionService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping("/permissions")
    public ResponseEntity<RestResponse<Permission>> addPermission(@Valid @RequestBody Permission permission) throws ExitsException {
        RestResponse<Permission> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Permission added successfully");
        response.setData(permissionService.save(permission));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/permissions")
    public ResponseEntity<RestResponse<Permission>> updatePermission(@Valid @RequestBody Permission permission) throws ExitsException {
        RestResponse<Permission> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Permission updated successfully");
        response.setData(permissionService.save(permission));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<RestResponse<Void>> deletePermission(@PathVariable Long id) throws ExitsException {
        if (permissionService.fetchPermissionById(id) == null) {
            throw new ExitsException("Permission doesn't exist");
        }

        permissionService.deleteById(id);

        RestResponse<Void> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Permission deleted successfully");
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/permissions/{id}")
    public ResponseEntity<RestResponse<Permission>> getPermissionById(@PathVariable Long id) throws ExitsException {
        Permission permission = permissionService.fetchPermissionById(id);
        if (permission == null) {
            throw new ExitsException("Permission doesn't exist");
        }

        RestResponse<Permission> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Permission found");
        response.setData(permission);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/permissions")
    public ResponseEntity<RestResponse<ResultPaginationDTO>> getAllPermissions(@Filter Specification<Permission> spec, Pageable pageable) {
        RestResponse<ResultPaginationDTO> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(permissionService.fetchAllPermissions(spec, pageable));
        response.setMessage("Users found");

        return ResponseEntity.ok(response);
    }
}
