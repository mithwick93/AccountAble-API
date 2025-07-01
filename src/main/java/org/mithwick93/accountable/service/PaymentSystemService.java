package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.PaymentSystemRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.PaymentSystem;
import org.mithwick93.accountable.model.enums.PaymentSystemType;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentSystemService {

    private final JwtUtil jwtUtil;

    private final PaymentSystemRepository paymentSystemRepository;

    @Transactional(readOnly = true)
    public List<PaymentSystem> getAll() {
        return paymentSystemRepository.findAllByUserId(jwtUtil.getAuthenticatedUserId());
    }

    @Transactional(readOnly = true)
    public PaymentSystem getById(int id) {
        return paymentSystemRepository.findByIdAndUserId(id, jwtUtil.getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("Payment with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public PaymentSystemType getPaymentSystemTypeById(int id) {
        return paymentSystemRepository.findTypeIdById(id, jwtUtil.getAuthenticatedUserId())
                .map(PaymentSystemType::fromId)
                .orElseThrow(NotFoundException.supplier("Payment with id " + id + " not found"));
    }

}
