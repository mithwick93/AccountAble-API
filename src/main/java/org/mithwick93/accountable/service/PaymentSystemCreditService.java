package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.PaymentSystemCreditRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.PaymentSystemCredit;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentSystemCreditService {

    private final PaymentSystemCreditRepository creditRepository;

    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public List<PaymentSystemCredit> getAll() {
        return creditRepository.findAllByUserId(jwtUtil.getAuthenticatedUserId());
    }

    @Transactional(readOnly = true)
    public PaymentSystemCredit getById(int id) {
        return creditRepository.findByIdAndUserId(id, jwtUtil.getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("Credit payment with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<PaymentSystemCredit> getByLiabilityId(int liabilityId) {
        return creditRepository.findByLiabilityIdAndUserId(liabilityId, jwtUtil.getAuthenticatedUserId());
    }

    @CachePut(value = "payment_system_credit_cache", key = "#result.id", unless = "#result == null")
    public PaymentSystemCredit create(PaymentSystemCredit paymentSystemCredit) {
        paymentSystemCredit.setUserId(jwtUtil.getAuthenticatedUserId());
        return creditRepository.save(paymentSystemCredit);
    }

    @CachePut(value = "payment_system_credit_cache", key = "#result.id", unless = "#result == null")
    public PaymentSystemCredit update(int id, PaymentSystemCredit paymentSystemCredit) {
        PaymentSystemCredit existingCredit = getById(id);

        existingCredit.setName(paymentSystemCredit.getName());
        existingCredit.setDescription(paymentSystemCredit.getDescription());
        existingCredit.setCurrency(paymentSystemCredit.getCurrency());
        existingCredit.setLiabilityId(paymentSystemCredit.getLiabilityId());

        return creditRepository.save(existingCredit);
    }

    @CacheEvict(value = "payment_system_credit_cache", key = "#id")
    public void delete(int id) {
        PaymentSystemCredit existingCredit = getById(id);
        creditRepository.delete(existingCredit);
    }

}
