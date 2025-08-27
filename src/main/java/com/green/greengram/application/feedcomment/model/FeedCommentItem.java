package com.green.greengram.application.feedcomment.model;

import lombok.Getter;

@Getter
public class FeedCommentItem {
    private Long feedCommentId;
    private String comment;
    private Long writerUserId;
    private String writerUid;
    private String writerNickName;
    private String writerPic;
}
