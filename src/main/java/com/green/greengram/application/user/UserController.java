package com.green.greengram.application.user;

import com.green.greengram.application.user.model.UserSignInReq;
import com.green.greengram.application.user.model.UserSignUpReq;
import com.green.greengram.config.model.ResultResponse;
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

    @PostMapping("/sign-up")
    public ResultResponse<?> signUp(@Valid @RequestPart UserSignUpReq req
                                  , @RequestPart(required = false) MultipartFile pic) {
        log.info("req: {}", req);
        log.info("pic: {}", pic != null ? pic.getOriginalFilename() : pic);
        userService.signUp(req, pic);
        return new ResultResponse<>("", 1);
    }

    @PostMapping("/sign-in")
    public ResultResponse<?> signIn(@Valid @RequestBody UserSignInReq req) {
        log.info("req: {}", req);
        return null;
    }
}
