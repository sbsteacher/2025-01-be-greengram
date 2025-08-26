package com.green.greengram.configuration.security.oauth.userinfo;

import java.util.Map;
import java.util.Optional;

/*
    "response": {
        "id": id값,
        "name": 이름값,
        "email": 이메일,
        "nickname": 닉네임
        "profile_image": 프로파일 이미지
    }


 */
public class NaverOAuth2UserInfo extends Oauth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    private Optional<Map<String, Object>> getResponse() {
        return Optional.ofNullable((Map<String, Object>) attributes.get("response"));

    }

    @Override
    public String getId() {
        return getResponse().get().get("id").toString();
    }

    @Override
    public String getName() {
        return getResponse().get().get("name").toString();
    }

    @Override
    public String getEmail() {
        return getResponse().get().get("email").toString();
    }

    @Override
    public String getProfileImageUrl() {
        return getResponse().get().get("profile_image").toString();
    }
}
