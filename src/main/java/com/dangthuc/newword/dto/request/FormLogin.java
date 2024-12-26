package com.dangthuc.newword.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class FormLogin {

    @NotBlank(message = "Username không được để trống")
    private String username;
    @NotBlank(message = "Password không được để trống")
    private String password;
}
