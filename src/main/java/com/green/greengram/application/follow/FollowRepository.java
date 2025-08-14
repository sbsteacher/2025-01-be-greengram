package com.green.greengram.application.follow;

import com.green.greengram.entity.UserFollow;
import com.green.greengram.entity.UserFollowIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<UserFollow, UserFollowIds> {
}
