package com.green.greengram.application.feedcomment;

import com.green.greengram.application.feedcomment.model.FeedCommentPostReq;
import com.green.greengram.entity.Feed;
import com.green.greengram.entity.FeedComment;
import com.green.greengram.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentRepository feedCommentRepository;

    public long postFeedComment(long signedUserId, FeedCommentPostReq req) {
        Feed feed = Feed.builder()
                .feedId(req.getFeedId())
                .build();

        User user = new User();
        user.setUserId(signedUserId);

        FeedComment feedComment = FeedComment.builder()
                                             .feed(feed)
                                             .user(user)
                                             .comment(req.getComment())
                                             .build();
        feedCommentRepository.save(feedComment);
        return feedComment.getFeedCommentId();
    }
}
