package com.green.greengram.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class FeedLikeIds implements Serializable {
    private Long feedId;
    private Long userId;
}
