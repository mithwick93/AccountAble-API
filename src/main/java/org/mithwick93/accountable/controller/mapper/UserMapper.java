package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mithwick93.accountable.controller.dto.response.UserResponse;
import org.mithwick93.accountable.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract UserResponse toUserResponse(User user);

    public abstract List<UserResponse> toUserResponses(List<User> users);
}
