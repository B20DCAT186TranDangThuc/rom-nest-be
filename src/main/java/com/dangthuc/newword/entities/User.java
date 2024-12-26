package com.dangthuc.newword.entities;

import com.dangthuc.newword.dto.request.UserForm;
import com.dangthuc.newword.dto.response.UserInfoDto;
import com.dangthuc.newword.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "THUCTD_USERS")
public class User extends BaseEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_INFO_SEQ")
//    @SequenceGenerator(sequenceName = "USER_INFO_SEQ", allocationSize = 1, name = "USER_INFO_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dob;
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    public User() {

    }

    public UserInfoDto userInfoDto() {
        return UserInfoDto.builder()
                .id(this.id)
                .username(this.username)
                .fullName(this.firstName + " " + this.lastName)
                .email(this.email)
                .phone(this.phone)
                .address(this.address)
                .gender(this.gender.name())
                .birthday(this.dob)
                .status(this.status)
                .crateAt(this.getCratedAt())
                .updateAt(this.getUpdateAt())
                .createdBy(this.getCreatedBy())
                .updatedBy(this.getUpdatedBy())
                .role(this.role.getName())
                .build();
    }

    public User(UserForm userForm) {
        this.username = userForm.getUsername();
        this.password = userForm.getPassword();
        this.firstName = userForm.getFirstName();
        this.lastName = userForm.getLastName();
        this.email = userForm.getEmail();
        this.phone = userForm.getPhone();
        this.address = userForm.getAddress();
        this.dob = userForm.getDob();
        this.setGender("MALE".equals(userForm.getGender()) ? Gender.MALE :
                "FEMALE".equals(userForm.getGender()) ? Gender.FEMALE :
                        Gender.OTHER);
        this.role = userForm.getRole();
    }
}
