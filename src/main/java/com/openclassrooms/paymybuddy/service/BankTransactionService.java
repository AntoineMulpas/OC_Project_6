package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.Fees;
import com.openclassrooms.paymybuddy.model.TransactionDTO;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import com.openclassrooms.paymybuddy.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.openclassrooms.paymybuddy.utils.CalculateFees.calculateFee;

@Service
@Transactional
public class BankTransactionService {

    private final BankTransactionRepository bankTransactionRepository;
    private final IdOfUserAuthenticationService idOfUserAuthenticationService;
    private final AppAccountRepository appAccountRepository;
    private final FeeRepository feeRepository;

    @Autowired
    public BankTransactionService(BankTransactionRepository bankTransactionRepository, IdOfUserAuthenticationService idOfUserAuthenticationService, AppAccountRepository appAccountRepository, FeeRepository feeRepository) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.idOfUserAuthenticationService = idOfUserAuthenticationService;
        this.appAccountRepository = appAccountRepository;
        this.feeRepository = feeRepository;
    }

    public BankTransaction makeANewTransactionFromAppAccountToBankAccount(Double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Amount is not correct.");
        }

        Long userId = idOfUserAuthenticationService.getUserId();

        if (userId == null) {
            throw new RuntimeException("User id does not exist.");
        }

        AppAccount appAccount = appAccountRepository.findAppAccountByUserIdEquals(userId)
                .orElseThrow(() -> new RuntimeException("App Account does not exist."));

        double newSoldOfAppAccount = appAccount.getSold() - amount;
        if (newSoldOfAppAccount < 0) {
            throw new IllegalArgumentException("Your AppAccount cannot have a negative sold.");
        }

        appAccount.setSold(newSoldOfAppAccount);
        appAccountRepository.save(appAccount);

        BankTransaction bankTransactionToSave = new BankTransaction(
                userId,
                LocalDateTime.now(),
                -amount
        );

        BankTransaction savedBankTransaction = bankTransactionRepository.save(bankTransactionToSave);
        double feeToPerceive = calculateFee(amount);
        Fees fees = new Fees(
                savedBankTransaction.getId(),
                null,
                bankTransactionToSave.getDate(),
                feeToPerceive
        );
        feeRepository.save(fees);
        return savedBankTransaction;
    }




    public BankTransaction makeANewTransactionFromBankAccountToAppAccount(Double amount) {
        if (amount <= 0) {
            throw new RuntimeException("Amount is not correct.");
        }

        Long userId = idOfUserAuthenticationService.getUserId();
        if (userId == null) {
            throw new RuntimeException("User id does not exist.");
        }

        Optional<AppAccount> appAccount = appAccountRepository.findAppAccountByUserIdEquals(userId);
        if (appAccount.isEmpty()) {
            throw new RuntimeException("App Account does not exist.");
        }

        Double soldOfAppAccount = appAccount.get().getSold();
        Double newSoldOfAppAccount = soldOfAppAccount + amount;

        appAccount.get().setSold(newSoldOfAppAccount);
        appAccountRepository.save(appAccount.get());

        BankTransaction bankTransactionToSave = new BankTransaction(
                userId,
                LocalDateTime.now(),
                amount
        );


        BankTransaction savedBankTransaction = bankTransactionRepository.save(bankTransactionToSave);
        double feeToPerceive = calculateFee(amount);
        Fees fees = new Fees(
                savedBankTransaction.getId(),
                null,
                bankTransactionToSave.getDate(),
                feeToPerceive
        );
        feeRepository.save(fees);
        return savedBankTransaction;
    }


    public List<TransactionDTO> getListOfBankTransactionForCurrentUser() {
        Long userId = idOfUserAuthenticationService.getUserId();

        if (userId == null) {
            throw new RuntimeException("User id does not exist.");
        }

        return bankTransactionRepository.findTransactionByUserId(userId)
                .stream()
                .map(transaction -> new TransactionDTO(
                        transaction.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        transaction.getAmount() < 0 ? "AppAccount" : "BankAccount",
                        transaction.getAmount() < 0 ? "BankAccount" : "AppAccount",
                        Math.abs(transaction.getAmount())
                ))
                .collect(Collectors.toList());
    }
}
