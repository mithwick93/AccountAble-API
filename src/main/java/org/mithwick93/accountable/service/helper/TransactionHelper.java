package org.mithwick93.accountable.service.helper;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.model.Currency;
import org.mithwick93.accountable.model.PaymentSystemCredit;
import org.mithwick93.accountable.model.PaymentSystemType;
import org.mithwick93.accountable.model.Transaction;
import org.mithwick93.accountable.service.AssetService;
import org.mithwick93.accountable.service.LiabilityService;
import org.mithwick93.accountable.service.PaymentSystemCreditService;
import org.mithwick93.accountable.service.PaymentSystemDebitService;
import org.mithwick93.accountable.service.PaymentSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionHelper {

    private final AssetService assetService;

    private final LiabilityService liabilityService;

    private final PaymentSystemService paymentSystemService;

    private final PaymentSystemCreditService paymentSystemCreditService;

    private final PaymentSystemDebitService paymentSystemDebitService;

    public void updateAccounts(final Transaction existingTransaction, final Transaction newTransaction) {
        reverseTransaction(existingTransaction);
        updateAccounts(newTransaction);
    }

    private void reverseTransaction(final Transaction transaction) {
        switch (transaction.getType()) {
            case INCOME -> reverseIncome(transaction);
            case EXPENSE -> reverseExpense(transaction);
            case TRANSFER -> reverseTransfer(transaction);
            default ->
                    throw new IllegalArgumentException("Unsupported transaction type: " + transaction.getType());
        }
    }

    public void updateAccounts(final Transaction transaction) {
        switch (transaction.getType()) {
            case INCOME -> handleIncome(transaction);
            case EXPENSE -> handleExpense(transaction);
            case TRANSFER -> handleTransfer(transaction);
            default ->
                    throw new IllegalArgumentException("Unsupported transaction type: " + transaction.getType());
        }
    }

    private void reverseIncome(final Transaction transaction) {
        Integer toAssetId = Objects.requireNonNull(transaction.getToAssetId(), "To Asset ID is required for INCOME transactions");
        processIncome(toAssetId, transaction.getAmount().negate(), transaction.getCurrency());
    }

    private void reverseExpense(final Transaction transaction) {
        Integer fromPaymentSystemId = Objects.requireNonNull(transaction.getFromPaymentSystemId(), "From Payment System ID is required for EXPENSE transactions");
        processExpense(fromPaymentSystemId, transaction.getAmount().negate(), transaction.getCurrency());
    }

    private void reverseTransfer(final Transaction transaction) {
        if (transaction.isPaymentSystemTransfer()) {
            reverseAccountToPaymentSystem(transaction);
        } else if (transaction.isAccountToAccountTransfer()) {
            reverseAccountToAccount(transaction);
        } else {
            throw new IllegalArgumentException("Invalid transfer type for transaction: " + transaction);
        }
    }

    private void reverseAccountToPaymentSystem(final Transaction transaction) {
        Integer fromAssetId = Objects.requireNonNull(transaction.getFromAssetId(), "From Asset ID is required for Payment System Transfer");
        Integer toPaymentSystemId = Objects.requireNonNull(transaction.getToPaymentSystemId(), "To Payment System ID is required for Payment System Transfer");
        processAccountToPaymentSystem(fromAssetId, toPaymentSystemId, transaction.getAmount().negate(), transaction.getCurrency());
    }

    private void reverseAccountToAccount(final Transaction transaction) {
        Integer fromAssetId = Objects.requireNonNull(transaction.getFromAssetId(), "From Asset ID is required for Account-to-Account Transfer");
        Integer toAssetId = Objects.requireNonNull(transaction.getToAssetId(), "To Asset ID is required for Account-to-Account Transfer");
        processAccountToAccount(fromAssetId, toAssetId, transaction.getAmount().negate(), transaction.getCurrency());
    }

    private void handleIncome(final Transaction transaction) {
        Integer toAssetId = Objects.requireNonNull(transaction.getToAssetId(), "To Asset ID is required for INCOME transactions");
        processIncome(toAssetId, transaction.getAmount(), transaction.getCurrency());
    }

    private void handleExpense(final Transaction transaction) {
        Integer fromPaymentSystemId = Objects.requireNonNull(transaction.getFromPaymentSystemId(), "From Payment System ID is required for EXPENSE transactions");
        processExpense(fromPaymentSystemId, transaction.getAmount(), transaction.getCurrency());
    }

    private void handleTransfer(final Transaction transaction) {
        if (transaction.isPaymentSystemTransfer()) {
            processAccountToPaymentSystem(
                    Objects.requireNonNull(transaction.getFromAssetId(), "From Asset ID is required for Payment System Transfer"),
                    Objects.requireNonNull(transaction.getToPaymentSystemId(), "To Payment System ID is required for Payment System Transfer"),
                    transaction.getAmount(),
                    transaction.getCurrency()
            );
        } else if (transaction.isAccountToAccountTransfer()) {
            processAccountToAccount(
                    Objects.requireNonNull(transaction.getFromAssetId(), "From Asset ID is required for Account-to-Account Transfer"),
                    Objects.requireNonNull(transaction.getToAssetId(), "To Asset ID is required for Account-to-Account Transfer"),
                    transaction.getAmount(),
                    transaction.getCurrency()
            );
        } else {
            throw new IllegalArgumentException("Invalid transfer type for transaction: " + transaction);
        }
    }

    private void processIncome(final int assetId, final BigDecimal amount, final Currency currency) {
        assetService.updateBalance(assetId, amount, currency);
    }

    private void processExpense(final int paymentSystemId, final BigDecimal amount, final Currency currency) {
        PaymentSystemType paymentSystemType = paymentSystemService.getPaymentSystemTypeById(paymentSystemId);

        switch (paymentSystemType) {
            case DEBIT -> handleDebitPayment(paymentSystemId, amount, currency);
            case CREDIT ->
                    handleCreditPayment(paymentSystemId, amount, currency);
            default ->
                    throw new IllegalStateException("Unexpected payment system type: " + paymentSystemType);
        }
    }

    private void handleDebitPayment(final int paymentSystemId, final BigDecimal amount, final Currency currency) {
        int assetId = paymentSystemDebitService.getById(paymentSystemId).getAssetId();
        assetService.updateBalance(assetId, amount.negate(), currency);
    }

    private void handleCreditPayment(final int paymentSystemId, final BigDecimal amount, final Currency currency) {
        PaymentSystemCredit paymentSystemCredit = paymentSystemCreditService.getById(paymentSystemId);
        int liabilityId = paymentSystemCredit.getLiabilityId();

        liabilityService.updateBalance(liabilityId, amount, currency);
    }

    //TODO: this should be from asset to liability
    private void processAccountToPaymentSystem(final int assetId, final int paymentSystemId, final BigDecimal amount, final Currency currency) {
        assetService.updateBalance(assetId, amount.negate(), currency);

        PaymentSystemCredit paymentSystemCredit = paymentSystemCreditService.getById(paymentSystemId);
        int liabilityId = paymentSystemCredit.getLiabilityId();

        liabilityService.updateBalance(liabilityId, amount, currency);
    }

    private void processAccountToAccount(final int fromAssetId, final int toAssetId, final BigDecimal amount, final Currency currency) {
        assetService.updateBalance(fromAssetId, amount.negate(), currency);
        assetService.updateBalance(toAssetId, amount, currency);
    }

}
