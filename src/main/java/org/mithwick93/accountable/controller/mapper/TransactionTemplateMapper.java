package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.controller.dto.request.TransactionTemplateRequest;
import org.mithwick93.accountable.controller.dto.response.TransactionTemplateResponse;
import org.mithwick93.accountable.model.TransactionTemplate;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransactionTemplateMapper extends TransactionBaseMapper {

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract TransactionTemplate toTransactionTemplate(TransactionTemplateRequest request);

    @Mapping(target = "user", expression = "java(mapUser(transactionTemplate.getUserId()))")
    @Mapping(target = "category", expression = "java(mapTransactionCategory(transactionTemplate.getCategoryId()))")
    @Mapping(target = "fromAsset", expression = "java(mapAsset(transactionTemplate.getFromAssetId()))")
    @Mapping(target = "toAsset", expression = "java(mapAsset(transactionTemplate.getToAssetId()))")
    @Mapping(target = "fromPaymentSystemCredit", expression = "java(mapPaymentSystemCredit(transactionTemplate.getFromPaymentSystemId()))")
    @Mapping(target = "toPaymentSystemCredit", expression = "java(mapPaymentSystemCredit(transactionTemplate.getToPaymentSystemId()))")
    @Mapping(target = "fromPaymentSystemDebit", expression = "java(mapPaymentSystemDebit(transactionTemplate.getFromPaymentSystemId()))")
    @Mapping(target = "toPaymentSystemDebit", expression = "java(mapPaymentSystemDebit(transactionTemplate.getToPaymentSystemId()))")
    public abstract TransactionTemplateResponse toTransactionTemplateResponse(TransactionTemplate transactionTemplate);

    public abstract List<TransactionTemplateResponse> toTransactionTemplateResponses(List<TransactionTemplate> transactionTemplates);

}
