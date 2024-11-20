package org.mithwick93.accountable.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.request.PaymentSystemCreditRequest;
import org.mithwick93.accountable.controller.dto.response.PaymentSystemCreditResponse;
import org.mithwick93.accountable.controller.mapper.PaymentSystemMapper;
import org.mithwick93.accountable.model.PaymentSystemCredit;
import org.mithwick93.accountable.service.PaymentSystemCreditService;
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
@RequestMapping("/api/payment-systems/credits")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentSystemCreditController {

    private final PaymentSystemCreditService creditService;

    private final PaymentSystemMapper paymentSystemMapper;

    @GetMapping
    public ResponseEntity<List<PaymentSystemCreditResponse>> getAll() {
        List<PaymentSystemCredit> paymentSystemCredits = creditService.getAll();
        List<PaymentSystemCreditResponse> paymentSystemCreditResponses = paymentSystemMapper.toPaymentSystemCreditResponses(paymentSystemCredits);
        return ResponseEntity.ok(paymentSystemCreditResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentSystemCreditResponse> getById(@PathVariable int id) {
        PaymentSystemCredit paymentSystemCredit = creditService.getById(id);
        PaymentSystemCreditResponse paymentSystemCreditResponse = paymentSystemMapper.toPaymentSystemCreditResponse(paymentSystemCredit);
        return ResponseEntity.ok(paymentSystemCreditResponse);
    }

    @PostMapping
    public ResponseEntity<PaymentSystemCreditResponse> create(@Valid @RequestBody PaymentSystemCreditRequest request) {
        PaymentSystemCredit newPaymentSystemCredit = paymentSystemMapper.toPaymentSystemCredit(request);
        PaymentSystemCredit createdPaymentSystemCredit = creditService.create(newPaymentSystemCredit);
        PaymentSystemCreditResponse createdPaymentSystemCreditResponse = paymentSystemMapper.toPaymentSystemCreditResponse(createdPaymentSystemCredit);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaymentSystemCreditResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentSystemCreditResponse> update(@PathVariable int id, @Valid @RequestBody PaymentSystemCreditRequest request) {
        PaymentSystemCredit updatePaymentSystemCredit = paymentSystemMapper.toPaymentSystemCredit(request);
        PaymentSystemCredit updatedPaymentSystemCredit = creditService.update(id, updatePaymentSystemCredit);
        PaymentSystemCreditResponse updatedPaymentSystemCreditResponse = paymentSystemMapper.toPaymentSystemCreditResponse(updatedPaymentSystemCredit);

        return ResponseEntity.ok(updatedPaymentSystemCreditResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        creditService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
