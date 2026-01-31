package com.baold.expense_management.service;

import com.baold.expense_management.model.request.IncomeRequest;
import com.baold.expense_management.model.response.DataResponse;

public interface CategoryService {

    DataResponse incomeMoney(IncomeRequest request);
}
