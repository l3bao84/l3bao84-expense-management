package com.baold.expense_management.model.mysql;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer status;

    private String icon;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
