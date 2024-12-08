package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    @Modifying
    @Query(
            value = """
                    UPDATE shared_transactions st
                    SET st.is_settled = 1, st.paid_amount = st.share
                    WHERE st.user_id = :participantId AND st.transaction_id IN (:transactionIds)
                    """,
            nativeQuery = true
    )
    void markTransactionsAsPaidByCreatorAndParticipant(int participantId, List<Long> transactionIds);

}
