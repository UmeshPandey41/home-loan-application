package com.ms.homeloanapplication.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loan_applications")
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_type_id", nullable = false)
    private LoanType loanType;

    @NotNull
    @Positive
    private Double loanAmount;

    @NotNull
    @Positive
    private Integer tenure;

    private String status = "PENDING";

    @Column(name = "application_date")
    private String applicationDate;

    private String propertyAddress;

    private Double propertyValue;

    private Double annualIncome;

    // New fields for nominee details
    private String nomineeName;
    private String nomineeRelationship;
    private String nomineeContact;

    @PrePersist
    public void prePersist() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy, h:mm a");
        this.applicationDate = LocalDateTime.now().format(formatter);
    }
}
