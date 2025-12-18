package com.beraber.triviaquiz.service;

import com.beraber.triviaquiz.entity.User;
import com.beraber.triviaquiz.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // KAYIT OL (GÜNCELLENDİ)
    public User register(User user) {
        // Eğer bu isimde biri varsa null dön
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return null;
        }

        user.setTotalScore(0); // Puanı sıfırla
        user.setRole("USER");  // <--- YENİ EKLENEN SATIR: Varsayılan rol "USER" olsun

        return userRepository.save(user);
    }

    // GİRİŞ YAP
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
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

    // --- YENİ EKLENEN METODLAR (ADMİN İÇİN) ---

    // Tüm kullanıcıları listele
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Kullanıcıyı sil
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}