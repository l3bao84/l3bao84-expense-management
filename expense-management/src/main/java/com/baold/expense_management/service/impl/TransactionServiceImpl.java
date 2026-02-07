package com.baold.expense_management.service.impl;

import com.baold.expense_management.model.mysql.Category;
import com.baold.expense_management.model.mysql.Transaction;
import com.baold.expense_management.model.mysql.TransactionType;
import com.baold.expense_management.model.request.GetTransRequest;
import com.baold.expense_management.model.request.UpdateTransactionRequest;
import com.baold.expense_management.model.response.DataResponse;
import com.baold.expense_management.model.response.TransactionResponse;
import com.baold.expense_management.repository.CategoryRepository;
import com.baold.expense_management.repository.TransactionRepository;
import com.baold.expense_management.repository.TransactionSpecification;
import com.baold.expense_management.service.TransactionService;
import com.baold.expense_management.utils.DataUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import static com.baold.expense_management.constants.CommonConstants.*;
import static com.baold.expense_management.constants.MessageConstants.*;
import static com.baold.expense_management.constants.ResponseCodeConstants.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public DataResponse getTransaction(GetTransRequest request) {
        if(DataUtils.isNullOrEmpty(request.getCategoryId())) {
            return new DataResponse(ERROR_CODE_1, MessageFormat.format(MISSING_PARAM, CATEGORY_ID));
        }
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (DataUtils.isNullOrEmpty(category)) {
            return new DataResponse(ERROR_CODE_4, INVALID_CATEGORY);
        }else if(!DataUtils.safeEquals(category.getStatus(), 1)) {
            return new DataResponse(ERROR_CODE_4, INVALID_CATEGORY);
        }
        if(!DataUtils.isNullOrEmpty(request.getTransactionType())) {
            if(!Arrays.asList(0, 1).contains(request.getTransactionType())) {
                return new DataResponse(ERROR_CODE_1, ERROR_INVALID_TRANSACTION_TYPE);
            }
        }
        if(!DataUtils.isNullOrEmpty(request.getFrom())) {
            try {
                LocalDate fromDate = LocalDate.parse(request.getFrom(), DataUtils.FORMAT_DMY);
                if (fromDate.isAfter(LocalDate.now())) {
                    return new DataResponse(ERROR_CODE_2, ERROR_INVALID_TIME_MUST_BE_BEFORE_NOW);
                }
            } catch (DateTimeParseException e) {
                return new DataResponse(ERROR_CODE_2, ERROR_INVALID_TIME);
            }
        }
        if(!DataUtils.isNullOrEmpty(request.getTo())) {
            try {
                LocalDate toDate = LocalDate.parse(request.getTo(), DataUtils.FORMAT_DMY);
                if (toDate.isAfter(LocalDate.now())) {
                    return new DataResponse(ERROR_CODE_2, ERROR_INVALID_TIME_MUST_BE_BEFORE_NOW);
                }
            } catch (DateTimeParseException e) {
                return new DataResponse(ERROR_CODE_2, ERROR_INVALID_TIME);
            }
        }
        if (!DataUtils.isNullOrEmpty(request.getFrom())
                && !DataUtils.isNullOrEmpty(request.getTo())) {

            LocalDate fromDate = LocalDate.parse(request.getFrom(), DataUtils.FORMAT_DMY);
            LocalDate toDate = LocalDate.parse(request.getTo(), DataUtils.FORMAT_DMY);

            if (fromDate.isAfter(toDate)) {
                return new DataResponse(ERROR_CODE_2, ERROR_INVALID_TIME_FROM_MUST_BE_BEFORE_TO);
            }
        }

        Specification<Transaction> spec = Specification.where(TransactionSpecification.byCategory(request.getCategoryId()));
        LocalDateTime fromDateTime;
        LocalDateTime toDateTime;

        if (DataUtils.isNullOrEmpty(request.getFrom()) && DataUtils.isNullOrEmpty(request.getTo())) {
            YearMonth month = YearMonth.now();
            fromDateTime = month.atDay(1).atStartOfDay();
            toDateTime = month.atEndOfMonth().atTime(LocalTime.MAX);
        } else {
            fromDateTime = LocalDate.parse(request.getFrom(), DataUtils.FORMAT_DMY).atStartOfDay();
            toDateTime = LocalDate.parse(request.getTo(), DataUtils.FORMAT_DMY).atTime(LocalTime.MAX);
        }
        spec = spec.and(TransactionSpecification.byDateRange(fromDateTime, toDateTime));
        if(!DataUtils.isNullOrEmpty(request.getTransactionType())) {
            TransactionType transactionType = TransactionType.INCOME;
            if(DataUtils.safeEquals(request.getTransactionType(), 1)) {
                transactionType = TransactionType.EXPENSE;
            }
            spec = spec.and(TransactionSpecification.byType(transactionType));
        }
        List<Transaction> transactionList = transactionRepository.findAll(spec);
        if(transactionList.isEmpty()) {
            return new DataResponse(ERROR_CODE_3, NO_EMPTY_RESPONSE);
        }
        List<TransactionResponse> rs = transactionList.stream().map(transaction -> TransactionResponse.builder()
                .id(transaction.getId())
                .categoryId(transaction.getCategoryId())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .transactionType(transaction.getType())
                .note(transaction.getNote())
                .build()).toList();
        return new DataResponse(ERROR_CODE_0, LOOK_UP_SUCCESSFUL, rs);
    }

    @Override
    public DataResponse updateTransaction(UpdateTransactionRequest request) {
        if(DataUtils.isNullOrEmpty(request.getId())) {
            return new DataResponse(ERROR_CODE_1, MessageFormat.format(MISSING_PARAM, ID_PARAM));
        }
        Transaction transaction = transactionRepository.findById(request.getId()).orElse(null);
        if(DataUtils.isNullOrEmpty(transaction)) {
            return new DataResponse(ERROR_CODE_2, NO_TRANSACTION_INFORMATION);
        }
        if (!DataUtils.isNullOrEmpty(request.getAmount())) {
            if (request.getAmount() <= 0) {
                return new DataResponse(ERROR_CODE_3, INVALID_CHARGE_AMOUNT);
            }
            transaction.setAmount(request.getAmount());
        }
        if (!DataUtils.isNullOrEmpty(request.getNote())) {
            if (request.getNote().length() > 255) {
                return new DataResponse(ERROR_CODE_3, ERROR_DESC_EXCEED_LENGTH);
            }
            transaction.setNote(request.getNote());
        }
        if(!DataUtils.isNullOrEmpty(request.getCategoryId())) {
            Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
            if (DataUtils.isNullOrEmpty(category)) {
                return new DataResponse(ERROR_CODE_4, INVALID_CATEGORY);
            }else if(!DataUtils.safeEquals(category.getStatus(), 1)) {
                return new DataResponse(ERROR_CODE_4, INVALID_CATEGORY);
            }
            if(!DataUtils.safeEquals(transaction.getCategoryId(), category.getId())) {
                transaction.setCategoryId(category.getId());
            }
        }
        if(!DataUtils.isNullOrEmpty(request.getType())) {
            if(!Arrays.asList(0, 1).contains(request.getType())) {
                return new DataResponse(ERROR_CODE_1, ERROR_INVALID_TRANSACTION_TYPE);
            }
            TransactionType transactionType = TransactionType.INCOME;
            if(DataUtils.safeEquals(request.getType(), 1)) {
                transactionType = TransactionType.EXPENSE;
            }
            if(!DataUtils.safeEquals(transaction.getType(), transactionType)) {
                transaction.setType(transactionType);
            }
        }
        if(!DataUtils.isNullOrEmpty(request.getTime())) {
            try {
                LocalDate parsedTime = LocalDate.parse(request.getTime(), DataUtils.FORMAT_DMY);
                if (parsedTime.isAfter(LocalDate.now())) {
                    return new DataResponse(ERROR_CODE_2, ERROR_INVALID_TIME_MUST_BE_BEFORE_NOW);
                }
                LocalDateTime transactionTime = parsedTime.atTime(LocalTime.now());
                transaction.setTransactionDate(transactionTime);
            } catch (DateTimeParseException e) {
                return new DataResponse(ERROR_CODE_2, ERROR_INVALID_TIME);
            }
        }
        transaction.setUpdatedAt(LocalDateTime.now());
        try {
            transactionRepository.save(transaction);
        }catch (Exception e) {
            return new DataResponse(ERROR_CODE_3, ERROR_WHILE_PROCESSING_TRY_AGAIN);
        }
        return new DataResponse(ERROR_CODE_0, DEFAULT_SUCCESS);
    }

    @Override
    public DataResponse delTransaction(Long id) {
        if(DataUtils.isNullOrEmpty(id)) {
            return new DataResponse(ERROR_CODE_1, MessageFormat.format(MISSING_PARAM, ID_PARAM));
        }
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if(DataUtils.isNullOrEmpty(transaction)) {
            return new DataResponse(ERROR_CODE_2, NO_TRANSACTION_INFORMATION);
        }
        try {
            transactionRepository.delete(transaction);
        }catch (Exception e) {
            return new DataResponse(ERROR_CODE_3, ERROR_WHILE_PROCESSING_TRY_AGAIN);
        }
        return new DataResponse(ERROR_CODE_0, DEFAULT_SUCCESS);
    }
}
