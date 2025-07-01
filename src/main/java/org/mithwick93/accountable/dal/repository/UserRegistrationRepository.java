package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Integer> {

    Optional<UserRegistration> findByToken(String token);

    boolean existsByUsernameOrEmail(String username, String email);

}
