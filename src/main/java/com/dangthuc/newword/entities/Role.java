package com.dangthuc.newword.entities;

import com.dangthuc.newword.dto.response.RoleInfoDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "THUCTD_ROLES")
@Getter
@Setter
public class Role extends BaseEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ")
//    @SequenceGenerator(sequenceName = "ROLE_SEQ", allocationSize = 1, name = "ROLE_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private boolean active;

    @ManyToMany
    @JsonIgnoreProperties(value = {"roles"})
    @JoinTable(name = "THUCTD_PERMISSION_ROLE", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    List<Permission> permissions;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    List<User> users;

    public RoleInfoDto toRoleInfoDto() {
        RoleInfoDto roleInfoDto = new RoleInfoDto();
        roleInfoDto.setName(name);
        roleInfoDto.setDescription(description);
        roleInfoDto.setActive(active);
        List<Map<Long, String>> permissions = getPermissions().stream()
                .map(permission -> Map.of(permission.getId(), permission.getName()))
                .collect(Collectors.toList());
        roleInfoDto.setPermissions(permissions);

        return roleInfoDto;
    }
}
