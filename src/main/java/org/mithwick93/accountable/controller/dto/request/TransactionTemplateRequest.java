package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.mithwick93.accountable.model.enums.Currency;
import org.mithwick93.accountable.model.enums.Frequency;
import org.mithwick93.accountable.model.enums.TransactionType;
import org.mithwick93.accountable.validation.ValidEnum;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public record TransactionTemplateRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 500)
        String description,

        @NotBlank(message = "Transaction type is required")
        @ValidEnum(enumClass = TransactionType.class, message = "must be a valid transaction type")
        String type,

        Integer categoryId,

        @NotNull(message = "Transaction amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Transaction amount must be greater than zero")
        BigDecimal amount,

        @NotBlank
        @ValidEnum(enumClass = Currency.class, message = "must be a valid currency")
        String currency,

        Integer fromAssetId,

        Integer toAssetId,

        Integer fromPaymentSystemId,

        Integer toPaymentSystemId,

        @ValidEnum(enumClass = Frequency.class, message = "must be a valid frequency")
        String frequency,

        @ValidEnum(enumClass = DayOfWeek.class, message = "must be a valid day of week")
        String dayOfWeek,

        Byte dayOfMonth,

        Byte monthOfYear,

        LocalDate startDate,

        LocalDate endDate

) {

}
