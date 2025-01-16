package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.PaymentSystemDebitRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.PaymentSystemDebit;
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
public class PaymentSystemDebitService {

    private final PaymentSystemDebitRepository debitRepository;

    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public List<PaymentSystemDebit> getAll() {
        return debitRepository.findAllByUserId(jwtUtil.getAuthenticatedUserId());
    }

    @Transactional(readOnly = true)
    public PaymentSystemDebit getById(int id) {
        return debitRepository.findByIdAndUserId(id, jwtUtil.getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("Debit payment with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<PaymentSystemDebit> getByAssetId(int assetId) {
        return debitRepository.findByAssetIdAndUserId(assetId, jwtUtil.getAuthenticatedUserId());
    }

    @CachePut(value = "payment_system_debit_cache", key = "#result.id", unless = "#result == null")
    public PaymentSystemDebit create(PaymentSystemDebit paymentSystemDebit) {
        paymentSystemDebit.setUserId(jwtUtil.getAuthenticatedUserId());
        return debitRepository.save(paymentSystemDebit);
    }

    @CachePut(value = "payment_system_debit_cache", key = "#result.id", unless = "#result == null")
    public PaymentSystemDebit update(int id, PaymentSystemDebit paymentSystemDebit) {
        PaymentSystemDebit existingDebit = getById(id);

        existingDebit.setName(paymentSystemDebit.getName());
        existingDebit.setDescription(paymentSystemDebit.getDescription());
        existingDebit.setCurrency(paymentSystemDebit.getCurrency());
        existingDebit.setAssetId(paymentSystemDebit.getAssetId());
        existingDebit.setDailyLimit(paymentSystemDebit.getDailyLimit());
        existingDebit.setCardHolderName(paymentSystemDebit.getCardHolderName());
        existingDebit.setCardNumber(paymentSystemDebit.getCardNumber());
        existingDebit.setSecurityCode(paymentSystemDebit.getSecurityCode());
        existingDebit.setExpiryDate(paymentSystemDebit.getExpiryDate());
        existingDebit.setAdditionalNote(paymentSystemDebit.getAdditionalNote());
        existingDebit.setActive(paymentSystemDebit.isActive());

        return debitRepository.save(existingDebit);
    }

    @CacheEvict(value = "payment_system_debit_cache", key = "#id")
    public void delete(int id) {
        PaymentSystemDebit existingDebit = getById(id);
        debitRepository.delete(existingDebit);
    }

}
