package com.ms.homeloanapplication.service;

import com.ms.homeloanapplication.exception.EntityNotFoundException;
import com.ms.homeloanapplication.model.entity.LoanApplication;
import com.ms.homeloanapplication.model.entity.User;
import com.ms.homeloanapplication.repository.LoanApplicationRepository;
import com.ms.homeloanapplication.repository.LoanTypeRepository;
import com.ms.homeloanapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.ms.homeloanapplication.model.dto.LoanApplicationDTO;
import com.ms.homeloanapplication.service.LoanTypeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final UserRepository userRepository;
    private final LoanTypeService loanTypeService;

    public List<LoanApplication> getAllApplications() {
        return loanApplicationRepository.findAll();
    }

    public List<LoanApplication> getUserApplications(Long userId) {
        return loanApplicationRepository.findByUserId(userId);
    }

    public LoanApplication createApplication(LoanApplication application, String username) {
        User user = userRepository.findByUsername(username);
        application.setUser(user);
        return loanApplicationRepository.save(application);
    }

    public LoanApplication updateApplication(Long id, LoanApplicationDTO applicationDTO, String username) {
        LoanApplication existingApplication = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LoanApplication", "id: " + id));

        if (!existingApplication.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("Not authorized to update this application");
        }

        // Set the loan type first
        existingApplication.setLoanType(loanTypeService.getLoanTypeById(applicationDTO.getLoanTypeId()));

        // Update other fields
        existingApplication.setLoanAmount(applicationDTO.getLoanAmount());
        existingApplication.setTenure(applicationDTO.getTenure());
        existingApplication.setPropertyAddress(applicationDTO.getPropertyAddress());
        existingApplication.setPropertyValue(applicationDTO.getPropertyValue());
        existingApplication.setAnnualIncome(applicationDTO.getAnnualIncome());
        existingApplication.setNomineeName(applicationDTO.getNomineeName());
        existingApplication.setNomineeRelationship(applicationDTO.getNomineeRelationship());
        existingApplication.setNomineeContact(applicationDTO.getNomineeContact());

        return loanApplicationRepository.save(existingApplication);
    }


    public void deleteApplication(Long id, String username) {
        LoanApplication application = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LoanApplication", "id: " + id));

        if (!application.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("Not authorized to delete this application");
        }

        loanApplicationRepository.deleteById(id);
    }

    public LoanApplication updateStatus(Long id, String status) {
        LoanApplication application = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LoanApplication", "id: " + id));
        application.setStatus(status);
        return loanApplicationRepository.save(application);
    }

    public LoanApplication getApplicationById(Long id) {
        return loanApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LoanApplication", "id: " + id));
    }
}
