package com.green.greengram.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class UserFollowIds implements Serializable {
    private Long fromUserId;
    private Long toUserId;
}
