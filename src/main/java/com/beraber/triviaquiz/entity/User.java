package com.beraber.triviaquiz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "app_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private Integer totalScore = 0;

    private String role; // Rol bilgisi (ADMIN veya USER)

    // --- MANUEL GETTER VE SETTERLAR ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getTotalScore() { return totalScore; }
    public void setTotalScore(Integer totalScore) { this.totalScore = totalScore; }

    // EN ÖNEMLİ KISIM BURASI:
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}