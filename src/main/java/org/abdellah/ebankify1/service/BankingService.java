package org.abdellah.ebankify1.service;

import lombok.RequiredArgsConstructor;
import org.abdellah.ebankify1.dto.AccountCreationDTO;
import org.abdellah.ebankify1.dto.AccountDTO;
import org.abdellah.ebankify1.dto.TransactionDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankingService {
    private final UserService userService;

    @Transactional
    public AccountDTO createPersonalAccount(AccountCreationDTO request) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return new AccountDTO();
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getCurrentUserTransactions() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return List.of();
    }

    @Transactional(readOnly = true)
    public AccountDTO getClientAccount(Long accountId) {
        return new AccountDTO();
    }

    @Transactional
    public TransactionDTO approveTransaction(Long transactionId) {
        return new TransactionDTO();
    }

    @Transactional
    public TransactionDTO rejectTransaction(Long transactionId) {
        return new TransactionDTO();
    }
}