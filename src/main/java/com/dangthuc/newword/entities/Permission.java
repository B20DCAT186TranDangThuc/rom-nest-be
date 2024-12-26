package com.dangthuc.newword.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "THUCTS_PERMISSIONS")
@Getter
@Setter
public class Permission extends BaseEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISSION_SEQ")
//    @SequenceGenerator(sequenceName = "PERMISSION_SEQ", allocationSize = 1, name = "PERMISSION_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name không được để trống")
    private String name;
    @NotBlank(message = "apiPath không được để trống")
    private String apiPath;
    @NotBlank(message = "method không được để trống")
    private String method;
    @NotBlank(message = "module không được để trống")
    private String module;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    @JsonIgnore
    private List<Role> roles;

    public Permission() {}

    public Permission(String name, String path, String post, String subscribers) {
        this.name = name;
        this.apiPath = path;
        this.method = post;
        this.module = subscribers;
    }
}
