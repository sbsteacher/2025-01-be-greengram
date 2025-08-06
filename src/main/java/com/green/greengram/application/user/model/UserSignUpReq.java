package com.green.greengram.application.user.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserSignUpReq {
    @Pattern(regexp = "^[A-Za-z0-9_]{4,50}$", message = "아이디는 영어, 숫자, 언더바로만 구성되어야 하며 4~50자까지 작성할 수 있습니다.")
    private String uid;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{10,}$", message = "비밀번호는 영문자, 숫자, 특수기호로 구성되며 10자 이상이어야 합니다.")
    private String upw;

    @Pattern(regexp = "^[가-힣]{2,10}$", message = "닉네임은 한글로 2~10자까지 가능합니다.")
    private String nickName;
}
