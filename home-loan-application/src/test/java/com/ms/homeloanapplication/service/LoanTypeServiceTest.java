package com.ms.homeloanapplication.service;

import com.ms.homeloanapplication.exception.EntityNotFoundException;
import com.ms.homeloanapplication.model.entity.LoanType;
import com.ms.homeloanapplication.repository.LoanTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanTypeServiceTest {
    @Mock
    private LoanTypeRepository loanTypeRepository;

    @InjectMocks
    private LoanTypeService loanTypeService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void getAllLoanTypes() {
        List<LoanType> loanTypes = Collections.singletonList(new LoanType());
        when(loanTypeRepository.findAll()).thenReturn(loanTypes);

        List<LoanType> result = loanTypeService.getAllLoanTypes();

        assertEquals(loanTypes, result);
        verify(loanTypeRepository).findAll();
    }

    @Test
    void createLoanType() {
        LoanType loanType = new LoanType();
        when(loanTypeRepository.save(any())).thenReturn(loanType);

        LoanType result = loanTypeService.createLoanType(loanType);

        assertNotNull(result);
        verify(loanTypeRepository).save(loanType);
    }

    @Test
    void updateLoanType_Success() {
        Long id = 1L;
        LoanType loanType = new LoanType();

        when(loanTypeRepository.existsById(id)).thenReturn(true);
        when(loanTypeRepository.save(any())).thenReturn(loanType);

        LoanType result = loanTypeService.updateLoanType(id, loanType);

        assertNotNull(result);
        verify(loanTypeRepository).save(loanType);
    }

    @Test
    void updateLoanType_NotFound() {
        Long id = 1L;
        LoanType loanType = new LoanType();

        when(loanTypeRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> loanTypeService.updateLoanType(id, loanType));
        verify(loanTypeRepository, never()).save(any());
    }

    @Test
    void getLoanTypeById_Success() {
        Long id = 1L;
        LoanType loanType = new LoanType();

        when(loanTypeRepository.findById(id)).thenReturn(Optional.of(loanType));

        LoanType result = loanTypeService.getLoanTypeById(id);

        assertNotNull(result);
        verify(loanTypeRepository).findById(id);
    }

    @Test
    void getLoanTypeById_NotFound() {
        Long id = 1L;
        when(loanTypeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> loanTypeService.getLoanTypeById(id));
    }
}