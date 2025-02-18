package org.mithwick93.accountable.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.mithwick93.accountable.controller.dto.request.TransactionRequest;
import org.mithwick93.accountable.model.enums.TransactionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;
import java.util.function.Predicate;

@Constraint(validatedBy = ValidTransaction.ValidTransactionValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransaction {

    String message() default "Invalid transaction type for the given conditions";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ValidTransactionValidator implements ConstraintValidator<ValidTransaction, TransactionRequest> {

        private final Predicate<TransactionRequest> fromAssetPresent =
                transactionRequest -> transactionRequest.fromAssetId() != null;

        private final Predicate<TransactionRequest> toAssetPresent =
                transactionRequest -> transactionRequest.toAssetId() != null;

        private final Predicate<TransactionRequest> fromPaymentSystemPresent =
                transactionRequest -> transactionRequest.fromPaymentSystemId() != null;

        private final Predicate<TransactionRequest> toPaymentSystemPresent =
                transactionRequest -> transactionRequest.toPaymentSystemId() != null;

        private final Predicate<TransactionRequest> fromLiabilityPresent =
                transactionRequest -> transactionRequest.fromLiabilityId() != null;

        private final Predicate<TransactionRequest> toLiabilityPresent =
                transactionRequest -> transactionRequest.toLiabilityId() != null;

        private final Predicate<TransactionRequest> sharedTransactionsPresent =
                transactionRequest -> transactionRequest.sharedTransactions() != null && !transactionRequest.sharedTransactions().isEmpty();

        @Override
        public boolean isValid(TransactionRequest value, ConstraintValidatorContext context) {
            return Optional.ofNullable(value)
                    .map(transactionRequest -> TransactionType.valueOf(transactionRequest.type()))
                    .map(transactionType -> switch (transactionType) {
                        case INCOME -> validateIncome(value);
                        case EXPENSE -> validateExpense(value);
                        case TRANSFER -> validateTransfer(value);
                    })
                    .orElse(false);
        }

        private boolean validateIncome(TransactionRequest transactionRequest) {
            return !fromAssetPresent.test(transactionRequest) &&
                    toAssetPresent.test(transactionRequest) &&
                    !fromPaymentSystemPresent.test(transactionRequest) &&
                    !toPaymentSystemPresent.test(transactionRequest) &&
                    !fromLiabilityPresent.test(transactionRequest) &&
                    !toLiabilityPresent.test(transactionRequest) &&
                    !sharedTransactionsPresent.test(transactionRequest);
        }

        private boolean validateExpense(TransactionRequest transactionRequest) {
            return !fromAssetPresent.test(transactionRequest) &&
                    !toAssetPresent.test(transactionRequest) &&
                    fromPaymentSystemPresent.test(transactionRequest) &&
                    !toPaymentSystemPresent.test(transactionRequest) &&
                    !fromLiabilityPresent.test(transactionRequest) &&
                    !toLiabilityPresent.test(transactionRequest);
        }

        private boolean validateTransfer(TransactionRequest transactionRequest) {
            boolean isFromAssetPresent = fromAssetPresent.test(transactionRequest);
            boolean isToAssetPresent = toAssetPresent.test(transactionRequest);
            boolean isFromPaymentSystemPresent = fromPaymentSystemPresent.test(transactionRequest);
            boolean isToPaymentSystemPresent = toPaymentSystemPresent.test(transactionRequest);
            boolean isFromLiabilityPresent = fromLiabilityPresent.test(transactionRequest);
            boolean isToLiabilityPresent = toLiabilityPresent.test(transactionRequest);
            boolean isSharedTransactionsPresent = sharedTransactionsPresent.test(transactionRequest);

            return (isFromAssetPresent && isToAssetPresent && !isFromPaymentSystemPresent && !isToPaymentSystemPresent && !isSharedTransactionsPresent && !isFromLiabilityPresent && !isToLiabilityPresent) ||
                    (isFromAssetPresent && isToPaymentSystemPresent && !isToAssetPresent && !isFromPaymentSystemPresent && !isSharedTransactionsPresent && !isFromLiabilityPresent && !isToLiabilityPresent) ||
                    (isFromAssetPresent && isToLiabilityPresent && !isToAssetPresent && !isFromPaymentSystemPresent && !isSharedTransactionsPresent && !isFromLiabilityPresent && !isToPaymentSystemPresent);
        }

    }

}
