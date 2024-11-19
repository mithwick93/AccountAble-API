package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUserId(int userId);

    Optional<Transaction> findByIdAndUserId(long id, int userId);
}
