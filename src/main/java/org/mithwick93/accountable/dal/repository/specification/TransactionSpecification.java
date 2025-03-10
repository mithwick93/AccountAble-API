package org.mithwick93.accountable.dal.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.model.SharedTransaction;
import org.mithwick93.accountable.model.Transaction;
import org.mithwick93.accountable.model.TransactionSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionSpecification {

    public Specification<Transaction> withFilters(TransactionSearch search) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.conjunction());

            // Filter by users
            search.userIds()
                    .filter(userIds -> !userIds.isEmpty())
                    .ifPresent(userIds -> predicates.add(root.get("userId").in(userIds)));

            // Filter by name
            search.name().ifPresent(name ->
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"))
            );

            //Filter by description
            search.description().ifPresent(description ->
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%"))
            );

            // Date filters
            search.dateFrom().ifPresent(dateFrom ->
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), dateFrom))
            );
            search.dateTo().ifPresent(dateTo ->
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), dateTo))
            );

            // Filter by currencies
            search.currencies()
                    .filter(currencies -> !currencies.isEmpty())
                    .ifPresent(currencies -> predicates.add(root.get("currency").in(currencies)));

            // Filter by amount from
            search.amountFrom().ifPresent(amountFrom ->
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), amountFrom))
            );
            search.amountTo().ifPresent(amountTo ->
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), amountTo))
            );

            // Filter by transaction types
            search.types()
                    .filter(types -> !types.isEmpty())
                    .ifPresent(types -> predicates.add(root.get("type").in(types)));

            // Filter by category IDs
            search.categoryIds()
                    .filter(categoryIds -> !categoryIds.isEmpty())
                    .ifPresent(categoryIds -> predicates.add(root.get("categoryId").in(categoryIds)));

            // Filter by asset IDs
            search.fromAssetIds()
                    .filter(fromAssetIds -> !fromAssetIds.isEmpty())
                    .ifPresent(fromAssetIds -> predicates.add(root.get("fromAssetId").in(fromAssetIds)));
            search.toAssetIds()
                    .filter(toAssetIds -> !toAssetIds.isEmpty())
                    .ifPresent(toAssetIds -> predicates.add(root.get("toAssetId").in(toAssetIds)));

            // Filter by payment system IDs
            search.fromPaymentSystemIds()
                    .filter(fromPaymentSystemIds -> !fromPaymentSystemIds.isEmpty())
                    .ifPresent(fromPaymentSystemIds -> predicates.add(root.get("fromPaymentSystemId").in(fromPaymentSystemIds)));
            search.toPaymentSystemIds()
                    .filter(toPaymentSystemIds -> !toPaymentSystemIds.isEmpty())
                    .ifPresent(toPaymentSystemIds -> predicates.add(root.get("toPaymentSystemId").in(toPaymentSystemIds)));

            // Filter by liability IDs
            search.fromLiabilityIds()
                    .filter(fromLiabilityIds -> !fromLiabilityIds.isEmpty())
                    .ifPresent(fromLiabilityIds -> predicates.add(root.get("fromLiabilityId").in(fromLiabilityIds)));
            search.toLiabilityIds()
                    .filter(toLiabilityIds -> !toLiabilityIds.isEmpty())
                    .ifPresent(toLiabilityIds -> predicates.add(root.get("toLiabilityId").in(toLiabilityIds)));

            // Handle unsettled shared transactions
            search.hasPendingSettlements().ifPresent(hasPendingSettlements -> {
                Join<Transaction, SharedTransaction> sharedTransactionJoin = root.join("sharedTransactions", JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(sharedTransactionJoin.get("isSettled"), !hasPendingSettlements));
            });

            // Handle presence of shared transactions
            search.hasSharedTransactions().ifPresent(hasSharedTransactions -> {
                if (hasSharedTransactions) {
                    predicates.add(criteriaBuilder.isNotEmpty(root.get("sharedTransactions")));
                } else {
                    predicates.add(criteriaBuilder.isEmpty(root.get("sharedTransactions")));
                }
            });

            // Combine all predicates into one final predicate
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
