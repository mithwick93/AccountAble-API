package org.mithwick93.accountable.cache;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.TransactionCategoryRepository;
import org.mithwick93.accountable.model.TransactionCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionCategoryCache {

    private final TransactionCategoryRepository transactionCategoryRepository;

    @Cacheable(value = "transaction_category_cache", key = "#id", unless = "#result == null")
    public TransactionCategory getTransactionCategory(int id) {
        return transactionCategoryRepository.findById(id)
                .orElse(null);
    }

}
