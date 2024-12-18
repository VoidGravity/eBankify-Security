package org.abdellah.ebankify1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.abdellah.ebankify1.dto.AccountCreationDTO;
import org.abdellah.ebankify1.dto.AccountDTO;
import org.abdellah.ebankify1.dto.TransactionDTO;
import org.abdellah.ebankify1.service.BankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final BankingService bankingService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/accounts")
    public ResponseEntity<AccountDTO> createPersonalAccount(@Valid @RequestBody AccountCreationDTO request) {
        return ResponseEntity.ok(bankingService.createPersonalAccount(request));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getPersonalTransactions() {
        return ResponseEntity.ok(bankingService.getCurrentUserTransactions());
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountDTO> getClientAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(bankingService.getClientAccount(accountId));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/transactions/{transactionId}/approve")
    public ResponseEntity<TransactionDTO> approveTransaction(@PathVariable Long transactionId) {
        return ResponseEntity.ok(bankingService.approveTransaction(transactionId));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/transactions/{transactionId}/reject")
    public ResponseEntity<TransactionDTO> rejectTransaction(@PathVariable Long transactionId) {
        return ResponseEntity.ok(bankingService.rejectTransaction(transactionId));
    }
}