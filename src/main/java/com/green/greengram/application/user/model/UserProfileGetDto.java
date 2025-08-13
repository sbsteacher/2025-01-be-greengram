package com.green.greengram.application.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileGetDto {
    private long signedUserId;
    private long profileUserId;
}
