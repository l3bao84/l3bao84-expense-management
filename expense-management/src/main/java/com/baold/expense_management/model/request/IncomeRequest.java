package com.baold.expense_management.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class IncomeRequest {
    private Long amount;
    private Long categoryId;
    private String note;
}
