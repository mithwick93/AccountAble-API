package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.PaymentSystemCreditRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.PaymentSystemCredit;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentSystemCreditService {

    private final PaymentSystemCreditRepository creditRepository;

    private final JwtUtil jwtUtil;

    public List<PaymentSystemCredit> getAll() {
        return creditRepository.findAllByUserId(jwtUtil.getAuthenticatedUserId());
    }

    public PaymentSystemCredit getById(int id) {
        return creditRepository.findByIdAndUserId(id, jwtUtil.getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("Credit payment with id " + id + " not found"));
    }

    public PaymentSystemCredit create(PaymentSystemCredit paymentSystemCredit) {
        paymentSystemCredit.setUserId(jwtUtil.getAuthenticatedUserId());
        return creditRepository.save(paymentSystemCredit);
    }

    public PaymentSystemCredit update(int id, PaymentSystemCredit paymentSystemCredit) {
        PaymentSystemCredit existingCredit = getById(id);
        paymentSystemCredit.setId(existingCredit.getId());
        paymentSystemCredit.setUserId(existingCredit.getUserId());

        return creditRepository.save(paymentSystemCredit);
    }

    public void delete(int id) {
        PaymentSystemCredit existingCredit = getById(id);
        creditRepository.delete(existingCredit);
    }

}
