package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.PasswordResetToken;
import org.mithwick93.accountable.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByUser(User user);

}
