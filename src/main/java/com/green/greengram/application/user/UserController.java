package com.green.greengram.application.user;

import com.green.greengram.application.user.model.UserSignInDto;
import com.green.greengram.application.user.model.UserSignInReq;
import com.green.greengram.application.user.model.UserSignUpReq;
import com.green.greengram.config.jwt.JwtTokenManager;
import com.green.greengram.config.model.ResultResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResultResponse<?> signOut(HttpServletResponse response) {
        jwtTokenManager.signOut(response);
        return new ResultResponse<>("sign-out 성공", null);
    }

    @PostMapping("/reissue")
    public ResultResponse<?> reissue(HttpServletResponse response, HttpServletRequest request) {
        jwtTokenManager.reissue(request, response);
        return new ResultResponse<>("AccessToken 재발행 성공", null);
    }

}
