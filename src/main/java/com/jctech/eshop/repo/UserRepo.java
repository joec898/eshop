package com.jctech.eshop.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jctech.eshop.model.user.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
     Optional<User> findOneByUserId(String userId);
     Optional<User> findOneByUserIdAndPassword(String userId, String password);
}
