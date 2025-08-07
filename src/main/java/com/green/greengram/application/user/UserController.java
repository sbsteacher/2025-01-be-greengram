package com.green.greengram.application.user;

import com.green.greengram.application.user.model.UserSignInDto;
import com.green.greengram.application.user.model.UserSignInReq;
import com.green.greengram.application.user.model.UserSignUpReq;
import com.green.greengram.config.jwt.JwtTokenManager;
import com.green.greengram.config.model.ResultResponse;
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

    @PostMapping("/sign-in")
    public ResultResponse<?> signIn(@Valid @RequestBody UserSignInReq req, HttpServletResponse response) {
        log.info("req: {}", req);
        UserSignInDto userSignInDto = userService.signIn(req);
        jwtTokenManager.issue(response, userSignInDto.getJwtUser());
        return new ResultResponse<>("sign-in 성공", userSignInDto.getUserSignInRes());
    }
}
