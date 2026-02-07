package com.baold.expense_management.model.response;

import com.baold.expense_management.model.mysql.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Long id;
    private Long categoryId;
    private Long amount;
    private TransactionType transactionType;
    private String note;
    private LocalDateTime transactionDate;
}
