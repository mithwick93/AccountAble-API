package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
