package com.green.greengram.application.user;

import com.green.greengram.application.user.model.*;
import com.green.greengram.configuration.jwt.JwtTokenManager;
import com.green.greengram.configuration.model.ResultResponse;
import com.green.greengram.configuration.model.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping("/sign-up")
    public ResultResponse<?> signUp(@Valid @RequestPart UserSignUpReq req
                                  , @RequestPart(required = false) MultipartFile pic) {
        log.info("req: {}", req);
        log.info("pic: {}", pic != null ? pic.getOriginalFilename() : pic);
        userService.signUp(req, pic);
        return new ResultResponse<>("", 1);
    }

    //response는 쿠키에 AT, RT을 담기 위해 필요하다.
    @PostMapping("/sign-in")
    public ResultResponse<?> signIn(@Valid @RequestBody UserSignInReq req, HttpServletResponse response) {
        log.info("req: {}", req);
        UserSignInDto userSignInDto = userService.signIn(req);
        jwtTokenManager.issue(response, userSignInDto.getJwtUser());
        return new ResultResponse<>("sign-in 성공", userSignInDto.getUserSignInRes());
    }

    @PostMapping("/sign-out")
    public ResultResponse<?> signOut(@AuthenticationPrincipal UserPrincipal userPrincipal
                                   , HttpServletResponse response) {
        jwtTokenManager.signOut(response);
        return new ResultResponse<>("sign-out 성공", null);
    }

    @PostMapping("/reissue")
    public ResultResponse<?> reissue(HttpServletResponse response, HttpServletRequest request) {
        jwtTokenManager.reissue(request, response);
        return new ResultResponse<>("AccessToken 재발행 성공", null);
    }

    @GetMapping("/profile")
    public ResultResponse<?> getProfileUser(@AuthenticationPrincipal UserPrincipal userPrincipal
                                          , @RequestParam("profile_user_id") long profileUserId) {
        log.info("profileUserId: {}", profileUserId);
        UserProfileGetDto dto = new UserProfileGetDto(userPrincipal.getSignedUserId(), profileUserId);
        UserProfileGetRes userProfileGetRes = userService.getProfileUser(dto);
        return new ResultResponse<>("프로파일 유저 정보", userProfileGetRes);
    }

    @PatchMapping("/profile/pic")
    public ResultResponse<?> patchProfilePic(@AuthenticationPrincipal UserPrincipal userPrincipal
                                           , @RequestPart MultipartFile pic) {
        String savedFileName = userService.patchProfilePic(userPrincipal.getSignedUserId(), pic);
        return new ResultResponse<>("프로파일 사진 수정 완료", savedFileName);
    }

    //DeleteMapping - /profile/pic
    //프로파일 있는 폴더를 삭제하고
    //return new ResultResponse<>("프로파일 사진 삭제 완료", null);

    @DeleteMapping("/profile/pic")
    public ResultResponse<?> patchProfilePic(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        userService.deleteProfilePic(userPrincipal.getSignedUserId());
        return new ResultResponse<>("프로파일 사진 삭제 완료", null);
    }
}
