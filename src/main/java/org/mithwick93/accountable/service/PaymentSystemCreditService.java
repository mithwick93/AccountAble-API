package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.PaymentSystemCreditRepository;
import org.mithwick93.accountable.exception.BadRequestException;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.Currency;
import org.mithwick93.accountable.model.PaymentSystemCredit;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    public PaymentSystemCredit create(PaymentSystemCredit paymentSystemCredit) {
        paymentSystemCredit.setUserId(jwtUtil.getAuthenticatedUserId());
        return creditRepository.save(paymentSystemCredit);
    }

    public PaymentSystemCredit update(int id, PaymentSystemCredit paymentSystemCredit) {
        PaymentSystemCredit existingCredit = getById(id);

        existingCredit.setName(paymentSystemCredit.getName());
        existingCredit.setDescription(paymentSystemCredit.getDescription());
        existingCredit.setCurrency(paymentSystemCredit.getCurrency());
        existingCredit.setCreditLimit(paymentSystemCredit.getCreditLimit());
        existingCredit.setUtilizedAmount(paymentSystemCredit.getUtilizedAmount());
        existingCredit.setStatementDay(paymentSystemCredit.getStatementDay());
        existingCredit.setDueDay(paymentSystemCredit.getDueDay());

        return creditRepository.save(existingCredit);
    }

    public PaymentSystemCredit updateUtilizedAmount(int id, BigDecimal amount, Currency currency) {
        PaymentSystemCredit existingCredit = getById(id);

        if (!existingCredit.getCurrency().equals(currency)) {
            throw new BadRequestException("Currency " + currency + " does not match credit system currency " + existingCredit.getCurrency());
        }

        existingCredit.setUtilizedAmount(existingCredit.getUtilizedAmount().add(amount));

        return creditRepository.save(existingCredit);
    }

    public void delete(int id) {
        PaymentSystemCredit existingCredit = getById(id);
        creditRepository.delete(existingCredit);
    }

}
