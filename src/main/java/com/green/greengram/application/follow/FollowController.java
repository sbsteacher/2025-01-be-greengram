package com.green.greengram.application.follow;

import com.green.greengram.application.follow.model.FollowPostReq;
import com.green.greengram.configuration.model.ResultResponse;
import com.green.greengram.configuration.model.UserPrincipal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/follow")
public class FollowController {
    private final FollowService followService;

    @PostMapping
    public ResultResponse<?> postUserFollow(@AuthenticationPrincipal UserPrincipal userPrincipal
                                          , @Valid @RequestBody FollowPostReq req) {
        log.info("fromUserId: {}", userPrincipal.getSignedUserId());
        log.info("toUserId: {}", req.getToUserId());
        followService.postUserFollow(userPrincipal.getSignedUserId(), req.getToUserId());
        return new ResultResponse<>("팔로우 성공", null);
    }

    @DeleteMapping
    public ResultResponse<?> deleteUserFollow(@AuthenticationPrincipal UserPrincipal userPrincipal
                                            , @Valid @RequestParam("to_user_id") @Positive Long toUserId) {
        log.info("fromUserId: {}", userPrincipal.getSignedUserId());
        log.info("toUserId: {}", toUserId);
        followService.deleteUserFollow(userPrincipal.getSignedUserId(), toUserId);
        return new ResultResponse<>("팔로우 취소", null);
    }
}
