package com.dangthuc.newword.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserInfoDto {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate birthday;
    private String status;

    private LocalDateTime crateAt;
    private String createdBy;
    private LocalDateTime updateAt;
    private String updatedBy;

    private String role;
}
