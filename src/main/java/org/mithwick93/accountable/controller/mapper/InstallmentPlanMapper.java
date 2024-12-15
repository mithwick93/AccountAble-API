package org.mithwick93.accountable.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mithwick93.accountable.cache.LiabilityCache;
import org.mithwick93.accountable.controller.dto.request.InstallmentPlanRequest;
import org.mithwick93.accountable.controller.dto.response.InstallmentPlanResponse;
import org.mithwick93.accountable.controller.dto.response.LiabilityResponse;
import org.mithwick93.accountable.model.InstallmentPlan;
import org.mithwick93.accountable.model.Liability;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class InstallmentPlanMapper extends BaseMapper {

    @Autowired
    private LiabilityMapper liabilityMapper;

    @Autowired
    private LiabilityCache liabilityCache;

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract InstallmentPlan toInstallmentPlan(InstallmentPlanRequest installmentPlanRequest);

    @Mapping(target = "user", expression = "java(mapUser(installmentPlan.getUserId()))")
    @Mapping(target = "liability", expression = "java(mapLiability(installmentPlan.getLiabilityId()))")
    public abstract InstallmentPlanResponse toInstallmentPlanResponse(InstallmentPlan installmentPlan);

    public abstract List<InstallmentPlanResponse> toInstallmentPlanResponses(List<InstallmentPlan> installmentPlans);

    protected LiabilityResponse mapLiability(int liabilityId) {
        Liability liability = liabilityCache.getLiability(liabilityId);
        return liabilityMapper.toLiabilityResponse(liability);
    }

}
