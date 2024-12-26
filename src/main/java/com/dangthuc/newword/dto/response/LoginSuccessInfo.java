package com.dangthuc.newword.dto.response;

import com.dangthuc.newword.entities.User;
import lombok.Data;

@Data
public class LoginSuccessInfo {
   private UserInfoDto userInfoDto;
   private String token;
}
