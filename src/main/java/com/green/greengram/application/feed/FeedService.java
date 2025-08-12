package com.green.greengram.application.feed;

import com.green.greengram.application.feed.model.FeedGetDto;
import com.green.greengram.application.feed.model.FeedGetRes;
import com.green.greengram.application.feed.model.FeedPostReq;
import com.green.greengram.application.feed.model.FeedPostRes;
import com.green.greengram.application.feedcomment.FeedCommentMapper;
import com.green.greengram.application.feedcomment.model.FeedCommentGetReq;
import com.green.greengram.application.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.application.feedcomment.model.FeedCommentItem;
import com.green.greengram.config.util.ImgUploadManager;
import com.green.greengram.entity.Feed;
import com.green.greengram.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper feedMapper;
    private final FeedCommentMapper feedCommentMapper;
    private final FeedRepository feedRepository;
    private final ImgUploadManager imgUploadManager;

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
        //각 피드에서 사진 가져오기, 댓글 가져오기(4개만)
        final int START_IDX = 0;
        final int SIZE = 4;
        final int MORE_COMMENT_COUNT = 4;
        for(FeedGetRes feedGetRes : list) {
            feedGetRes.setPics(feedMapper.findAllPicByFeedId(feedGetRes.getFeedId()));
            //startIdx:0, size: 4
            FeedCommentGetReq req = new FeedCommentGetReq(feedGetRes.getFeedId(), START_IDX, SIZE);
            List<FeedCommentItem> commentList = feedCommentMapper.findAllByFeedIdLimitedTo(req);
            boolean moreComment = commentList.size() == MORE_COMMENT_COUNT;
            FeedCommentGetRes feedCommentGetRes = new FeedCommentGetRes(moreComment, commentList);
            feedGetRes.setComments(feedCommentGetRes);
            if(moreComment) { //마지막 댓글 삭제
               commentList.remove(MORE_COMMENT_COUNT - 1);
            }
        }
        return list;
    }

}
