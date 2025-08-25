package com.green.greengram.application.user.model;

import com.green.greengram.configuration.model.JwtUser;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignInDto {
    private UserSignInRes userSignInRes; //응답용
    private JwtUser jwtUser; //JWT 발행 때 사용
}
