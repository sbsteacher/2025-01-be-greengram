package com.green.greengram.application.feed;

import com.green.greengram.application.feed.model.FeedGetDto;
import com.green.greengram.application.feed.model.FeedGetReq;
import com.green.greengram.application.feed.model.FeedPostReq;
import com.green.greengram.application.feed.model.FeedPostRes;
import com.green.greengram.config.model.ResultResponse;
import com.green.greengram.config.model.UserPrincipal;
import com.green.greengram.entity.Feed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;

    @PostMapping
    public ResultResponse<?> postFeed(@AuthenticationPrincipal UserPrincipal userPrincipal
                                    , @Valid @RequestPart FeedPostReq req
                                    , @RequestPart(name = "pic") List<MultipartFile> pics) {
        log.info("signedUserId: {}", userPrincipal.getSignedUserId());
        log.info("req: {}", req);
        log.info("pics.size(): {}", pics.size());
        FeedPostRes result = feedService.postFeed(userPrincipal.getSignedUserId(), req, pics);
        return new ResultResponse<>("피드 등록 완료", result);
    }

    //페이징, 피드(사진, 댓글(3개만))
    //현재는 피드+사진만 (N+1로 처리)
    @GetMapping
    public ResultResponse<?> getFeedList(@AuthenticationPrincipal UserPrincipal userPrincipal
                                       , @Valid @ModelAttribute FeedGetReq req) {
        log.info("signedUserId: {}", userPrincipal.getSignedUserId());
        log.info("req: {}", req);
        FeedGetDto feedGetDto = FeedGetDto.builder()
                                          .signedUserId(userPrincipal.getSignedUserId())
                                          .startIdx((req.getPage() - 1) * req.getRowPerPage())
                                          .size(req.getRowPerPage())
                                          .build();

        return null;
    }
}
