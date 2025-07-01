package org.mithwick93.accountable.dal.repository;

import org.mithwick93.accountable.model.UserWhitelist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWhiteListRepository extends JpaRepository<UserWhitelist, String> {

}