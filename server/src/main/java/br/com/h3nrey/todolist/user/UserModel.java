package br.com.h3nrey.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserModel {
    @Id
    @GeneratedValue(generator = "UUID")
    public UUID id;

    @Column(unique = true)
    private String username;
    private String email;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
