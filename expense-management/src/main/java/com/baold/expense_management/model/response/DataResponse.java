package com.baold.expense_management.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataResponse {
    private Long errorCode;
    private String message;
    private Object data;

    public DataResponse(Long errorCode, String message, Object data) {
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public DataResponse(Long errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
