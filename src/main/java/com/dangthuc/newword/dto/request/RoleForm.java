package com.dangthuc.newword.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleForm {

    private Long id;
    @NotBlank(message = "Role name không được để trống!")
    private String name;
    private boolean active;
    private String description;
    private List<Long> permissionIds;
}
