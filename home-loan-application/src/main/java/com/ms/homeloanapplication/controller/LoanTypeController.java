package com.ms.homeloanapplication.controller;

import com.ms.homeloanapplication.model.dto.LoanTypeDTO;
import com.ms.homeloanapplication.model.entity.LoanType;
import com.ms.homeloanapplication.service.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/loan-types")
@RequiredArgsConstructor
public class LoanTypeController {
    private final LoanTypeService loanTypeService;

    @GetMapping
    public List<LoanType> getAllLoanTypes() {
        return loanTypeService.getAllLoanTypes();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LoanType> createLoanType(@Valid @RequestBody LoanTypeDTO loanTypeDTO) {
        LoanType loanType = new LoanType();
        loanType.setName(loanTypeDTO.getName());
        loanType.setInterestRate(loanTypeDTO.getInterestRate());
        loanType.setMaxTenure(loanTypeDTO.getMaxTenure());
        loanType.setDescription(loanTypeDTO.getDescription());
        return ResponseEntity.ok(loanTypeService.createLoanType(loanType));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LoanType> updateLoanType(@PathVariable Long id, @Valid @RequestBody LoanTypeDTO loanTypeDTO) {
        LoanType loanType = new LoanType();
        loanType.setName(loanTypeDTO.getName());
        loanType.setInterestRate(loanTypeDTO.getInterestRate());
        loanType.setMaxTenure(loanTypeDTO.getMaxTenure());
        loanType.setDescription(loanTypeDTO.getDescription());
        return ResponseEntity.ok(loanTypeService.updateLoanType(id, loanType));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteLoanType(@PathVariable Long id) {
        loanTypeService.deleteLoanType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanType> getLoanTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(loanTypeService.getLoanTypeById(id));
    }
}
