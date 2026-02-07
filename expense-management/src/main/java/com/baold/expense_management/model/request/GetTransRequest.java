package com.baold.expense_management.model.request;

import lombok.Data;

@Data
public class GetTransRequest {

    private Long categoryId;
    private Integer transactionType;
    private String from;
    private String to;
}
