package com.green.greengram.application.feedlike.model;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FeedLikeToggleReq {
    @Positive
    private Long feedId;
}
