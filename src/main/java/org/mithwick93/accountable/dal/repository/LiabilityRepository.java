package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.Liability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LiabilityRepository extends JpaRepository<Liability, Integer> {

    List<Liability> findAllByUserId(int userId);

    Optional<Liability> findByIdAndUserId(int id, int userId);

}
