package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsernameOrEmail(String username, String email);

    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}