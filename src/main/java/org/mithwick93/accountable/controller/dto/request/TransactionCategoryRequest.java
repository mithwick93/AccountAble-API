package org.mithwick93.accountable.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.mithwick93.accountable.model.TransactionType;
import org.mithwick93.accountable.validation.ValidEnum;

public record TransactionCategoryRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @ValidEnum(enumClass = TransactionType.class, message = "must be a valid transaction type")
        String type
) {

}
