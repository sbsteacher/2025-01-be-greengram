package com.green.greengram.application.feedcomment;

import com.green.greengram.application.feedcomment.model.FeedCommentGetReq;
import com.green.greengram.application.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.application.feedcomment.model.FeedCommentItem;
import com.green.greengram.application.feedcomment.model.FeedCommentPostReq;
import com.green.greengram.configuration.constants.ConstComment;
import com.green.greengram.entity.Feed;
import com.green.greengram.entity.FeedComment;
import com.green.greengram.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public void deleteFeedComment(long signedUserId, long feedCommentId) {
        FeedComment feedComment = feedCommentRepository.findById(feedCommentId).orElseThrow( ()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "feed_comment_id를 확인해 주세요.") );

        //본인이 작성한 댓글이 아니라면 exception 터트림 "본인이 작성한 댓글이 아닙니다."
        if(signedUserId != feedComment.getUser().getUserId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인이 작성한 댓글이 아닙니다.");
        }

        //댓글 삭제
        feedCommentRepository.deleteById(feedCommentId);
    }
}
