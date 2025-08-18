package com.green.greengram.application.feedcomment;

import com.green.greengram.entity.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
    void deleteByFeedFeedId(long feedId);
}
