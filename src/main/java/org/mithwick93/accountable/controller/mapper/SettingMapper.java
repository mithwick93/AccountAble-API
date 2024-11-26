package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.controller.dto.request.SettingRequest;
import org.mithwick93.accountable.controller.dto.response.SettingResponse;
import org.mithwick93.accountable.model.Setting;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SettingMapper {

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract Setting toSetting(SettingRequest request);

    public abstract SettingResponse toSettingResponse(Setting setting);

    public abstract List<SettingResponse> toSettingResponses(List<Setting> setting);

}
