package com.baold.expense_management.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfoResponse {

    private Long id;
    private String name;
    private BigDecimal balance;
    private Integer status;
    private String icon;
    private BigDecimal expense;
    private String description;

    public CategoryInfoResponse(Long id, String name, BigDecimal balance, String icon,  BigDecimal expense, String description) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.expense = expense;
        this.description = description;

        if (balance == null || balance.compareTo(BigDecimal.ZERO) == 0) {
            this.balance = BigDecimal.ZERO;
            this.status = 1;
        } else if (balance.compareTo(BigDecimal.ZERO) > 0) {
            this.balance = balance;
            this.status = 2;
        } else {
            this.balance = balance.abs();
            this.status = 0;
        }
    }
}
