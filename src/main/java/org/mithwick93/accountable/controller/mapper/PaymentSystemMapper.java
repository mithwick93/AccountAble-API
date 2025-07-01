package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.cache.AssetCache;
import org.mithwick93.accountable.cache.LiabilityCache;
import org.mithwick93.accountable.controller.dto.request.PaymentSystemCreditRequest;
import org.mithwick93.accountable.controller.dto.request.PaymentSystemDebitRequest;
import org.mithwick93.accountable.controller.dto.response.AssetResponse;
import org.mithwick93.accountable.controller.dto.response.LiabilityResponse;
import org.mithwick93.accountable.controller.dto.response.PaymentSystemCreditResponse;
import org.mithwick93.accountable.controller.dto.response.PaymentSystemDebitResponse;
import org.mithwick93.accountable.model.Asset;
import org.mithwick93.accountable.model.Liability;
import org.mithwick93.accountable.model.PaymentSystemCredit;
import org.mithwick93.accountable.model.PaymentSystemDebit;
import org.mithwick93.accountable.util.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PaymentSystemMapper extends BaseMapper {

    @Autowired
    protected EncryptionUtils encryptionUtils;

    @Autowired
    private AssetCache assetCache;

    @Autowired
    private AssetMapper assetMapper;

    @Autowired
    private LiabilityCache liabilityCache;

    @Autowired
    private LiabilityMapper liabilityMapper;

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "active", defaultValue = "true")
    @Mapping(target = "cardHolderName", expression = "java(encryptionUtils.encrypt(request.cardHolderName()))")
    @Mapping(target = "cardNumber", expression = "java(encryptionUtils.encrypt(request.cardNumber()))")
    @Mapping(target = "securityCode", expression = "java(encryptionUtils.encrypt(request.securityCode()))")
    @Mapping(target = "expiryDate", expression = "java(encryptionUtils.encrypt(request.expiryDate()))")
    @Mapping(target = "additionalNote", expression = "java(encryptionUtils.encrypt(request.additionalNote()))")
    public abstract PaymentSystemCredit toPaymentSystemCredit(PaymentSystemCreditRequest request);

    @Mapping(target = "user", expression = "java(mapUser(model.getUserId()))")
    @Mapping(target = "liability", expression = "java(mapLiability(model.getLiabilityId()))")
    @Mapping(target = "cardHolderName", expression = "java(encryptionUtils.decrypt(model.getCardHolderName()))")
    @Mapping(target = "cardNumber", expression = "java(encryptionUtils.decrypt(model.getCardNumber()))")
    @Mapping(target = "securityCode", expression = "java(encryptionUtils.decrypt(model.getSecurityCode()))")
    @Mapping(target = "expiryDate", expression = "java(encryptionUtils.decrypt(model.getExpiryDate()))")
    @Mapping(target = "additionalNote", expression = "java(encryptionUtils.decrypt(model.getAdditionalNote()))")
    public abstract PaymentSystemCreditResponse toPaymentSystemCreditResponse(PaymentSystemCredit model);

    public abstract List<PaymentSystemCreditResponse> toPaymentSystemCreditResponses(List<PaymentSystemCredit> models);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "active", defaultValue = "true")
    @Mapping(target = "cardHolderName", expression = "java(encryptionUtils.encrypt(request.cardHolderName()))")
    @Mapping(target = "cardNumber", expression = "java(encryptionUtils.encrypt(request.cardNumber()))")
    @Mapping(target = "securityCode", expression = "java(encryptionUtils.encrypt(request.securityCode()))")
    @Mapping(target = "expiryDate", expression = "java(encryptionUtils.encrypt(request.expiryDate()))")
    @Mapping(target = "additionalNote", expression = "java(encryptionUtils.encrypt(request.additionalNote()))")
    public abstract PaymentSystemDebit toPaymentSystemDebit(PaymentSystemDebitRequest request);

    @Mapping(target = "user", expression = "java(mapUser(model.getUserId()))")
    @Mapping(target = "asset", expression = "java(mapAsset(model.getAssetId()))")
    @Mapping(target = "cardHolderName", expression = "java(encryptionUtils.decrypt(model.getCardHolderName()))")
    @Mapping(target = "cardNumber", expression = "java(encryptionUtils.decrypt(model.getCardNumber()))")
    @Mapping(target = "securityCode", expression = "java(encryptionUtils.decrypt(model.getSecurityCode()))")
    @Mapping(target = "expiryDate", expression = "java(encryptionUtils.decrypt(model.getExpiryDate()))")
    @Mapping(target = "additionalNote", expression = "java(encryptionUtils.decrypt(model.getAdditionalNote()))")
    public abstract PaymentSystemDebitResponse toPaymentSystemDebitResponse(PaymentSystemDebit model);

    public abstract List<PaymentSystemDebitResponse> toPaymentSystemDebitResponses(List<PaymentSystemDebit> models);

    protected AssetResponse mapAsset(int assetId) {
        Asset asset = assetCache.getAsset(assetId);
        return assetMapper.toAssetResponse(asset);
    }

    protected LiabilityResponse mapLiability(int liabilityId) {
        Liability liability = liabilityCache.getLiability(liabilityId);
        return liabilityMapper.toLiabilityResponse(liability);
    }

}
