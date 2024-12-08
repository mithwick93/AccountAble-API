package org.mithwick93.accountable.service;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.TransactionCategoryRepository;
import org.mithwick93.accountable.dal.repository.TransactionRepository;
import org.mithwick93.accountable.dal.repository.specification.TransactionSpecification;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.Transaction;
import org.mithwick93.accountable.model.TransactionCategory;
import org.mithwick93.accountable.model.TransactionSearch;
import org.mithwick93.accountable.service.helper.TransactionHelper;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionCategoryRepository transactionCategoryRepository;

    private final TransactionHelper transactionHelper;

    private final TransactionSpecification transactionSpecification;

    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Transaction> searchTransactions(TransactionSearch transactionSearch, Pageable pageable) {
        return transactionRepository.findAll(transactionSpecification.withFilters(transactionSearch), pageable);
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionById(long id) {
        return transactionRepository.findById(id)
                .orElseThrow(NotFoundException.supplier("Transaction with id " + id + " not found"));
    }

    public Transaction createTransaction(Transaction transaction, boolean shouldUpdateAccounts) {
        if (shouldUpdateAccounts) {
            transactionHelper.updateAccounts(transaction);
        }

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(long id, Transaction transaction, boolean shouldUpdateAccounts) {
        Transaction existingTransaction = getTransactionById(id);

        if (shouldUpdateAccounts) {
            transactionHelper.updateAccounts(existingTransaction, transaction);
        }

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

    public List<Transaction> markTransactionsAsPaid(int requestUserId, @NotEmpty List<Long> transactionIds) {
        transactionRepository.markTransactionsAsPaidByCreatorAndParticipant(requestUserId, transactionIds);
        return transactionRepository.findAllById(transactionIds);
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

    @CachePut(value = "transaction_category_cache", key = "#result.id", unless = "#result == null")
    public TransactionCategory createTransactionCategory(TransactionCategory transactionCategory) {
        transactionCategory.setUserId(jwtUtil.getAuthenticatedUserId());

        return transactionCategoryRepository.save(transactionCategory);
    }

    @CachePut(value = "transaction_category_cache", key = "#result.id", unless = "#result == null")
    public TransactionCategory updateTransactionCategory(int id, TransactionCategory transactionCategory) {
        TransactionCategory existingTransactionCategory = getTransactionCategoryById(id);

        existingTransactionCategory.setName(transactionCategory.getName());
        existingTransactionCategory.setType(transactionCategory.getType());
        existingTransactionCategory.setUserId(jwtUtil.getAuthenticatedUserId());

        return transactionCategoryRepository.save(existingTransactionCategory);
    }

    @CacheEvict(value = "transaction_category_cache", key = "#id")
    public void deleteTransactionCategory(int id) {
        TransactionCategory existingTransactionCategory = getTransactionCategoryById(id);
        transactionCategoryRepository.delete(existingTransactionCategory);
    }

}
