package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.PaymentSystemDebit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentSystemDebitRepository extends JpaRepository<PaymentSystemDebit, Integer> {

    List<PaymentSystemDebit> findAllByUserId(int userId);

    Optional<PaymentSystemDebit> findByIdAndUserId(int id, int userId);

}
