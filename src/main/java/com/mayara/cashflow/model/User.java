package com.mayara.cashflow.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    protected User() {
    }

    public User(String name, String email, String password) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome de usuário é obrigatório.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
        }
        if (password == null || email.isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória");
        }
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

