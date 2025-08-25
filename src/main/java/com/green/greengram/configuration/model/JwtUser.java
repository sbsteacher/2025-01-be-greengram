package com.green.greengram.configuration.model;

import com.green.greengram.configuration.enumcode.model.EnumUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class JwtUser {
    private long signedUserId;
    private List<EnumUserRole> roles; //인가 처리 때 사용
    /*
    role 이름은 ROLE_아무거나

    ROLE_USER, ROLE_ADMIN, ROLE_MANAGER, ROLE_LEVEL_1 ...
     */
}
