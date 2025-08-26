package com.green.greengram.application.user;

import com.green.greengram.configuration.security.SignInProviderType;
import com.green.greengram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUidAndProviderType(String uid, SignInProviderType signInProviderType);
}
