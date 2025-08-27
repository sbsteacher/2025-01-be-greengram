package com.green.greengram.application.feed.model;

import jakarta.validation.constraints.*;
import lombok.Getter;

import lombok.ToString;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@ToString
public class FeedGetReq {
    @NotNull(message = "page값은 필수입니다.")
    @Positive
    private Integer page;

    @Min(value = 20, message = "20이상")
    @Max(value = 100, message = "100이하")
    @NotNull(message = "row_per_page값은 필수입니다.")
    private Integer rowPerPage;

    @Positive
    private Long profileUserId;

    private String keyword;

    public FeedGetReq(Integer page
                    , @BindParam("row_per_page") Integer rowPerPage
                    , @BindParam("profile_user_id") Long profileUserId
                    , String keyword) {
        this.page = page;
        this.rowPerPage = rowPerPage;
        this.profileUserId = profileUserId;
        this.keyword = keyword;
    }
}
