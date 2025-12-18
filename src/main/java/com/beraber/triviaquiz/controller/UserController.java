package com.beraber.triviaquiz.controller;

import java.util.List;
import com.beraber.triviaquiz.entity.User;
import com.beraber.triviaquiz.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Kayıt Endpoint'i: POST /api/users/register
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    // Giriş Endpoint'i: POST /api/users/login
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user.getUsername(), user.getPassword());
    }

    // Puan Ekleme: POST /api/users/score/1?score=50
    @PostMapping("/score/{userId}")
    public void addScore(@PathVariable Long userId, @RequestParam int score) {
        userService.addScore(userId, score);
    }

    @GetMapping("/leaderboard")
    public List<User> getLeaderboard() {
        return userService.getLeaderboard();
    }

    // Tüm kullanıcıları getir: GET /api/users
    @GetMapping
    public java.util.List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Kullanıcı sil: DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}