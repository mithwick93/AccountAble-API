package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.InstallmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstallmentPlanRepository extends JpaRepository<InstallmentPlan, Integer> {

    List<InstallmentPlan> findAllByUserId(int userId);

    List<InstallmentPlan> findByLiabilityIdAndUserId(int liabilityId, int userId);

    Optional<InstallmentPlan> findByIdAndUserId(int id, int userId);

}
