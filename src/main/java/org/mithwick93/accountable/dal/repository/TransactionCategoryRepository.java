package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Integer> {

}
