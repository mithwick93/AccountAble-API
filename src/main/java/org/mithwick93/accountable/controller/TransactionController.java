package org.mithwick93.accountable.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.request.MarkTransactionsPaidRequest;
import org.mithwick93.accountable.controller.dto.request.TransactionRequest;
import org.mithwick93.accountable.controller.dto.request.TransactionSearchRequest;
import org.mithwick93.accountable.controller.dto.response.MessageResponse;
import org.mithwick93.accountable.controller.dto.response.TransactionResponse;
import org.mithwick93.accountable.controller.mapper.TransactionMapper;
import org.mithwick93.accountable.model.Transaction;
import org.mithwick93.accountable.model.TransactionSearch;
import org.mithwick93.accountable.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import static org.mithwick93.accountable.model.TransactionType.TRANSACTION_TYPES;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAll() {
        List<Transaction> allTransactions = transactionService.getAllTransactions();
        List<TransactionResponse> transactionResponses = transactionMapper.toTransactionResponses(allTransactions);
        return ResponseEntity.ok(transactionResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getById(@PathVariable long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        TransactionResponse transactionResponse = transactionMapper.toTransactionResponse(transaction);
        return ResponseEntity.ok(transactionResponse);
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody TransactionRequest request) {
        Transaction newTransaction = transactionMapper.toTransaction(request);
        Transaction createdTransaction = transactionService.createTransaction(newTransaction, request.updateAccounts());
        TransactionResponse transactionResponse = transactionMapper.toTransactionResponse(createdTransaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> update(@PathVariable long id, @Valid @RequestBody TransactionRequest request) {
        Transaction updateTransaction = transactionMapper.toTransaction(request);
        Transaction updatedTransaction = transactionService.updateTransaction(id, updateTransaction, request.updateAccounts());
        TransactionResponse transactionResponse = transactionMapper.toTransactionResponse(updatedTransaction);
        return ResponseEntity.ok(transactionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getTypes() {
        return ResponseEntity.ok(TRANSACTION_TYPES);
    }

    @PutMapping("/mark-as-paid")
    public ResponseEntity<MessageResponse> markTransactionsAsPaid(@RequestBody MarkTransactionsPaidRequest request) {
        int updatedRowCount = transactionService.markTransactionsAsPaid(request.sharedTransactionIds());
        return ResponseEntity.ok(MessageResponse.of(updatedRowCount + " shared transactions marked as paid"));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<TransactionResponse>> searchTransactions(
            @RequestBody TransactionSearchRequest searchRequest,
            Pageable pageable
    ) {
        TransactionSearch transactionSearch = transactionMapper.toTransactionSearch(searchRequest);

        Page<TransactionResponse> transactionResponsesPage = transactionService.searchTransactions(transactionSearch, pageable)
                .map(transactionMapper::toTransactionResponse);

        return ResponseEntity.ok(transactionResponsesPage);
    }

}
