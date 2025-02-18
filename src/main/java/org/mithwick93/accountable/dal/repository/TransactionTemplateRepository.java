package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.TransactionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionTemplateRepository extends JpaRepository<TransactionTemplate, Integer> {

    List<TransactionTemplate> findAllByUserId(int userId);

    Optional<TransactionTemplate> findByIdAndUserId(int id, int userId);

}
