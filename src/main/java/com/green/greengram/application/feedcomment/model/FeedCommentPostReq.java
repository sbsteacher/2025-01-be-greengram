package com.green.greengram.application.feedcomment.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FeedCommentPostReq {
    @Positive
    private long feedId;

    @NotNull(message = "댓글 내용은 필수입니다.")
    @Size(min = 2, max = 150, message = "댓글 내용은 2자 이상 150자 이하만 가능합니다.")
    private String comment;
}
