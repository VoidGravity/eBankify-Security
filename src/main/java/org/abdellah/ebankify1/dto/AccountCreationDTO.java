package org.abdellah.ebankify1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.abdellah.ebankify1.model.AccountType;

import java.math.BigDecimal;

@Data
public class AccountCreationDTO {
    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "Initial deposit amount is required")
    @Positive(message = "Initial deposit must be positive")
    private BigDecimal initialDeposit;

    private String currency = "USD"; // Default currency
}
