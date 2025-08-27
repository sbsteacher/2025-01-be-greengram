package com.green.greengram.application.feed.model;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedGetDto {
    private long signedUserId;
    private int startIdx;
    private int size;
    private Long profileUserId;
    private String keyword;
}
