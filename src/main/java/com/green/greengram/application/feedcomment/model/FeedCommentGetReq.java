package com.green.greengram.application.feedcomment.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@ToString
public class FeedCommentGetReq {
    @Positive
    @NotNull(message = "feed_id는 필수값")
    private Long feedId;

    @PositiveOrZero
    @NotNull(message = "start_idx는 필수값")
    private Integer startIdx;

    //@Min(20) @Max(50)
    @NotNull(message = "size는 필수값")
    private Integer size;

    private Integer sizePlusOne;

    @ConstructorProperties({"feed_id", "start_idx", "size"})
    public FeedCommentGetReq(Long feedId, Integer startIdx, Integer size) {
        this.feedId = feedId;
        this.startIdx = startIdx;
        this.size = size;
        this.sizePlusOne = size + 1;
    }

//    public Integer getSizePlusOne() {
//        return size + 1;
//    }
}
