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

    public Transaction updateTransaction(long id, Transaction transaction) {
        Transaction existingTransaction = getTransactionById(id);

        existingTransaction.setName(transaction.getName());
        existingTransaction.setDescription(transaction.getDescription());
        existingTransaction.setType(transaction.getType());
        existingTransaction.setCategoryId(transaction.getCategoryId());
        existingTransaction.setAmount(transaction.getAmount());
        existingTransaction.setCurrency(transaction.getCurrency());
        existingTransaction.setDate(transaction.getDate());
        existingTransaction.setFromAssetId(transaction.getFromAssetId());
        existingTransaction.setToAssetId(transaction.getToAssetId());
        existingTransaction.setFromPaymentSystemId(transaction.getFromPaymentSystemId());
        existingTransaction.setToPaymentSystemId(transaction.getToPaymentSystemId());
        existingTransaction.setUserId(transaction.getUserId());
        existingTransaction.setSharedTransactions(transaction.getSharedTransactions());

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

    public TransactionCategory updateTransactionCategory(int id, TransactionCategory transactionCategory) {
        TransactionCategory existingTransactionCategory = getTransactionCategoryById(id);

        existingTransactionCategory.setName(transactionCategory.getName());
        existingTransactionCategory.setType(transactionCategory.getType());
        existingTransactionCategory.setUserId(jwtUtil.getAuthenticatedUserId());

        return transactionCategoryRepository.save(existingTransactionCategory);
    }

    public void deleteTransactionCategory(int id) {
        TransactionCategory existingTransactionCategory = getTransactionCategoryById(id);
        transactionCategoryRepository.delete(existingTransactionCategory);
    }

}
