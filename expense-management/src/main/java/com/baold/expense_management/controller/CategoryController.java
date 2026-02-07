package com.baold.expense_management.controller;

import com.baold.expense_management.constants.ComponentUrl;
import com.baold.expense_management.model.request.CreateCategoryRequest;
import com.baold.expense_management.model.request.IncomeRequest;
import com.baold.expense_management.model.response.DataResponse;
import com.baold.expense_management.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ComponentUrl.Category.CATEGORY_URL)
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<DataResponse> getCateInfo() {
        return ResponseEntity.ok().body(categoryService.getCateInfo());
    }

    @PostMapping(ComponentUrl.Category.CREATE_CATEGORY)
    public ResponseEntity<DataResponse> createCategory(@RequestBody CreateCategoryRequest request) {
        return ResponseEntity.ok().body(categoryService.createCategory(request));
    }

    @PostMapping(ComponentUrl.Category.UPDATE_CATEGORY)
    public ResponseEntity<DataResponse> updateCategory(@RequestBody CreateCategoryRequest request) {
        return ResponseEntity.ok().body(categoryService.updateCategory(request));
    }

    @PostMapping(ComponentUrl.Category.INCOME_URL)
    public ResponseEntity<DataResponse> getCategory(@RequestBody IncomeRequest request) {
        return ResponseEntity.ok().body(categoryService.incomeMoney(request));
    }

    @PostMapping(ComponentUrl.Category.REMOVE_CATEGORY)
    public ResponseEntity<DataResponse> removeCategory(@RequestParam("id") Long id) {
        return ResponseEntity.ok().body(categoryService.removeCate(id));
    }

    @PostMapping(ComponentUrl.Category.EXPENSE_URL)
    public ResponseEntity<DataResponse> expense(@RequestBody IncomeRequest request) {
        return ResponseEntity.ok().body(categoryService.expense(request));
    }
}
