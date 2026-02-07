package com.baold.expense_management.service.impl;

import com.baold.expense_management.model.mysql.Category;
import com.baold.expense_management.model.mysql.Transaction;
import com.baold.expense_management.model.mysql.TransactionType;
import com.baold.expense_management.model.request.CreateCategoryRequest;
import com.baold.expense_management.model.request.IncomeRequest;
import com.baold.expense_management.model.response.DataResponse;
import com.baold.expense_management.repository.CategoryRepository;
import com.baold.expense_management.repository.TransactionRepository;
import com.baold.expense_management.service.CategoryService;
import com.baold.expense_management.utils.DataUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

import static com.baold.expense_management.constants.CommonConstants.*;
import static com.baold.expense_management.constants.MessageConstants.*;
import static com.baold.expense_management.constants.ResponseCodeConstants.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public DataResponse createCategory(CreateCategoryRequest request) {
        if (DataUtils.isNullOrEmpty(request.getName())) {
            return new DataResponse(ERROR_CODE_1, MessageFormat.format(MISSING_PARAM, NAME));
        }
        if(request.getName().length() > 255) {
            return new DataResponse(ERROR_CODE_2, ERROR_NAME_EXCEED_LENGTH);
        }
        if(!DataUtils.isNullOrEmpty(request.getDescription()) && request.getDescription().length() > 255) {
            return new DataResponse(ERROR_CODE_3, ERROR_DESC_EXCEED_LENGTH);
        }
        if (DataUtils.isNullOrEmpty(request.getIcon())) {
            return new DataResponse(ERROR_CODE_1, MessageFormat.format(MISSING_PARAM, ICON));
        }
        Category category = new Category();
        category.setName(request.getName());
        category.setIcon(request.getIcon());
        category.setDescription(DataUtils.safeToString(request.getDescription()));
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category.setStatus(1);

        Category savedCategory = categoryRepository.save(category);

        if(!DataUtils.isNullOrEmpty(savedCategory.getId())) {
            return new DataResponse(ERROR_CODE_0, DEFAULT_SUCCESS);
        }
        return new DataResponse(ERROR_CODE_4, ERROR_WHILE_PROCESSING_TRY_AGAIN);
    }

    @Override
    public DataResponse updateCategory(CreateCategoryRequest request) {
        if(DataUtils.isNullOrEmpty(request.getId())) {
            return new DataResponse(ERROR_CODE_1, MessageFormat.format(MISSING_PARAM, ID_PARAM));
        }
        Category category = categoryRepository.findById(request.getId()).orElse(null);
        if (DataUtils.isNullOrEmpty(category)) {
            return new DataResponse(ERROR_CODE_4, INVALID_CATEGORY);
        }else if(!DataUtils.safeEquals(category.getStatus(), 1)) {
            return new DataResponse(ERROR_CODE_4, INVALID_CATEGORY);
        }
        if(!DataUtils.safeEquals(category.getName(), request.getName())) {
            category.setName(DataUtils.safeToString(request.getName()));
        }
        if(!DataUtils.safeEquals(category.getDescription(), request.getDescription())) {
            category.setDescription(DataUtils.safeToString(request.getDescription()));
        }
        if(!DataUtils.safeEquals(category.getIcon(), request.getIcon())) {
            category.setIcon(DataUtils.safeToString(request.getIcon()));
        }
        category.setUpdatedAt(LocalDateTime.now());
        try {
            categoryRepository.save(category);
        }catch (Exception e) {
            return new DataResponse(ERROR_CODE_3, ERROR_WHILE_PROCESSING_TRY_AGAIN);
        }
        return new DataResponse(ERROR_CODE_0, DEFAULT_SUCCESS);
    }

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
        if(!DataUtils.isNullOrEmpty(request.getNote()) && request.getNote().length() > 255) {
            return new DataResponse(ERROR_CODE_3, ERROR_DESC_EXCEED_LENGTH);
        }
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (DataUtils.isNullOrEmpty(category)) {
            return new DataResponse(ERROR_CODE_4, INVALID_CATEGORY);
        }else if(!DataUtils.safeEquals(category.getStatus(), 1)) {
            return new DataResponse(ERROR_CODE_4, INVALID_CATEGORY);
        }
        Transaction createTransaction = new Transaction();
        createTransaction.setCategoryId(category.getId());
        createTransaction.setAmount(request.getAmount());
        createTransaction.setType(TransactionType.INCOME);
        createTransaction.setNote(DataUtils.safeToString(request.getNote()));
        createTransaction.setTransactionDate(LocalDateTime.now());
        createTransaction.setCreatedAt(LocalDateTime.now());
        createTransaction.setUpdatedAt(LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(createTransaction);
        if(!DataUtils.isNullOrEmpty(savedTransaction.getId())) {
            return new DataResponse(ERROR_CODE_0, MessageFormat.format(CHARGE_MONEY_SUCCESS, request.getAmount(), category.getName()));
        }
        return new DataResponse(ERROR_CODE_5, ERROR_WHILE_PROCESSING_TRY_AGAIN);
    }

    @Override
    public DataResponse expense(IncomeRequest request) {
        if (DataUtils.isNullOrEmpty(request.getAmount())) {
            return new DataResponse(ERROR_CODE_1, MessageFormat.format(MISSING_PARAM, AMOUNT));
        }
        if (DataUtils.isNullOrEmpty(request.getCategoryId())) {
            return new DataResponse(ERROR_CODE_1, MessageFormat.format(MISSING_PARAM, CATEGORY_ID));
        }
        if (request.getAmount() <= 0) {
            return new DataResponse(ERROR_CODE_2, INVALID_CHARGE_AMOUNT);
        }
        if(!DataUtils.isNullOrEmpty(request.getNote()) && request.getNote().length() > 255) {
            return new DataResponse(ERROR_CODE_3, ERROR_DESC_EXCEED_LENGTH);
        }
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (DataUtils.isNullOrEmpty(category)) {
            return new DataResponse(ERROR_CODE_4, INVALID_CATEGORY);
        }else if(!DataUtils.safeEquals(category.getStatus(), 1)) {
            return new DataResponse(ERROR_CODE_4, INVALID_CATEGORY);
        }
        Transaction createTransaction = new Transaction();
        createTransaction.setCategoryId(category.getId());
        createTransaction.setAmount(request.getAmount());
        createTransaction.setType(TransactionType.EXPENSE);
        createTransaction.setNote(DataUtils.safeToString(request.getNote()));
        createTransaction.setTransactionDate(LocalDateTime.now());
        createTransaction.setCreatedAt(LocalDateTime.now());
        createTransaction.setUpdatedAt(LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(createTransaction);
        if(!DataUtils.isNullOrEmpty(savedTransaction.getId())) {
            return new DataResponse(ERROR_CODE_0, MessageFormat.format(EXPENSE_MONEY_SUCCESS, request.getAmount(), category.getName()));
        }
        return new DataResponse(ERROR_CODE_5, ERROR_WHILE_PROCESSING_TRY_AGAIN);
    }

    @Override
    public DataResponse removeCate(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (DataUtils.isNullOrEmpty(category)) {
            return new DataResponse(ERROR_CODE_1, INVALID_CATEGORY);
        }else if(!DataUtils.safeEquals(category.getStatus(), 1)) {
            return new DataResponse(ERROR_CODE_1, INVALID_CATEGORY_STATUS);
        }
        category.setStatus(0);
        categoryRepository.save(category);
        return new DataResponse(ERROR_CODE_0, DEFAULT_SUCCESS);
    }

    @Override
    public DataResponse getCateInfo() {
        YearMonth month = YearMonth.now();
        LocalDateTime from = month.atDay(1).atStartOfDay();
        LocalDateTime to = month.atEndOfMonth().atTime(LocalTime.MAX);
        return new DataResponse(ERROR_CODE_0, DEFAULT_SUCCESS, categoryRepository.getCategoryBalanceCurrentMonth(from, to));
    }
}
