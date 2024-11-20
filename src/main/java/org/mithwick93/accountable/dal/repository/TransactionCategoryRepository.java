package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Integer> {

    List<TransactionCategory> findAllByUserId(int userId);

    Optional<TransactionCategory> findByIdAndUserId(int id, int userId);

}
