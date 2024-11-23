package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mithwick93.accountable.controller.dto.request.TransactionCategoryRequest;
import org.mithwick93.accountable.controller.dto.request.TransactionRequest;
import org.mithwick93.accountable.controller.dto.response.TransactionCategoryResponse;
import org.mithwick93.accountable.controller.dto.response.TransactionResponse;
import org.mithwick93.accountable.model.Transaction;
import org.mithwick93.accountable.model.TransactionCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper extends BaseMapper {

    public abstract TransactionCategory toTransactionCategory(TransactionCategoryRequest request);

    public abstract TransactionCategoryResponse toTransactionCategoryResponse(TransactionCategory transactionCategory);

    public abstract List<TransactionCategoryResponse> toTransactionCategoryResponses(List<TransactionCategory> transactionCategories);

    public abstract Transaction toTransaction(TransactionRequest request);

    public abstract TransactionResponse toTransactionResponse(Transaction transaction);

    public abstract List<TransactionResponse> toTransactionResponses(List<Transaction> transactions);

}
