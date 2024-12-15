package org.mithwick93.accountable.service;

import lombok.RequiredArgsConstructor;
import org.mithwick93.accountable.dal.repository.LiabilityRepository;
import org.mithwick93.accountable.exception.BadRequestException;
import org.mithwick93.accountable.exception.NotFoundException;
import org.mithwick93.accountable.model.Currency;
import org.mithwick93.accountable.model.Liability;
import org.mithwick93.accountable.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LiabilityService {

    private final LiabilityRepository liabilityRepository;

    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public List<Liability> getAll() {
        return liabilityRepository.findAllByUserId(jwtUtil.getAuthenticatedUserId());
    }

    @Transactional(readOnly = true)
    public Liability getById(int id) {
        return liabilityRepository.findByIdAndUserId(id, jwtUtil.getAuthenticatedUserId())
                .orElseThrow(NotFoundException.supplier("Liability with id " + id + " not found"));
    }

    @CachePut(value = "liability_cache", key = "#result.id", unless = "#result == null")
    public Liability create(Liability liability) {
        liability.setUserId(jwtUtil.getAuthenticatedUserId());
        return liabilityRepository.save(liability);
    }

    @CachePut(value = "liability_cache", key = "#result.id", unless = "#result == null")
    public Liability update(int id, Liability liability) {
        Liability existingLiability = getById(id);

        liability.setName(liability.getName());
        liability.setDescription(liability.getDescription());
        liability.setType(liability.getType());
        liability.setCurrency(liability.getCurrency());
        liability.setAmount(liability.getAmount());
        liability.setBalance(liability.getBalance());
        liability.setInterestRate(liability.getInterestRate());
        liability.setStatementDay(liability.getStatementDay());
        liability.setDueDay(liability.getDueDay());

        return liabilityRepository.save(existingLiability);
    }

    @CachePut(value = "liability_cache", key = "#result.id", unless = "#result == null")
    public Liability updateBalance(int id, BigDecimal amount, Currency currency) {
        Liability existingLiability = getById(id);

        if (!existingLiability.getCurrency().equals(currency)) {
            throw new BadRequestException("Currency " + currency + " does not match liability currency " + existingLiability.getCurrency());
        }

        existingLiability.setBalance(existingLiability.getBalance().add(amount));
        return liabilityRepository.save(existingLiability);
    }

    @CacheEvict(value = "liability_cache", key = "#id")
    public void delete(int id) {
        Liability existingLiability = getById(id);
        liabilityRepository.delete(existingLiability);
    }

}
