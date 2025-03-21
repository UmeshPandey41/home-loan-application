package com.ms.homeloanapplication.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class LoanTypeDTO {
    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Double interestRate;

    @NotNull
    @Positive
    private Integer maxTenure;

    private String description;
}
