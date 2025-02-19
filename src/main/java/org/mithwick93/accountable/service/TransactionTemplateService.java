package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.TransactionTemplateRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.TransactionTemplate;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionTemplateService {

    private final TransactionTemplateRepository transactionTemplateRepository;

    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public List<TransactionTemplate> getAll() {
        return transactionTemplateRepository.findAllByUserId(jwtUtil.getAuthenticatedUserId());
    }

    @Transactional(readOnly = true)
    public TransactionTemplate getById(int id) {
        return transactionTemplateRepository.findByIdAndUserId(id, jwtUtil.getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("Transaction template with id " + id + " not found"));
    }

    public TransactionTemplate create(TransactionTemplate transactionTemplate) {
        transactionTemplate.setUserId(jwtUtil.getAuthenticatedUserId());
        return transactionTemplateRepository.save(transactionTemplate);
    }

    public TransactionTemplate update(int id, TransactionTemplate transactionTemplate) {
        TransactionTemplate existingTransactionTemplate = getById(id);

        existingTransactionTemplate.setName(transactionTemplate.getName());
        existingTransactionTemplate.setDescription(transactionTemplate.getDescription());
        existingTransactionTemplate.setType(transactionTemplate.getType());
        existingTransactionTemplate.setCategoryId(transactionTemplate.getCategoryId());
        existingTransactionTemplate.setAmount(transactionTemplate.getAmount());
        existingTransactionTemplate.setCurrency(transactionTemplate.getCurrency());
        existingTransactionTemplate.setFromAssetId(transactionTemplate.getFromAssetId());
        existingTransactionTemplate.setToAssetId(transactionTemplate.getToAssetId());
        existingTransactionTemplate.setFromPaymentSystemId(transactionTemplate.getFromPaymentSystemId());
        existingTransactionTemplate.setToPaymentSystemId(transactionTemplate.getToPaymentSystemId());
        existingTransactionTemplate.setFrequency(transactionTemplate.getFrequency());
        existingTransactionTemplate.setDayOfWeek(transactionTemplate.getDayOfWeek());
        existingTransactionTemplate.setDayOfMonth(transactionTemplate.getDayOfMonth());
        existingTransactionTemplate.setMonthOfYear(transactionTemplate.getMonthOfYear());
        existingTransactionTemplate.setStartDate(transactionTemplate.getStartDate());
        existingTransactionTemplate.setEndDate(transactionTemplate.getEndDate());

        return transactionTemplateRepository.save(existingTransactionTemplate);
    }

    public void delete(int id) {
        TransactionTemplate existingTransactionTemplate = getById(id);
        transactionTemplateRepository.delete(existingTransactionTemplate);
    }

}
