package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.TransactionNameToCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionNameToCategoryRepository extends JpaRepository<TransactionNameToCategory, Integer> {

    @Query(
            value = """
                    SELECT t.category_id FROM (
                        SELECT category_id, COUNT(*) as cnt
                        FROM transaction_name_to_categories
                        WHERE MATCH(transaction_name) AGAINST (:searchTerm IN NATURAL LANGUAGE MODE)
                        GROUP BY category_id
                        ORDER BY cnt DESC
                        LIMIT 1
                    ) t
                    """,
            nativeQuery = true
    )
    Optional<Integer> findMostProbableCategoryIdByTransactionName(@Param("searchTerm") String searchTerm);

}
