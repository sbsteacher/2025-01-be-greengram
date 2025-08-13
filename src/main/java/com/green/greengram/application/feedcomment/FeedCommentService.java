package com.green.greengram.application.feedcomment;

import com.green.greengram.application.feedcomment.model.FeedCommentGetReq;
import com.green.greengram.application.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.application.feedcomment.model.FeedCommentItem;
import com.green.greengram.application.feedcomment.model.FeedCommentPostReq;
import com.green.greengram.config.constants.ConstComment;
import com.green.greengram.entity.Feed;
import com.green.greengram.entity.FeedComment;
import com.green.greengram.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentRepository feedCommentRepository;
    private final FeedCommentMapper feedCommentMapper;
    private final ConstComment constComment;

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

    public FeedCommentGetRes getFeedList(FeedCommentGetReq req) {
        List<FeedCommentItem> commentList = feedCommentMapper.findAllByFeedIdLimitedTo(req);
        boolean moreComment = commentList.size() > req.getSize();
        if(moreComment) { //마지막 댓글 삭제
            commentList.remove(commentList.size() - 1); //마지막 아이템 삭제
        }
        return new FeedCommentGetRes(moreComment, commentList);
    }
}
