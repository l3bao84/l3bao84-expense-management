package com.baold.expense_management.repository;

import com.baold.expense_management.model.mysql.Transaction;
import com.baold.expense_management.model.mysql.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TransactionSpecification {

    public static Specification<Transaction> byCategory(Long categoryId) {
        return (root, query, cb) ->
                cb.equal(root.get("categoryId"), categoryId);
    }

    public static Specification<Transaction> byType(TransactionType type) {
        return (root, query, cb) ->
                cb.equal(root.get("type"), type);
    }

    public static Specification<Transaction> byDateRange(
            LocalDateTime from,
            LocalDateTime to
    ) {
        return (root, query, cb) ->
                cb.between(root.get("transactionDate"), from, to);
    }
}
