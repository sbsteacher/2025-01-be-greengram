package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserFollow extends CreatedAt {
    @EmbeddedId
    private UserFollowIds userFollowIds;

    //관계설정(FK)
    @ManyToOne
    @MapsId("fromUserId")
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne
    @MapsId("toUserId")
    @JoinColumn(name = "to_user_id")
    private User toUser;
}
