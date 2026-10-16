package com.beraber.triviaquiz.controller;

import java.util.List;

import com.beraber.triviaquiz.entity.User;
import com.beraber.triviaquiz.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Kayıt: POST /api/users/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        String name = user.getUsername() == null ? "" : user.getUsername().trim();
        if (name.length() < 3) {
            return ResponseEntity.badRequest().body("Kullanıcı adı en az 3 karakter olmalı.");
        }
        if (user.getPassword() == null || user.getPassword().length() < UserService.MIN_PASSWORD_LENGTH) {
            return ResponseEntity.badRequest().body("Şifre en az " + UserService.MIN_PASSWORD_LENGTH + " karakter olmalı.");
        }
        user.setUsername(name);
        User saved = userService.register(user);
        if (saved == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Bu kullanıcı adı zaten alınmış.");
        }
        return ResponseEntity.ok(saved);
    }

    // Giriş: POST /api/users/login  (başarılıysa oturum açılır)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User body, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.login(body.getUsername(), body.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Session fixation'a karşı mevcut oturumun kimliğini yenile
        if (request.getSession(false) != null) request.changeSessionId();

        String role = user.getRole() == null ? "USER" : user.getRole();
        Authentication auth = UsernamePasswordAuthenticationToken.authenticated(
                user.getUsername(), null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        new HttpSessionSecurityContextRepository().saveContext(context, request, response);

        return ResponseEntity.ok(user);
    }

    // Çıkış: POST /api/users/logout
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        if (request.getSession(false) != null) request.getSession(false).invalidate();
    }

    // Puan ekleme: yalnızca giriş yapmış kullanıcı kendi hesabına ekleyebilir
    @PostMapping("/score/{userId}")
    public ResponseEntity<Void> addScore(@PathVariable Long userId, @RequestParam int score, Authentication auth) {
        User me = userService.findByUsername(auth.getName());
        if (me == null || !me.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userService.addScore(userId, score);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/leaderboard")
    public List<User> getLeaderboard() {
        return userService.getLeaderboard();
    }

    // Yönetici: tüm kullanıcılar
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Yönetici: kullanıcı sil
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
