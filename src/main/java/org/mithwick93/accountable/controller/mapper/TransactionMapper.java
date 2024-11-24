package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.controller.dto.request.SharedTransactionRequest;
import org.mithwick93.accountable.controller.dto.request.TransactionCategoryRequest;
import org.mithwick93.accountable.controller.dto.request.TransactionRequest;
import org.mithwick93.accountable.controller.dto.response.SharedTransactionResponse;
import org.mithwick93.accountable.controller.dto.response.TransactionCategoryResponse;
import org.mithwick93.accountable.controller.dto.response.TransactionResponse;
import org.mithwick93.accountable.model.SharedTransaction;
import org.mithwick93.accountable.model.Transaction;
import org.mithwick93.accountable.model.TransactionCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper extends BaseMapper {

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract TransactionCategory toTransactionCategory(TransactionCategoryRequest request);

    public abstract TransactionCategoryResponse toTransactionCategoryResponse(TransactionCategory transactionCategory);

    public abstract List<TransactionCategoryResponse> toTransactionCategoryResponses(List<TransactionCategory> transactionCategories);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract Transaction toTransaction(TransactionRequest request);

    public abstract TransactionResponse toTransactionResponse(Transaction transaction);

    public abstract List<TransactionResponse> toTransactionResponses(List<Transaction> transactions);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "transaction", ignore = true)
    public abstract SharedTransaction toSharedTransaction(SharedTransactionRequest request);

    public abstract List<SharedTransaction> toSharedTransactions(List<SharedTransactionRequest> requests);

    public abstract SharedTransactionResponse toSharedTransactionResponse(SharedTransaction sharedTransaction);

    public abstract List<SharedTransactionResponse> toSharedTransactionResponses(List<SharedTransaction> sharedTransactions);

}
