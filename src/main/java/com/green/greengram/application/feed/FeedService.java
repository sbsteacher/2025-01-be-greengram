package com.green.greengram.application.feed;

import com.green.greengram.application.feed.model.*;
import com.green.greengram.application.feedcomment.FeedCommentMapper;
import com.green.greengram.application.feedcomment.FeedCommentRepository;
import com.green.greengram.application.feedcomment.model.FeedCommentGetReq;
import com.green.greengram.application.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.application.feedcomment.model.FeedCommentItem;
import com.green.greengram.application.feedlike.FeedLikeRepository;
import com.green.greengram.configuration.constants.ConstComment;
import com.green.greengram.configuration.util.ImgUploadManager;
import com.green.greengram.entity.Feed;
import com.green.greengram.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper feedMapper;
    private final FeedCommentMapper feedCommentMapper;
    private final FeedLikeRepository feedLikeRepository;
    private final FeedRepository feedRepository;
    private final ImgUploadManager imgUploadManager;
    private final ConstComment constComment;
    private final FeedCommentRepository feedCommentRepository;

    @Transactional
    public FeedPostRes postFeed(long signedUserId, FeedPostReq req, List<MultipartFile> pics) {
        User writerUser = new User();
        writerUser.setUserId(signedUserId);

        Feed feed = Feed.builder()
                        .writerUser(writerUser)
                        .location(req.getLocation())
                        .contents(req.getContents())
                        .build();

        feedRepository.save(feed); //feed객체는 영속성을 갖는다.

        List<String> fileNames = imgUploadManager.saveFeedPics(feed.getFeedId(), pics);

        feed.addFeedPics(fileNames);

        return new FeedPostRes(feed.getFeedId(), fileNames);
    }

    public List<FeedGetRes> getFeedList(FeedGetDto dto) {
        List<FeedGetRes> list = feedMapper.findAllLimitedTo(dto);

        for(FeedGetRes feedGetRes : list) {
            feedGetRes.setPics(feedMapper.findAllPicByFeedId(feedGetRes.getFeedId()));
            //startIdx:0, size: 4
            FeedCommentGetReq req = new FeedCommentGetReq(feedGetRes.getFeedId(), constComment.startIndex, constComment.needForViewCount);
            List<FeedCommentItem> commentList = feedCommentMapper.findAllByFeedIdLimitedTo(req);
            boolean moreComment = commentList.size() > constComment.needForViewCount; //row수가 4였을 때만 true가 담기고, row수가 0~3인 경우는 false가 담긴다.
            FeedCommentGetRes feedCommentGetRes = new FeedCommentGetRes(moreComment, commentList);
            feedGetRes.setComments(feedCommentGetRes);
            if(moreComment) { //마지막 댓글 삭제
               commentList.remove(commentList.size() - 1); //마지막 아이템 삭제
            }
        }
        return list;
    }

    public Set<String> getKeywordList(FeedKeywordGetReq req) {
        return feedMapper.findAllByKeyword(req);
    }

    @Transactional
    public void deleteFeed(long signedUserId, long feedId) {
        Feed feed = feedRepository.findById(feedId)
                                  .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "feed_id가 존재하지 않습니다."));
        if(feed.getWriterUser().getUserId() != signedUserId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "피드 삭제 권한이 없습니다.");
        }

        //해당 피드 좋아요 삭제
        feedLikeRepository.deleteByIdFeedId(feedId);

        //해당 피드 댓글 삭제
        feedCommentRepository.deleteByFeedFeedId(feedId);

        //피드, 피드 사진 삭제
        feedRepository.delete(feed);

        //해당 피드 사진 폴더 삭제
        imgUploadManager.removeFeedDirectory(feedId);
    }

}
