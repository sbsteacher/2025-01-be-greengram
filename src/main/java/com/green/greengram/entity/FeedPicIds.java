package com.green.greengram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class FeedPicIds implements Serializable {
    private long feedId;
    @Column(length = 50)
    private String pic;
}
