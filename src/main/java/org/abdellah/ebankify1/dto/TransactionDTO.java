package org.abdellah.ebankify1.dto;


import lombok.Data;
import org.abdellah.ebankify1.model.TransactionStatus;
import org.abdellah.ebankify1.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;
    private String transactionReference;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private LocalDateTime timestamp;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private String description;
}

