package com.baold.expense_management.model.request;

import lombok.Data;

@Data
public class UpdateTransactionRequest {

    private Long id;
    private Long categoryId;
    private Long amount;
    private Integer type;
    private String note;
    private String time;
}
