package org.mithwick93.accountable.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.request.PaymentSystemDebitRequest;
import org.mithwick93.accountable.controller.dto.response.PaymentSystemDebitResponse;
import org.mithwick93.accountable.controller.mapper.PaymentSystemMapper;
import org.mithwick93.accountable.model.PaymentSystemDebit;
import org.mithwick93.accountable.service.PaymentSystemDebitService;
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
@RequestMapping("/api/payment-systems/debits")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentSystemDebitController {

    private final PaymentSystemDebitService debitService;

    private final PaymentSystemMapper paymentSystemMapper;

    @GetMapping
    public ResponseEntity<List<PaymentSystemDebitResponse>> getAll() {
        List<PaymentSystemDebit> paymentSystemDebits = debitService.getAll();
        List<PaymentSystemDebitResponse> paymentSystemDebitResponses = paymentSystemMapper.toPaymentSystemDebitResponses(paymentSystemDebits);
        return ResponseEntity.ok(paymentSystemDebitResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentSystemDebitResponse> getById(@PathVariable int id) {
        PaymentSystemDebit paymentSystemDebit = debitService.getById(id);
        PaymentSystemDebitResponse paymentSystemDebitResponse = paymentSystemMapper.toPaymentSystemDebitResponse(paymentSystemDebit);
        return ResponseEntity.ok(paymentSystemDebitResponse);
    }

    @PostMapping
    public ResponseEntity<PaymentSystemDebitResponse> create(@Valid @RequestBody PaymentSystemDebitRequest request) {
        PaymentSystemDebit newPaymentSystemDebit = paymentSystemMapper.toPaymentSystemDebit(request);
        PaymentSystemDebit createdPaymentSystemDebit = debitService.create(newPaymentSystemDebit);
        PaymentSystemDebitResponse createdPaymentSystemDebitResponse = paymentSystemMapper.toPaymentSystemDebitResponse(createdPaymentSystemDebit);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaymentSystemDebitResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentSystemDebitResponse> update(@PathVariable int id, @Valid @RequestBody PaymentSystemDebitRequest request) {
        PaymentSystemDebit updatePaymentSystemDebit = paymentSystemMapper.toPaymentSystemDebit(request);
        PaymentSystemDebit updatedPaymentSystemDebit = debitService.update(id, updatePaymentSystemDebit);
        PaymentSystemDebitResponse updatedPaymentSystemDebitResponse = paymentSystemMapper.toPaymentSystemDebitResponse(updatedPaymentSystemDebit);
        return ResponseEntity.ok(updatedPaymentSystemDebitResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        debitService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
