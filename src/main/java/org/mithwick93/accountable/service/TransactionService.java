package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.TransactionRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.Transaction;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public List<Transaction> getAll() {
        return transactionRepository.findAllByUserId(jwtUtil.getAuthenticatedUserId());
    }

    @Transactional(readOnly = true)
    public Transaction getById(long id) {
        return transactionRepository.findByIdAndUserId(id, jwtUtil.getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("Transaction with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public Transaction create(Transaction transaction) {
        transaction.setUserId(jwtUtil.getAuthenticatedUserId());
        return transactionRepository.save(transaction);
    }

    public Transaction update(long id, Transaction updatedTransaction) {
        Transaction existingTransaction = getById(id);

        updatedTransaction.setId(existingTransaction.getId());
        updatedTransaction.setUserId(existingTransaction.getUserId());

        return transactionRepository.save(updatedTransaction);
    }

    public void delete(long id) {
        Transaction existingTransaction = getById(id);
        transactionRepository.delete(existingTransaction);
    }

}
