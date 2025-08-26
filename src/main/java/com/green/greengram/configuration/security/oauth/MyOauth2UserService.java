package com.green.greengram.configuration.security.oauth;

import com.green.greengram.application.user.UserRepository;
import com.green.greengram.configuration.enumcode.model.EnumUserRole;
import com.green.greengram.configuration.model.JwtUser;
import com.green.greengram.configuration.model.UserPrincipal;
import com.green.greengram.configuration.security.SignInProviderType;
import com.green.greengram.configuration.security.oauth.userinfo.Oauth2UserInfo;
import com.green.greengram.configuration.security.oauth.userinfo.Oauth2UserInfoFactory;
import com.green.greengram.entity.User;

import com.green.greengram.entity.UserRole;
import com.green.greengram.entity.UserRoleIds;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class MyOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final Oauth2UserInfoFactory oauth2UserInfoFactory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest req) {
        try {
            return process(req);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest req) {
        OAuth2User oAuth2User = super.loadUser(req); //소셜 로그인 완료하고 사용자 정보 JSON형태의 데이터를 담고 있는 객체
        /*
        req.getClientRegistration().getRegistrationId(); 소셜로그인 신청한 플랫폼 문자열값이 넘어온다.
        플랫폼 문자열값은 spring.security.oauth2.client.registration 아래에 있는 속성값들이다. (google, kakao, naver)
         */

        //소셜 로그인 accessToken
        String oauth2AccessToken = req.getAccessToken().getTokenValue();

        SignInProviderType signInProviderType = SignInProviderType.valueOf(req.getClientRegistration()
                                                                              .getRegistrationId()
                                                                              .toUpperCase());

        //사용하기 편하도록 규격화된 객체로 변환
        Oauth2UserInfo oauth2UserInfo = oauth2UserInfoFactory.getOauth2UserInfo(signInProviderType, oAuth2User.getAttributes());

        //기존에 회원가입이 되어있는지 체크
        User user = userRepository.findByUidAndProviderType(oauth2UserInfo.getId(), signInProviderType);
        if(user == null) { // 최초 로그인 상황 > 회원가입 처리
            user = new User();
            user.setUid(oauth2UserInfo.getId());
            user.setAccessToken(oauth2AccessToken);
            user.setProviderType(signInProviderType);
            user.setUpw("");
            user.setNickName(oauth2UserInfo.getName());
            user.setPic(oauth2UserInfo.getProfileImageUrl());

            //최초 소셜 로그인은 회원가입으로 권한은 USER_1 처리
            List<UserRole> userRoles = new ArrayList<>(1);
            UserRoleIds ids = new UserRoleIds(user.getUserId(), EnumUserRole.USER_1);

            UserRole userRole = new UserRole(ids, user);
            userRoles.add(userRole);

            user.setUserRoles(userRoles);
        } else {
            user.setAccessToken(oauth2AccessToken);
        }
        userRepository.save(user);

        List<EnumUserRole> roles = user.getUserRoles().stream().map(item -> item.getUserRoleIds()
                                                                                .getRoleCode()).toList();

        String nickName = user.getNickName() == null ? user.getUid() : user.getNickName();
        JwtUser jwtUser = new OAuth2JwtUser(nickName, user.getPic(), user.getUserId(), roles);

        UserPrincipal myUserDetails = new UserPrincipal(jwtUser);
        return myUserDetails; //이 객체는 OAuth2AuthenticationSuccessHandler객체의 onAuthenticationSuccess메소드의 Authentication auth 매개변수로 전달된다.
    }
}






