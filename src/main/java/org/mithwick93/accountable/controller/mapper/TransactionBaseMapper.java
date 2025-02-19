package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapping;
import org.mithwick93.accountable.cache.AssetCache;
import org.mithwick93.accountable.cache.LiabilityCache;
import org.mithwick93.accountable.cache.PaymentSystemCache;
import org.mithwick93.accountable.cache.TransactionCategoryCache;
import org.mithwick93.accountable.controller.dto.response.AssetResponse;
import org.mithwick93.accountable.controller.dto.response.LiabilityResponse;
import org.mithwick93.accountable.controller.dto.response.PaymentSystemCreditResponse;
import org.mithwick93.accountable.controller.dto.response.PaymentSystemDebitResponse;
import org.mithwick93.accountable.controller.dto.response.TransactionCategoryResponse;
import org.mithwick93.accountable.model.Asset;
import org.mithwick93.accountable.model.Liability;
import org.mithwick93.accountable.model.PaymentSystemCredit;
import org.mithwick93.accountable.model.PaymentSystemDebit;
import org.mithwick93.accountable.model.TransactionCategory;
import org.mithwick93.accountable.model.enums.PaymentSystemType;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class TransactionBaseMapper extends BaseMapper {

    @Autowired
    private TransactionCategoryCache transactionCategoryCache;

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

    @Mapping(target = "user", expression = "java(mapUser(transactionCategory.getUserId()))")
    public abstract TransactionCategoryResponse toTransactionCategoryResponse(TransactionCategory transactionCategory);

    protected TransactionCategoryResponse mapTransactionCategory(Integer categoryId) {
        TransactionCategory category = transactionCategoryCache.getTransactionCategory(categoryId);
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
