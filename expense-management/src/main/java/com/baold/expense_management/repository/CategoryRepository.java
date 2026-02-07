package com.baold.expense_management.repository;

import com.baold.expense_management.model.mysql.Category;
import com.baold.expense_management.model.response.CategoryInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> getCategoriesByStatus(Integer status);

    @Query(value = """
    SELECT 
        c.id AS categoryId,
        c.name AS categoryName,
        COALESCE(
            SUM(
                CASE
                    WHEN t.type = 'INCOME'
                    THEN t.amount
                    ELSE -t.amount
                END
            ), 0
        ) AS balance,
        c.icon as icon,
        COALESCE(
            SUM(
                CASE
                    WHEN t.type = 'EXPENSE'
                    THEN t.amount
                END
            ), 0
        ) as expense,
        c.description as description
    FROM category c
    LEFT JOIN transaction t
        ON t.category_id = c.id
        AND t.transaction_date BETWEEN :from AND :to
    WHERE c.status = 1
    GROUP BY c.id, c.name
    """, nativeQuery = true)
    List<CategoryInfoResponse> getCategoryBalanceCurrentMonth(LocalDateTime from, LocalDateTime to);
}
