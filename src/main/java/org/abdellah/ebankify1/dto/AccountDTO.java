package org.abdellah.ebankify1.dto;


import lombok.Data;
import org.abdellah.ebankify1.model.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal balance;
    private String currency;
    private boolean active;
    private LocalDateTime createdAt;
    private Long userId;
}
