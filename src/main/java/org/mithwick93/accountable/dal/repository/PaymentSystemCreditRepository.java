package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.PaymentSystemCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentSystemCreditRepository extends JpaRepository<PaymentSystemCredit, Integer> {

    List<PaymentSystemCredit> findAllByUserId(int userId);

    List<PaymentSystemCredit> findByLiabilityIdAndUserId(int liabilityId, int userId);

    Optional<PaymentSystemCredit> findByIdAndUserId(int id, int userId);

}