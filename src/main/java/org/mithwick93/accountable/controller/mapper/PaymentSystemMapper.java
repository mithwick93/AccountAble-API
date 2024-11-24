package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.controller.dto.request.PaymentSystemCreditRequest;
import org.mithwick93.accountable.controller.dto.request.PaymentSystemDebitRequest;
import org.mithwick93.accountable.controller.dto.response.PaymentSystemCreditResponse;
import org.mithwick93.accountable.controller.dto.response.PaymentSystemDebitResponse;
import org.mithwick93.accountable.model.PaymentSystemCredit;
import org.mithwick93.accountable.model.PaymentSystemDebit;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PaymentSystemMapper extends BaseMapper {

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract PaymentSystemCredit toPaymentSystemCredit(PaymentSystemCreditRequest request);

    public abstract PaymentSystemCreditResponse toPaymentSystemCreditResponse(PaymentSystemCredit model);

    public abstract List<PaymentSystemCreditResponse> toPaymentSystemCreditResponses(List<PaymentSystemCredit> models);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract PaymentSystemDebit toPaymentSystemDebit(PaymentSystemDebitRequest request);

    public abstract PaymentSystemDebitResponse toPaymentSystemDebitResponse(PaymentSystemDebit model);

    public abstract List<PaymentSystemDebitResponse> toPaymentSystemDebitResponses(List<PaymentSystemDebit> models);

}
