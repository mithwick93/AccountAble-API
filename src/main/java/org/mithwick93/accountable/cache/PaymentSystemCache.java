package org.mithwick93.accountable.cache;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.PaymentSystemCreditRepository;
import org.mithwick93.accountable.dal.repository.PaymentSystemDebitRepository;
import org.mithwick93.accountable.dal.repository.PaymentSystemRepository;
import org.mithwick93.accountable.model.PaymentSystemCredit;
import org.mithwick93.accountable.model.PaymentSystemDebit;
import org.mithwick93.accountable.model.PaymentSystemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentSystemCache {

    private final PaymentSystemRepository paymentSystemRepository;

    private final PaymentSystemCreditRepository paymentSystemCreditRepository;

    private final PaymentSystemDebitRepository paymentSystemDebitRepository;

    @Cacheable(value = "payment_system_type_cache", key = "#id", unless = "#result == null")
    public PaymentSystemType getPaymentSystemTypeById(int id) {
        return paymentSystemRepository.findTypeIdById(id)
                .map(PaymentSystemType::fromId)
                .orElse(null);
    }

    @Cacheable(value = "payment_system_credit_cache", key = "#id", unless = "#result == null")
    public PaymentSystemCredit getPaymentSystemCredit(int id) {
        return paymentSystemCreditRepository.findById(id)
                .orElse(null);
    }

    @Cacheable(value = "payment_system_debit_cache", key = "#id", unless = "#result == null")
    public PaymentSystemDebit getPaymentSystemDebit(int id) {
        return paymentSystemDebitRepository.findById(id)
                .orElse(null);
    }

}
