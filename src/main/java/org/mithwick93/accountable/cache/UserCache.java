package org.mithwick93.accountable.cache;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.UserRepository;
import org.mithwick93.accountable.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserCache {

    private final UserRepository userRepository;

    @Cacheable(value = "user_cache", unless = "#result == null")
    public User getUser(int id) {
        return userRepository.findById(id)
                .orElse(null);
    }

}
