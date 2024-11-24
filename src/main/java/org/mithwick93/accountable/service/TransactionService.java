package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.TransactionCategoryRepository;
import org.mithwick93.accountable.dal.repository.TransactionRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.Transaction;
import org.mithwick93.accountable.model.TransactionCategory;
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

    private final TransactionCategoryRepository transactionCategoryRepository;

    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionById(long id) {
        return transactionRepository.findById(id)
                .orElseThrow(NotFoundException.supplier("Transaction with id " + id + " not found"));
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(long id, Transaction updatedTransaction) {
        Transaction existingTransaction = getTransactionById(id);

        existingTransaction.setName(updatedTransaction.getName());
        existingTransaction.setDescription(updatedTransaction.getDescription());
        existingTransaction.setType(updatedTransaction.getType());
        existingTransaction.setCategoryId(updatedTransaction.getCategoryId());
        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setCurrency(updatedTransaction.getCurrency());
        existingTransaction.setDate(updatedTransaction.getDate());
        existingTransaction.setFromAssetId(updatedTransaction.getFromAssetId());
        existingTransaction.setToAssetId(updatedTransaction.getToAssetId());
        existingTransaction.setFromPaymentSystemId(updatedTransaction.getFromPaymentSystemId());
        existingTransaction.setToPaymentSystemId(updatedTransaction.getToPaymentSystemId());
        existingTransaction.setUserId(updatedTransaction.getUserId());
        existingTransaction.setSharedTransactions(updatedTransaction.getSharedTransactions());

        return transactionRepository.save(existingTransaction);
    }

    public void deleteTransaction(long id) {
        Transaction existingTransaction = getTransactionById(id);
        transactionRepository.delete(existingTransaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionCategory> getAllTransactionCategories() {
        return transactionCategoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public TransactionCategory getTransactionCategoryById(int id) {
        return transactionCategoryRepository.findById(id)
                .orElseThrow(NotFoundException.supplier("Transaction category with id " + id + " not found"));
    }

    public TransactionCategory createTransactionCategory(TransactionCategory transactionCategory) {
        transactionCategory.setUserId(jwtUtil.getAuthenticatedUserId());

        return transactionCategoryRepository.save(transactionCategory);
    }

    public TransactionCategory updateTransactionCategory(int id, TransactionCategory updatedTransactionCategory) {
        TransactionCategory existingTransactionCategory = getTransactionCategoryById(id);

        existingTransactionCategory.setName(updatedTransactionCategory.getName());
        existingTransactionCategory.setType(updatedTransactionCategory.getType());
        existingTransactionCategory.setUserId(jwtUtil.getAuthenticatedUserId());

        return transactionCategoryRepository.save(existingTransactionCategory);
    }

    public void deleteTransactionCategory(int id) {
        TransactionCategory existingTransactionCategory = getTransactionCategoryById(id);
        transactionCategoryRepository.delete(existingTransactionCategory);
    }

}
