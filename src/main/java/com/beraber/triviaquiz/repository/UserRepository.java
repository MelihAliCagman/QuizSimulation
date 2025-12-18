package com.beraber.triviaquiz.repository;

import java.util.List;
import com.beraber.triviaquiz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // "SELECT * FROM app_users WHERE username = ?" sorgusunu otomatik yazar
    User findByUsername(String username);
    List<User> findTop10ByOrderByTotalScoreDesc();
}