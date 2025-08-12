package com.green.greengram.application.feedcomment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FeedCommentGetRes {
    private boolean moreComment; //댓글 더 있다 정보
    private List<FeedCommentItem> commentList; //댓글 리스트 정보
}
