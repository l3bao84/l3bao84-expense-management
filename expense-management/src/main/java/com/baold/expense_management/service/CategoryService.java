package com.baold.expense_management.service;

import com.baold.expense_management.model.request.CreateCategoryRequest;
import com.baold.expense_management.model.request.IncomeRequest;
import com.baold.expense_management.model.response.DataResponse;

public interface CategoryService {

    DataResponse createCategory(CreateCategoryRequest request);

    DataResponse updateCategory(CreateCategoryRequest request);

    DataResponse incomeMoney(IncomeRequest request);

    DataResponse expense(IncomeRequest request);

    DataResponse removeCate(Long request);

    DataResponse getCateInfo();
}
