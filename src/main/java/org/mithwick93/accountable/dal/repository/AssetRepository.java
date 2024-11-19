package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {
    List<Asset> findAllByUserId(int userId);

    Optional<Asset> findByIdAndUserId(int id, int userId);
}
