package com.green.greengram.application.user.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserSignUpReq {
    private String uid;
    private String upw;
    private String nickName;
}
