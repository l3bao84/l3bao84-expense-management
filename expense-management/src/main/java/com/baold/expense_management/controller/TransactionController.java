package com.baold.expense_management.controller;

import com.baold.expense_management.constants.ComponentUrl;
import com.baold.expense_management.model.request.GetTransRequest;
import com.baold.expense_management.model.request.UpdateTransactionRequest;
import com.baold.expense_management.model.response.DataResponse;
import com.baold.expense_management.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ComponentUrl.Transaction.TRANSACTION_URL)
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<DataResponse> getTransaction(@RequestBody GetTransRequest request) {
        return ResponseEntity.ok().body(transactionService.getTransaction(request));
    }

    @PostMapping(value = ComponentUrl.Transaction.UPDATE_URL)
    public ResponseEntity<DataResponse> updateTransaction(@RequestBody UpdateTransactionRequest request) {
        return ResponseEntity.ok().body(transactionService.updateTransaction(request));
    }

    @PostMapping(value = ComponentUrl.Transaction.DELETE_URL)
    public ResponseEntity<DataResponse> delTransaction(@RequestParam("id") Long id) {
        return ResponseEntity.ok().body(transactionService.delTransaction(id));
    }
}
