package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.cache.AssetCache;
import org.mithwick93.accountable.controller.dto.request.PaymentSystemCreditRequest;
import org.mithwick93.accountable.controller.dto.request.PaymentSystemDebitRequest;
import org.mithwick93.accountable.controller.dto.response.AssetResponse;
import org.mithwick93.accountable.controller.dto.response.PaymentSystemCreditResponse;
import org.mithwick93.accountable.controller.dto.response.PaymentSystemDebitResponse;
import org.mithwick93.accountable.model.Asset;
import org.mithwick93.accountable.model.PaymentSystemCredit;
import org.mithwick93.accountable.model.PaymentSystemDebit;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PaymentSystemMapper extends BaseMapper {

    @Autowired
    private AssetMapper assetMapper;

    @Autowired
    private AssetCache assetCache;

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract PaymentSystemCredit toPaymentSystemCredit(PaymentSystemCreditRequest request);

    @Mapping(target = "user", expression = "java(mapUser(model.getUserId()))")
    public abstract PaymentSystemCreditResponse toPaymentSystemCreditResponse(PaymentSystemCredit model);

    public abstract List<PaymentSystemCreditResponse> toPaymentSystemCreditResponses(List<PaymentSystemCredit> models);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract PaymentSystemDebit toPaymentSystemDebit(PaymentSystemDebitRequest request);

    @Mapping(target = "user", expression = "java(mapUser(model.getUserId()))")
    @Mapping(target = "asset", expression = "java(mapAsset(model.getAssetId()))")
    public abstract PaymentSystemDebitResponse toPaymentSystemDebitResponse(PaymentSystemDebit model);

    public abstract List<PaymentSystemDebitResponse> toPaymentSystemDebitResponses(List<PaymentSystemDebit> models);

    protected AssetResponse mapAsset(int assetId) {
        Asset asset = assetCache.getAsset(assetId);
        return assetMapper.toAssetResponse(asset);
    }

}
