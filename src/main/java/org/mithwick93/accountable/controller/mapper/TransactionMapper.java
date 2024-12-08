package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.cache.TransactionCache;
import org.mithwick93.accountable.controller.dto.request.SharedTransactionRequest;
import org.mithwick93.accountable.controller.dto.request.TransactionCategoryRequest;
import org.mithwick93.accountable.controller.dto.request.TransactionRequest;
import org.mithwick93.accountable.controller.dto.request.TransactionSearchRequest;
import org.mithwick93.accountable.controller.dto.response.SharedTransactionResponse;
import org.mithwick93.accountable.controller.dto.response.TransactionCategoryResponse;
import org.mithwick93.accountable.controller.dto.response.TransactionResponse;
import org.mithwick93.accountable.model.SharedTransaction;
import org.mithwick93.accountable.model.Transaction;
import org.mithwick93.accountable.model.TransactionCategory;
import org.mithwick93.accountable.model.TransactionSearch;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper extends BaseMapper {

    @Autowired
    private TransactionCache transactionCache;

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract TransactionCategory toTransactionCategory(TransactionCategoryRequest request);

    @Mapping(target = "user", expression = "java(mapUser(transactionCategory.getUserId()))")
    public abstract TransactionCategoryResponse toTransactionCategoryResponse(TransactionCategory transactionCategory);

    public abstract List<TransactionCategoryResponse> toTransactionCategoryResponses(List<TransactionCategory> transactionCategories);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract Transaction toTransaction(TransactionRequest request);

    @Mapping(target = "user", expression = "java(mapUser(transaction.getUserId()))")
    @Mapping(target = "category", expression = "java(mapTransactionCategory(transaction.getCategoryId()))")
    public abstract TransactionResponse toTransactionResponse(Transaction transaction);

    public abstract List<TransactionResponse> toTransactionResponses(List<Transaction> transactions);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "transaction", ignore = true)
    public abstract SharedTransaction toSharedTransaction(SharedTransactionRequest request);

    public abstract List<SharedTransaction> toSharedTransactions(List<SharedTransactionRequest> requests);

    @Mapping(target = "user", expression = "java(mapUser(sharedTransaction.getUserId()))")
    public abstract SharedTransactionResponse toSharedTransactionResponse(SharedTransaction sharedTransaction);

    public abstract List<SharedTransactionResponse> toSharedTransactionResponses(List<SharedTransaction> sharedTransactions);

    public TransactionSearch toTransactionSearch(TransactionSearchRequest request) {
        return new TransactionSearch(
                Optional.ofNullable(request.userIds()),
                Optional.ofNullable(request.dateFrom()),
                Optional.ofNullable(request.dateTo()),
                Optional.ofNullable(request.types()),
                Optional.ofNullable(request.categoryIds()),
                Optional.ofNullable(request.fromAssetIds()),
                Optional.ofNullable(request.toAssetIds()),
                Optional.ofNullable(request.fromPaymentSystemIds()),
                Optional.ofNullable(request.toPaymentSystemIds()),
                Optional.ofNullable(request.hasPendingSettlements()),
                Optional.ofNullable(request.hasSharedTransactions())
        );
    }

    protected TransactionCategoryResponse mapTransactionCategory(Integer categoryId) {
        TransactionCategory category = transactionCache.getTransactionCategory(categoryId);
        return toTransactionCategoryResponse(category);
    }

}
