package com.ms.homeloanapplication.service;

import com.ms.homeloanapplication.model.dto.LoanApplicationDTO;
import com.ms.homeloanapplication.model.entity.LoanApplication;
import com.ms.homeloanapplication.model.entity.User;
import com.ms.homeloanapplication.repository.LoanApplicationRepository;
import com.ms.homeloanapplication.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {
    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanTypeService loanTypeService;

    @InjectMocks
    private LoanApplicationService loanApplicationService;

    @Test
    void getAllApplications() {
        List<LoanApplication> applications = Collections.singletonList(new LoanApplication());
        when(loanApplicationRepository.findAll()).thenReturn(applications);

        List<LoanApplication> result = loanApplicationService.getAllApplications();

        assertEquals(applications, result);
        verify(loanApplicationRepository).findAll();
    }

    @Test
    void createApplication() {
        LoanApplication application = new LoanApplication();
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(loanApplicationRepository.save(any())).thenReturn(application);

        LoanApplication result = loanApplicationService.createApplication(application, "testUser");

        assertNotNull(result);
        assertEquals(user, result.getUser());
        verify(loanApplicationRepository).save(application);
    }

    @Test
    void updateApplication_Success() {
        Long id = 1L;
        String username = "testUser";
        LoanApplication existingApplication = new LoanApplication();
        User user = new User();
        user.setUsername(username);
        existingApplication.setUser(user);

        LoanApplicationDTO applicationDTO = new LoanApplicationDTO();
        applicationDTO.setLoanAmount(100000.00);

        when(loanApplicationRepository.findById(id)).thenReturn(Optional.of(existingApplication));
        when(loanApplicationRepository.save(any())).thenReturn(existingApplication);

        LoanApplication result = loanApplicationService.updateApplication(id, applicationDTO, username);

        assertNotNull(result);
        verify(loanApplicationRepository).save(any());
    }

    @Test
    void updateApplication_NotAuthorized() {
        Long id = 1L;
        LoanApplication existingApplication = new LoanApplication();
        User user = new User();
        user.setUsername("otherUser");
        existingApplication.setUser(user);

        when(loanApplicationRepository.findById(id)).thenReturn(Optional.of(existingApplication));

        assertThrows(AccessDeniedException.class,
                () -> loanApplicationService.updateApplication(id, new LoanApplicationDTO(), "testUser"));
    }

    @Test
    void deleteApplication_Success() {
        Long id = 1L;
        String username = "testUser";
        LoanApplication application = new LoanApplication();
        User user = new User();
        user.setUsername(username);
        application.setUser(user);

        when(loanApplicationRepository.findById(id)).thenReturn(Optional.of(application));
        doNothing().when(loanApplicationRepository).deleteById(id);

        assertDoesNotThrow(() -> loanApplicationService.deleteApplication(id, username));
        verify(loanApplicationRepository).deleteById(id);
    }

    @Test
    void updateStatus() {
        Long id = 1L;
        LoanApplication application = new LoanApplication();
        String newStatus = "APPROVED";

        when(loanApplicationRepository.findById(id)).thenReturn(Optional.of(application));
        when(loanApplicationRepository.save(any())).thenReturn(application);

        LoanApplication result = loanApplicationService.updateStatus(id, newStatus);

        assertNotNull(result);
        assertEquals(newStatus, result.getStatus());
        verify(loanApplicationRepository).save(application);
    }
}