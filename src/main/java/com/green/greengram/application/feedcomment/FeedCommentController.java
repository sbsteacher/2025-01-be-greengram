package com.green.greengram.application.feedcomment;

import com.green.greengram.application.feedcomment.model.FeedCommentGetReq;
import com.green.greengram.application.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.application.feedcomment.model.FeedCommentPostReq;
import com.green.greengram.configuration.model.ResultResponse;
import com.green.greengram.configuration.model.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/feed/comment")
public class FeedCommentController {
    private final FeedCommentService feedCommentService;

    @PostMapping
    public ResultResponse<?> postFeedComment(@AuthenticationPrincipal UserPrincipal userPrincipal
                                           , @Valid @RequestBody FeedCommentPostReq req) {
        log.info("signedUserId: {}", userPrincipal.getSignedUserId());
        log.info("req: {}", req);
        long feedCommentId = feedCommentService.postFeedComment(userPrincipal.getSignedUserId(), req);
        return new ResultResponse<>("댓글 등록 완료", feedCommentId);
    }

    @GetMapping
    public ResultResponse<?> getFeedCommentList(@Valid @ModelAttribute FeedCommentGetReq req) {
        log.info("req: {}", req);
        FeedCommentGetRes feedCommentGetRes = feedCommentService.getFeedList(req);
        return new ResultResponse<>(String.format("rows: %d", feedCommentGetRes.getCommentList().size())
                                  , feedCommentGetRes);
    }

    @DeleteMapping
    public ResultResponse<?> deleteFeedComment(@AuthenticationPrincipal UserPrincipal userPrincipal
                                             , @RequestParam("feed_comment_id") Long feedCommentId) {

        feedCommentService.deleteFeedComment(userPrincipal.getSignedUserId(), feedCommentId);
        return new ResultResponse<>("댓글을 삭제하였습니다.", null);
    }
}
