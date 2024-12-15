package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.controller.dto.request.LiabilityRequest;
import org.mithwick93.accountable.controller.dto.response.LiabilityResponse;
import org.mithwick93.accountable.controller.dto.response.LiabilityTypeResponse;
import org.mithwick93.accountable.model.Liability;
import org.mithwick93.accountable.model.LiabilityType;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LiabilityMapper extends BaseMapper {

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract Liability toLiability(LiabilityRequest liabilityRequest);

    @Mapping(target = "user", expression = "java(mapUser(liability.getUserId()))")
    public abstract LiabilityResponse toLiabilityResponse(Liability liability);

    public abstract List<LiabilityResponse> toLiabilityResponses(List<Liability> liabilities);

    @Mapping(target = "name", expression = "java(liabilityType.toString())")
    public abstract LiabilityTypeResponse toLiabilityTypeResponse(LiabilityType liabilityType);

    public abstract List<LiabilityTypeResponse> toLiabilityTypeResponses(List<LiabilityType> liabilityTypes);

}
