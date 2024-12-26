package com.dangthuc.newword.controller.auth;

import com.dangthuc.newword.dto.request.FormLogin;
import com.dangthuc.newword.dto.response.LoginSuccessInfo;
import com.dangthuc.newword.dto.response.RestResponse;
import com.dangthuc.newword.jwt.JwtService;
import com.dangthuc.newword.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    // phải có getter setter cho formlogin nếu muốn thoát khỏi validation
    public ResponseEntity<RestResponse<LoginSuccessInfo>> login(@RequestBody @Valid FormLogin request) {

        // nạp thông tin người dùng vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        // xác thực người dùng => cần override lại hàm loadUserByUsername()
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // nạp thông tin nếu xử lý thành công
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // tạo jwt token
        String JWT = jwtService.generateToken(request.getUsername());

        LoginSuccessInfo loginSuccessInfo = new LoginSuccessInfo();
        loginSuccessInfo.setUserInfoDto(userService.checkUserByUsername(request.getUsername()).userInfoDto());
        loginSuccessInfo.setToken(JWT);
        RestResponse<LoginSuccessInfo> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(loginSuccessInfo);
        response.setMessage("Login success");
        return ResponseEntity.ok(response);
    }
}
