package com.ms.homeloanapplication.controller;

import com.ms.homeloanapplication.model.dto.LoanApplicationDTO;
import com.ms.homeloanapplication.model.entity.LoanApplication;
import com.ms.homeloanapplication.model.entity.User;
import com.ms.homeloanapplication.repository.UserRepository;
import com.ms.homeloanapplication.service.LoanApplicationService;
import com.ms.homeloanapplication.service.LoanTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;
    private final UserRepository userRepository;
    private final LoanTypeService loanTypeService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<LoanApplication> getAllApplications() {
        return loanApplicationService.getAllApplications();
    }

    @GetMapping("/my-applications")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public List<LoanApplication> getUserApplications(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        return loanApplicationService.getUserApplications(user.getId());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<LoanApplication> getApplicationById(@PathVariable Long id) {
        LoanApplication application = loanApplicationService.getApplicationById(id);
        return ResponseEntity.ok(application);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<LoanApplication> createApplication(
            @Valid @RequestBody LoanApplicationDTO applicationDTO,
            Authentication authentication) {
        LoanApplication application = new LoanApplication();
        application.setLoanAmount(applicationDTO.getLoanAmount());
        application.setTenure(applicationDTO.getTenure());
        application.setPropertyAddress(applicationDTO.getPropertyAddress());
        application.setPropertyValue(applicationDTO.getPropertyValue());
        application.setAnnualIncome(applicationDTO.getAnnualIncome());
        application.setLoanType(loanTypeService.getLoanTypeById(applicationDTO.getLoanTypeId()));

        // Set nominee details
        application.setNomineeName(applicationDTO.getNomineeName());
        application.setNomineeRelationship(applicationDTO.getNomineeRelationship());
        application.setNomineeContact(applicationDTO.getNomineeContact());

        return ResponseEntity.ok(loanApplicationService.createApplication(application, authentication.getName()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<LoanApplication> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody LoanApplicationDTO applicationDTO,
            Authentication authentication) {
        return ResponseEntity.ok(loanApplicationService.updateApplication(id, applicationDTO, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id,
            Authentication authentication) {
        loanApplicationService.deleteApplication(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LoanApplication> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(loanApplicationService.updateStatus(id, status));
    }
}
