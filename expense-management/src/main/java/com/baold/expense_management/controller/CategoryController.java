package com.baold.expense_management.controller;

import com.baold.expense_management.constants.ComponentUrl;
import com.baold.expense_management.model.request.IncomeRequest;
import com.baold.expense_management.model.response.DataResponse;
import com.baold.expense_management.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ComponentUrl.Category.CATEGORY_URL)
public class CategoryController {

    private final CategoryService categoryService;

    @PatchMapping(ComponentUrl.Category.INCOME_URL)
    public ResponseEntity<DataResponse> getCategory(@RequestBody IncomeRequest request) {
        return ResponseEntity.ok().body(categoryService.incomeMoney(request));
    }
}
