package org.mithwick93.accountable.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.request.TransactionTemplateRequest;
import org.mithwick93.accountable.controller.dto.response.TransactionTemplateResponse;
import org.mithwick93.accountable.controller.mapper.TransactionTemplateMapper;
import org.mithwick93.accountable.model.TransactionTemplate;
import org.mithwick93.accountable.service.TransactionTemplateService;
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

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mithwick93.accountable.model.enums.Frequency.FREQUENCY_TYPES;

@RestController
@RequestMapping("/api/transactions/templates")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionTemplateController {

    private final TransactionTemplateMapper transactionTemplateMapper;

    private final TransactionTemplateService transactionTemplateService;

    @GetMapping
    public ResponseEntity<List<TransactionTemplateResponse>> getAll() {
        List<TransactionTemplate> allTransactionsTemplates = transactionTemplateService.getAll();
        List<TransactionTemplateResponse> transactionTemplateResponses = transactionTemplateMapper.toTransactionTemplateResponses(allTransactionsTemplates);
        return ResponseEntity.ok(transactionTemplateResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionTemplateResponse> getById(@PathVariable int id) {
        TransactionTemplate transactionTemplate = transactionTemplateService.getById(id);
        TransactionTemplateResponse transactionTemplateResponse = transactionTemplateMapper.toTransactionTemplateResponse(transactionTemplate);
        return ResponseEntity.ok(transactionTemplateResponse);
    }

    @PostMapping
    public ResponseEntity<TransactionTemplateResponse> create(@Valid @RequestBody TransactionTemplateRequest request) {
        TransactionTemplate newTransactionTemplate = transactionTemplateMapper.toTransactionTemplate(request);
        TransactionTemplate createdTransactionTemplate = transactionTemplateService.create(newTransactionTemplate);
        TransactionTemplateResponse transactionTemplateResponse = transactionTemplateMapper.toTransactionTemplateResponse(createdTransactionTemplate);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionTemplateResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionTemplateResponse> update(@PathVariable int id, @Valid @RequestBody TransactionTemplateRequest request) {
        TransactionTemplate updateTransactionTemplate = transactionTemplateMapper.toTransactionTemplate(request);
        TransactionTemplate updatedTransactionTemplate = transactionTemplateService.update(id, updateTransactionTemplate);
        TransactionTemplateResponse transactionTemplateResponse = transactionTemplateMapper.toTransactionTemplateResponse(updatedTransactionTemplate);
        return ResponseEntity.ok(transactionTemplateResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        transactionTemplateService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/frequencies")
    public ResponseEntity<List<String>> getFrequencies() {
        return ResponseEntity.ok(FREQUENCY_TYPES);
    }

    @GetMapping("/days-of-week")
    public ResponseEntity<List<String>> getDaysOfWeek() {
        List<String> daysOfWeek = Arrays.stream(DayOfWeek.values())
                .map(DayOfWeek::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(daysOfWeek);
    }

}
