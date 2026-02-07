package com.baold.expense_management.service;

import com.baold.expense_management.model.request.GetTransRequest;
import com.baold.expense_management.model.request.IncomeRequest;
import com.baold.expense_management.model.request.UpdateTransactionRequest;
import com.baold.expense_management.model.response.DataResponse;
import lombok.Data;

public interface TransactionService {

    DataResponse getTransaction(GetTransRequest request);

    DataResponse updateTransaction(UpdateTransactionRequest request);

    DataResponse delTransaction(Long id);
}
