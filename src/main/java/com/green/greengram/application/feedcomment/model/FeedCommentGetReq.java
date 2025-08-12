package com.green.greengram.application.feedcomment.model;

import lombok.Getter;

@Getter
public class FeedCommentGetReq {
    private long feedId;
    private int startIdx;
    private int size;

    public FeedCommentGetReq(long feedId, int startIdx, int size) {
        this.feedId = feedId;
        this.startIdx = startIdx;
        this.size = size;
    }
}
