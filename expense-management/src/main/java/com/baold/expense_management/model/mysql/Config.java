package com.baold.expense_management.model.mysql;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "config")
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String value;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
