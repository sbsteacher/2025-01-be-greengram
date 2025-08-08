package com.green.greengram.application.feed;

import com.green.greengram.application.feed.model.FeedPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;

    public void postFeed(long signedUserId, FeedPostReq req, List<MultipartFile> pics) {

    }
}
