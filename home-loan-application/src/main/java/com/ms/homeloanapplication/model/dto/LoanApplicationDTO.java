package com.ms.homeloanapplication.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class LoanApplicationDTO {
    @NotNull
    private Long loanTypeId;

    @NotNull
    @Positive
    private Double loanAmount;

    @NotNull
    @Positive
    private Integer tenure;

    private String propertyAddress;

    @Positive
    private Double propertyValue;

    @Positive
    private Double annualIncome;

    // New fields for nominee details
    private String nomineeName;
    private String nomineeRelationship;
    private String nomineeContact;
}
