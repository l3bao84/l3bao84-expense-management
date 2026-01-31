package com.baold.expense_management.service.impl;

import com.baold.expense_management.model.mysql.Category;
import com.baold.expense_management.model.request.IncomeRequest;
import com.baold.expense_management.model.response.DataResponse;
import com.baold.expense_management.repository.CategoryRepository;
import com.baold.expense_management.service.CategoryService;
import com.baold.expense_management.utils.DataUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

import static com.baold.expense_management.constants.CommonConstants.*;
import static com.baold.expense_management.constants.MessageConstants.*;
import static com.baold.expense_management.constants.ResponseCodeConstants.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public DataResponse incomeMoney(IncomeRequest request) {
        if (DataUtils.isNullOrEmpty(request.getAmount())) {
            return new DataResponse(ERROR_CODE_1, MessageFormat.format(MISSING_PARAM, AMOUNT));
        }
        if (DataUtils.isNullOrEmpty(request.getCategoryId())) {
            return new DataResponse(ERROR_CODE_1, MessageFormat.format(MISSING_PARAM, CATEGORY_ID));
        }
        if (request.getAmount() <= 0) {
            return new DataResponse(ERROR_CODE_2, INVALID_CHARGE_AMOUNT);
        }
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (DataUtils.isNullOrEmpty(category)) {
            return new DataResponse(ERROR_CODE_3, INVALID_CATEGORY);
        }
        return null;
    }
}
