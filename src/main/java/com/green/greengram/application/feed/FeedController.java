package com.green.greengram.application.feed;

import com.green.greengram.application.feed.model.*;
import com.green.greengram.configuration.model.ResultResponse;
import com.green.greengram.configuration.model.UserPrincipal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;
    private final int MAX_PIC_COUNT = 10;

    @PostMapping
    public ResultResponse<?> postFeed(@AuthenticationPrincipal UserPrincipal userPrincipal
                                    , @Valid @RequestPart FeedPostReq req
                                    , @RequestPart(name = "pic") List<MultipartFile> pics) {

        if(pics.size() > MAX_PIC_COUNT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , String.format("사진은 %d장까지 선택 가능합니다.", MAX_PIC_COUNT));
        }
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
                                          .profileUserId(req.getProfileUserId())
                                          .keyword(req.getKeyword())
                                          .build();
        List<FeedGetRes> result = feedService.getFeedList(feedGetDto);
        return new ResultResponse<>(String.format("rows: %d", result.size())
                                  , result);
    }

    @GetMapping("keyword")
    public ResultResponse<?> getKeywordList(@Valid @ModelAttribute FeedKeywordGetReq req) {
        log.info("req: {}", req);
        Set<String> result = feedService.getKeywordList(req);
        return new ResultResponse<>(String.format("rows: %d", result.size())
                                  , result);
    }


    @DeleteMapping
    public ResultResponse<?> deleteFeed(@AuthenticationPrincipal UserPrincipal userPrincipal
                                      , @RequestParam("feed_id") @Valid @Positive Long feedId) {
        log.info("signedUserId: {}", userPrincipal.getSignedUserId());
        log.info("feedId: {}", feedId);
        feedService.deleteFeed(userPrincipal.getSignedUserId(), feedId);
        return new ResultResponse<>("피드가 삭제되었습니다.", null);
    }
}
