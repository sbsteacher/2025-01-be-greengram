package com.green.greengram.application.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedGetRes {
    private long feedId;
    private String contents;
    private String location;
    private String createdAt;
    private long writerUserId;
    private String writerUid;
    private String writerNickName;
    private String writerPic;
    //private List<String> pics;
}
