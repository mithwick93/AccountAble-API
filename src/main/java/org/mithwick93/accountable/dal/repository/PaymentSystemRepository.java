package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.PaymentSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentSystemRepository extends JpaRepository<PaymentSystem, Integer> {

    List<PaymentSystem> findAllByUserId(int userId);

    Optional<PaymentSystem> findByIdAndUserId(int id, int userId);

    @Query(value = "SELECT type_id FROM payment_systems WHERE id = :id AND user_id = :userId", nativeQuery = true)
    Optional<Integer> findTypeIdById(@Param("id") int id, @Param("userId") int userId);

}
