package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class FeedPic extends CreatedAt {
    @EmbeddedId
    private FeedPicIds feedPicIds;

    //관계설정
    @ManyToOne
    @JoinColumn(name = "feed_id")
    @MapsId("feedId")
    private Feed feed;
}
