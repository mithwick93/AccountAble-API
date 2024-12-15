package org.mithwick93.accountable.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.controller.dto.request.LiabilityRequest;
import org.mithwick93.accountable.controller.dto.response.LiabilityResponse;
import org.mithwick93.accountable.controller.dto.response.LiabilityTypeResponse;
import org.mithwick93.accountable.controller.mapper.LiabilityMapper;
import org.mithwick93.accountable.model.Liability;
import org.mithwick93.accountable.model.LiabilityStatus;
import org.mithwick93.accountable.model.LiabilityType;
import org.mithwick93.accountable.service.LiabilityService;
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

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/liabilities")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LiabilityController {

    private final LiabilityService liabilityService;

    private final LiabilityMapper liabilityMapper;

    @GetMapping
    public ResponseEntity<List<LiabilityResponse>> getAll() {
        List<Liability> liabilities = liabilityService.getAll();
        List<LiabilityResponse> liabilityResponses = liabilityMapper.toLiabilityResponses(liabilities);
        return ResponseEntity.ok(liabilityResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiabilityResponse> getById(@PathVariable int id) {
        Liability liability = liabilityService.getById(id);
        LiabilityResponse liabilityResponse = liabilityMapper.toLiabilityResponse(liability);
        return ResponseEntity.ok(liabilityResponse);
    }

    @PostMapping
    public ResponseEntity<LiabilityResponse> create(@Valid @RequestBody LiabilityRequest request) {
        Liability newLiability = liabilityMapper.toLiability(request);
        Liability createdLiability = liabilityService.create(newLiability);
        LiabilityResponse liabilityResponse = liabilityMapper.toLiabilityResponse(createdLiability);
        return ResponseEntity.status(HttpStatus.CREATED).body(liabilityResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LiabilityResponse> update(
            @PathVariable int id,
            @Valid @RequestBody LiabilityRequest request
    ) {
        Liability liability = liabilityMapper.toLiability(request);
        Liability updatedLiability = liabilityService.update(id, liability);
        LiabilityResponse liabilityResponse = liabilityMapper.toLiabilityResponse(updatedLiability);
        return ResponseEntity.ok(liabilityResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        liabilityService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/types")
    public ResponseEntity<List<LiabilityTypeResponse>> getTypes() {
        List<LiabilityType> liabilityTypes = Arrays.asList(LiabilityType.values());
        List<LiabilityTypeResponse> liabilityTypeResponses = liabilityMapper.toLiabilityTypeResponses(liabilityTypes);
        return ResponseEntity.ok(liabilityTypeResponses);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getStatuses() {
        return ResponseEntity.ok(LiabilityStatus.LIABILITY_STATUSES);
    }

}
