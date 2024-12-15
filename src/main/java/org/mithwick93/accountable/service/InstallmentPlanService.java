package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.InstallmentPlanRepository;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.InstallmentPlan;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstallmentPlanService {

    private final InstallmentPlanRepository installmentPlanRepository;

    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public List<InstallmentPlan> getAll() {
        return installmentPlanRepository.findAllByUserId(jwtUtil.getAuthenticatedUserId());
    }

    @Transactional(readOnly = true)
    public InstallmentPlan getById(int id) {
        return installmentPlanRepository.findByIdAndUserId(id, jwtUtil.getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("InstallmentPlan with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<InstallmentPlan> getByLiabilityId(int liabilityId) {
        return installmentPlanRepository.findByLiabilityIdAndUserId(liabilityId, jwtUtil.getAuthenticatedUserId());
    }

    public InstallmentPlan create(InstallmentPlan installmentPlan) {
        installmentPlan.setUserId(jwtUtil.getAuthenticatedUserId());
        return installmentPlanRepository.save(installmentPlan);
    }

    public InstallmentPlan update(int id, InstallmentPlan installmentPlan) {
        InstallmentPlan existingInstallmentPlan = getById(id);

        existingInstallmentPlan.setName(installmentPlan.getName());
        existingInstallmentPlan.setDescription(installmentPlan.getDescription());
        existingInstallmentPlan.setLiabilityId(installmentPlan.getLiabilityId());
        existingInstallmentPlan.setCurrency(installmentPlan.getCurrency());
        existingInstallmentPlan.setInstallmentAmount(installmentPlan.getInstallmentAmount());
        existingInstallmentPlan.setTotalInstallments(installmentPlan.getTotalInstallments());
        existingInstallmentPlan.setInstallmentsPaid(installmentPlan.getInstallmentsPaid());
        existingInstallmentPlan.setInterestRate(installmentPlan.getInterestRate());
        existingInstallmentPlan.setStartDate(installmentPlan.getStartDate());
        existingInstallmentPlan.setEndDate(installmentPlan.getEndDate());
        existingInstallmentPlan.setStatus(installmentPlan.getStatus());

        return installmentPlanRepository.save(existingInstallmentPlan);
    }

    public void delete(int id) {
        InstallmentPlan existingInstallmentPlan = getById(id);
        installmentPlanRepository.delete(existingInstallmentPlan);
    }

}
