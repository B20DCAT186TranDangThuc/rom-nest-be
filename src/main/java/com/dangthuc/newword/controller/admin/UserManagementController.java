package com.dangthuc.newword.controller.admin;

import com.dangthuc.newword.dto.request.UserForm;
import com.dangthuc.newword.dto.response.ResultPaginationDTO;
import com.dangthuc.newword.dto.response.UserInfoDto;
import com.dangthuc.newword.dto.response.RestResponse;
import com.dangthuc.newword.entities.User;
import com.dangthuc.newword.exceptions.ExitsException;
import com.dangthuc.newword.service.UserService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class UserManagementController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<RestResponse<UserInfoDto>> addUser(@RequestBody UserForm newUser) throws ExitsException {
        if (userService.checkUserByUsername(newUser.getUsername()) != null) {
            throw new ExitsException("Username is already exist");
        }
        UserInfoDto user = userService.create(newUser);
        RestResponse<UserInfoDto> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setData(user);
        response.setMessage("User added successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/users")
    public ResponseEntity<RestResponse<UserInfoDto>> updateUser(@Valid @RequestBody UserForm request) throws ExitsException {
        if (userService.fetchUserById(request.getId()) == null) {
            throw new ExitsException("User not found");
        }
        UserInfoDto user = userService.update(request);
        RestResponse<UserInfoDto> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(user);
        response.setMessage("User updated successfully");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<RestResponse<Void>> deleteUser(@PathVariable Long id) throws ExitsException {
        if (userService.fetchUserById(id) == null) {
            throw new ExitsException("User not found");
        }

        userService.deleteById(id);

        RestResponse<Void> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User deleted successfully");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<RestResponse<UserInfoDto>> getUserById(@PathVariable("id") Long id) throws ExitsException {
        if (userService.fetchUserById(id) == null) {
            throw new ExitsException("User not found");
        }
        RestResponse<UserInfoDto> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(userService.fetchUserById(id).userInfoDto());
        response.setMessage("User found");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<RestResponse<ResultPaginationDTO>> getUsers(@Filter Specification<User> spec, Pageable pageable) {
        RestResponse<ResultPaginationDTO> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(userService.fetchAllUser(spec, pageable));
        response.setMessage("Users found");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
