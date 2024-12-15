package org.mithwick93.accountable.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.request.InstallmentPlanRequest;
import org.mithwick93.accountable.controller.dto.response.InstallmentPlanResponse;
import org.mithwick93.accountable.controller.mapper.InstallmentPlanMapper;
import org.mithwick93.accountable.model.InstallmentPlan;
import org.mithwick93.accountable.model.InstallmentPlanStatus;
import org.mithwick93.accountable.service.InstallmentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/installment-plans")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstallmentPlanController {

    private final InstallmentPlanService installmentPlanService;

    private final InstallmentPlanMapper installmentPlanMapper;

    @GetMapping
    public ResponseEntity<List<InstallmentPlanResponse>> getAll() {
        List<InstallmentPlan> installmentPlans = installmentPlanService.getAll();
        List<InstallmentPlanResponse> installmentPlanResponses = installmentPlanMapper.toInstallmentPlanResponses(installmentPlans);
        return ResponseEntity.ok(installmentPlanResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstallmentPlanResponse> getById(@PathVariable int id) {
        InstallmentPlan installmentPlan = installmentPlanService.getById(id);
        InstallmentPlanResponse installmentPlanResponse = installmentPlanMapper.toInstallmentPlanResponse(installmentPlan);
        return ResponseEntity.ok(installmentPlanResponse);
    }

    @GetMapping("/liability/{liabilityId}")
    public ResponseEntity<List<InstallmentPlanResponse>> getByLiabilityId(@PathVariable int liabilityId) {
        List<InstallmentPlan> installmentPlans = installmentPlanService.getByLiabilityId(liabilityId);
        List<InstallmentPlanResponse> installmentPlanResponses = installmentPlanMapper.toInstallmentPlanResponses(installmentPlans);
        return ResponseEntity.ok(installmentPlanResponses);
    }

    @PostMapping
    public ResponseEntity<InstallmentPlanResponse> create(@Valid @RequestBody InstallmentPlanRequest request) {
        InstallmentPlan newInstallmentPlan = installmentPlanMapper.toInstallmentPlan(request);
        InstallmentPlan createdInstallmentPlan = installmentPlanService.create(newInstallmentPlan);
        InstallmentPlanResponse installmentPlanResponse = installmentPlanMapper.toInstallmentPlanResponse(createdInstallmentPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(installmentPlanResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstallmentPlanResponse> update(
            @PathVariable int id,
            @Valid @RequestBody InstallmentPlanRequest request) {
        InstallmentPlan installmentPlan = installmentPlanMapper.toInstallmentPlan(request);
        InstallmentPlan updatedInstallmentPlan = installmentPlanService.update(id, installmentPlan);
        InstallmentPlanResponse installmentPlanResponse = installmentPlanMapper.toInstallmentPlanResponse(updatedInstallmentPlan);
        return ResponseEntity.ok(installmentPlanResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        installmentPlanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getStatuses() {
        return ResponseEntity.ok(InstallmentPlanStatus.INSTALLMENT_PLAN_STATUSES);
    }

}
