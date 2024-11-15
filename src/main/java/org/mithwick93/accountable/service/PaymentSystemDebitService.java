package org.mithwick93.accountable.service;

import org.mithwick93.accountable.dal.repository.PaymentSystemDebitRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.PaymentSystemDebit;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentSystemDebitService {
    private final PaymentSystemDebitRepository debitRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public PaymentSystemDebitService(
            PaymentSystemDebitRepository debitRepository,
            JwtUtil jwtUtil
    ) {
        this.debitRepository = debitRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<PaymentSystemDebit> getAll() {
        return debitRepository.findAllByUserId(jwtUtil.getAuthenticatedUserId());
    }

    public PaymentSystemDebit getById(int id) {
        return debitRepository.findByIdAndUserId(id, jwtUtil.getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("Debit payment with id " + id + " not found"));
    }

    public PaymentSystemDebit create(PaymentSystemDebit paymentSystemDebit) {
        paymentSystemDebit.setUserId(jwtUtil.getAuthenticatedUserId());
        return debitRepository.save(paymentSystemDebit);
    }

    public PaymentSystemDebit update(int id, PaymentSystemDebit paymentSystemDebit) {
        PaymentSystemDebit existingDebit = getById(id);
        paymentSystemDebit.setId(existingDebit.getId());
        paymentSystemDebit.setUserId(existingDebit.getUserId());

        return debitRepository.save(paymentSystemDebit);
    }

    public void delete(int id) {
        PaymentSystemDebit existingDebit = getById(id);
        debitRepository.delete(existingDebit);
    }
}
