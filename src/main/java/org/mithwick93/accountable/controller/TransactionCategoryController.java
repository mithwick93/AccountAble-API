package org.mithwick93.accountable.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.request.TransactionCategoryRequest;
import org.mithwick93.accountable.controller.dto.response.TransactionCategoryResponse;
import org.mithwick93.accountable.controller.mapper.TransactionMapper;
import org.mithwick93.accountable.model.TransactionCategory;
import org.mithwick93.accountable.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions/categories")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionCategoryController {

    private final TransactionMapper transactionMapper;

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionCategoryResponse>> getAll() {
        List<TransactionCategory> allTransactionCategories = transactionService.getAllTransactionCategories();
        List<TransactionCategoryResponse> transactionCategoryResponses = transactionMapper.toTransactionCategoryResponses(allTransactionCategories);
        return ResponseEntity.ok(transactionCategoryResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionCategoryResponse> getById(@PathVariable int id) {
        TransactionCategory transactionCategory = transactionService.getTransactionCategoryById(id);
        TransactionCategoryResponse transactionCategoryResponse = transactionMapper.toTransactionCategoryResponse(transactionCategory);
        return ResponseEntity.ok(transactionCategoryResponse);
    }

    @PostMapping
    public ResponseEntity<TransactionCategoryResponse> create(@Valid @RequestBody TransactionCategoryRequest request) {
        TransactionCategory newTransactionCategory = transactionMapper.toTransactionCategory(request);
        TransactionCategory createdTransactionCategory = transactionService.createTransactionCategory(newTransactionCategory);
        TransactionCategoryResponse transactionCategoryResponse = transactionMapper.toTransactionCategoryResponse(createdTransactionCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionCategoryResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionCategoryResponse> update(@PathVariable int id, @Valid @RequestBody TransactionCategoryRequest request) {
        TransactionCategory updateTransactionCategory = transactionMapper.toTransactionCategory(request);
        TransactionCategory updatedTransactionCategory = transactionService.updateTransactionCategory(id, updateTransactionCategory);
        TransactionCategoryResponse transactionCategoryResponse = transactionMapper.toTransactionCategoryResponse(updatedTransactionCategory);
        return ResponseEntity.ok(transactionCategoryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        transactionService.deleteTransactionCategory(id);
        return ResponseEntity.noContent().build();
    }

}
