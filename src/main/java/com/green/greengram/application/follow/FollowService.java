package com.green.greengram.application.follow;

import com.green.greengram.entity.User;
import com.green.greengram.entity.UserFollow;
import com.green.greengram.entity.UserFollowIds;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    public void postUserFollow(long fromUserId, long toUserId) {
        UserFollowIds userFollowIds = UserFollowIds.builder()
                                                   .fromUserId(fromUserId)
                                                   .toUserId(toUserId)
                                                   .build();
        User fromUser = new User();
        fromUser.setUserId(fromUserId);

        User toUser = new User();
        toUser.setUserId(toUserId);

        UserFollow userFollow = UserFollow.builder()
                .userFollowIds(userFollowIds)
                .fromUser(fromUser)
                .toUser(toUser)
                .build();

        followRepository.save(userFollow);
    }
}
