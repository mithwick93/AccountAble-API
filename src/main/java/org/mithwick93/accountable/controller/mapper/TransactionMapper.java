package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.cache.AssetCache;
import org.mithwick93.accountable.cache.LiabilityCache;
import org.mithwick93.accountable.cache.PaymentSystemCache;
import org.mithwick93.accountable.cache.TransactionCache;
import org.mithwick93.accountable.controller.dto.request.SharedTransactionRequest;
import org.mithwick93.accountable.controller.dto.request.TransactionCategoryRequest;
import org.mithwick93.accountable.controller.dto.request.TransactionRequest;
import org.mithwick93.accountable.controller.dto.request.TransactionSearchRequest;
import org.mithwick93.accountable.controller.dto.response.AssetResponse;
import org.mithwick93.accountable.controller.dto.response.LiabilityResponse;
import org.mithwick93.accountable.controller.dto.response.PaymentSystemCreditResponse;
import org.mithwick93.accountable.controller.dto.response.PaymentSystemDebitResponse;
import org.mithwick93.accountable.controller.dto.response.SharedTransactionResponse;
import org.mithwick93.accountable.controller.dto.response.TransactionCategoryResponse;
import org.mithwick93.accountable.controller.dto.response.TransactionResponse;
import org.mithwick93.accountable.model.Asset;
import org.mithwick93.accountable.model.Liability;
import org.mithwick93.accountable.model.PaymentSystemCredit;
import org.mithwick93.accountable.model.PaymentSystemDebit;
import org.mithwick93.accountable.model.SharedTransaction;
import org.mithwick93.accountable.model.Transaction;
import org.mithwick93.accountable.model.TransactionCategory;
import org.mithwick93.accountable.model.TransactionSearch;
import org.mithwick93.accountable.model.enums.PaymentSystemType;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper extends BaseMapper {

    @Autowired
    private TransactionCache transactionCache;

    @Autowired
    private AssetMapper assetMapper;

    @Autowired
    private AssetCache assetCache;

    @Autowired
    private LiabilityMapper liabilityMapper;

    @Autowired
    private LiabilityCache liabilityCache;

    @Autowired
    private PaymentSystemMapper paymentSystemMapper;

    @Autowired
    private PaymentSystemCache paymentSystemCache;

    @Autowired
    private JwtUtil jwtUtil;

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
    @Mapping(target = "fromAsset", expression = "java(mapAsset(transaction.getFromAssetId()))")
    @Mapping(target = "toAsset", expression = "java(mapAsset(transaction.getToAssetId()))")
    @Mapping(target = "fromLiability", expression = "java(mapLiability(transaction.getFromLiabilityId()))")
    @Mapping(target = "toLiability", expression = "java(mapLiability(transaction.getToLiabilityId()))")
    @Mapping(target = "fromPaymentSystemCredit", expression = "java(mapPaymentSystemCredit(transaction.getFromPaymentSystemId()))")
    @Mapping(target = "toPaymentSystemCredit", expression = "java(mapPaymentSystemCredit(transaction.getToPaymentSystemId()))")
    @Mapping(target = "fromPaymentSystemDebit", expression = "java(mapPaymentSystemDebit(transaction.getFromPaymentSystemId()))")
    @Mapping(target = "toPaymentSystemDebit", expression = "java(mapPaymentSystemDebit(transaction.getToPaymentSystemId()))")
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
        List<Integer> userIdFallback = request.userIds() == null || request.userIds().isEmpty()
                ? List.of(jwtUtil.getAuthenticatedUserId())
                : request.userIds();

        return new TransactionSearch(
                Optional.of(userIdFallback),
                Optional.ofNullable(request.dateFrom()),
                Optional.ofNullable(request.dateTo()),
                Optional.ofNullable(request.types()),
                Optional.ofNullable(request.categoryIds()),
                Optional.ofNullable(request.fromAssetIds()),
                Optional.ofNullable(request.toAssetIds()),
                Optional.ofNullable(request.fromPaymentSystemIds()),
                Optional.ofNullable(request.toPaymentSystemIds()),
                Optional.ofNullable(request.fromLiabilityIds()),
                Optional.ofNullable(request.toLiabilityIds()),
                Optional.ofNullable(request.hasPendingSettlements()),
                Optional.ofNullable(request.hasSharedTransactions())
        );
    }

    protected TransactionCategoryResponse mapTransactionCategory(Integer categoryId) {
        TransactionCategory category = transactionCache.getTransactionCategory(categoryId);
        return toTransactionCategoryResponse(category);
    }

    protected AssetResponse mapAsset(Integer assetId) {
        if (assetId == null) {
            return null;
        }
        Asset asset = assetCache.getAsset(assetId);
        return assetMapper.toAssetResponse(asset);
    }

    protected LiabilityResponse mapLiability(Integer liabilityId) {
        if (liabilityId == null) {
            return null;
        }
        Liability liability = liabilityCache.getLiability(liabilityId);
        return liabilityMapper.toLiabilityResponse(liability);
    }

    protected PaymentSystemCreditResponse mapPaymentSystemCredit(Integer paymentSystemId) {
        if (paymentSystemId == null) {
            return null;
        }
        if (paymentSystemCache.getPaymentSystemTypeById(paymentSystemId) != PaymentSystemType.CREDIT) {
            return null;
        }

        PaymentSystemCredit paymentSystemCredit = paymentSystemCache.getPaymentSystemCredit(paymentSystemId);
        return paymentSystemMapper.toPaymentSystemCreditResponse(paymentSystemCredit);
    }

    protected PaymentSystemDebitResponse mapPaymentSystemDebit(Integer paymentSystemId) {
        if (paymentSystemId == null) {
            return null;
        }
        if (paymentSystemCache.getPaymentSystemTypeById(paymentSystemId) != PaymentSystemType.DEBIT) {
            return null;
        }

        PaymentSystemDebit paymentSystemDebit = paymentSystemCache.getPaymentSystemDebit(paymentSystemId);
        return paymentSystemMapper.toPaymentSystemDebitResponse(paymentSystemDebit);
    }

}
