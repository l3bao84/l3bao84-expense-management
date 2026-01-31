package com.baold.expense_management.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataResponse {
    private Long errorCode;
    private String message;
    private Object data;

    public DataResponse(Long errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
