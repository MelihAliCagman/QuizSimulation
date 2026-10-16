package com.beraber.triviaquiz.service;

import com.beraber.triviaquiz.entity.User;
import com.beraber.triviaquiz.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public static final int MIN_PASSWORD_LENGTH = 6;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // KAYIT OL: şifre BCrypt ile hashlenir. Kullanıcı adı doluysa null döner.
    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setTotalScore(0);
        user.setRole("USER");

        return userRepository.save(user);
    }

    // GİRİŞ YAP
    public User login(String username, String password) {
        if (username == null || password == null) return null;
        User user = userRepository.findByUsername(username);
        if (user == null || user.getPassword() == null) return null;

        String stored = user.getPassword();
        if (isHashed(stored)) {
            return passwordEncoder.matches(password, stored) ? user : null;
        }

        // Eski kayıtlar düz metin tutuluyordu: doğruysa girişte hashe çevir.
        if (stored.equals(password)) {
            user.setPassword(passwordEncoder.encode(password));
            return userRepository.save(user);
        }
        return null;
    }

    public static boolean isHashed(String stored) {
        return stored != null && stored.startsWith("$2");
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // PUAN GÜNCELLE
    public void addScore(Long userId, int scoreToAdd) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setTotalScore(user.getTotalScore() + scoreToAdd);
            userRepository.save(user);
        }
    }

    // LİDERLİK TABLOSU
    public List<User> getLeaderboard() {
        return userRepository.findTop10ByOrderByTotalScoreDesc();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
