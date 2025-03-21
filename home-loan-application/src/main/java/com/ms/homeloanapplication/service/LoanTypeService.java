package com.ms.homeloanapplication.service;

import com.ms.homeloanapplication.exception.EntityNotFoundException;
import com.ms.homeloanapplication.model.entity.LoanType;
import com.ms.homeloanapplication.repository.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanTypeService {
    private final LoanTypeRepository loanTypeRepository;

    public List<LoanType> getAllLoanTypes() {
        return loanTypeRepository.findAll();
    }

    public LoanType createLoanType(LoanType loanType) {
        return loanTypeRepository.save(loanType);
    }

    public LoanType updateLoanType(Long id, LoanType loanType) {
        if (!loanTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("LoanType", "id: " + id);
        }
        loanType.setId(id);
        return loanTypeRepository.save(loanType);
    }

    public void deleteLoanType(Long id) {
        if (!loanTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("LoanType", "id: " + id);
        }
        loanTypeRepository.deleteById(id);
    }

    public LoanType getLoanTypeById(Long id) {
        return loanTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LoanType", "id: " + id));
    }
}
