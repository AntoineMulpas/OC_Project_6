package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.Fees;
import com.openclassrooms.paymybuddy.model.TransactionDTO;
import com.openclassrooms.paymybuddy.repository.AppAccountRepository;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import com.openclassrooms.paymybuddy.repository.FeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private static final Logger logger = LogManager.getLogger(BankTransactionService.class);


    @Autowired
    public BankTransactionService(BankTransactionRepository bankTransactionRepository, IdOfUserAuthenticationService idOfUserAuthenticationService, AppAccountRepository appAccountRepository, FeeRepository feeRepository) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.idOfUserAuthenticationService = idOfUserAuthenticationService;
        this.appAccountRepository = appAccountRepository;
        this.feeRepository = feeRepository;
    }

    public BankTransaction makeANewTransactionFromAppAccountToBankAccount(Double amount) {
        if (amount <= 0) {
            logger.error("Amount is not correct for transaction from App to Bank for user " + SecurityContextHolder.getContext().getAuthentication().getName());
            throw new RuntimeException("Amount is not correct.");
        }

        Long userId = idOfUserAuthenticationService.getUserId();

        if (userId == null) {
            logger.error("User id does not exist for transaction from App to Bank for user " + SecurityContextHolder.getContext().getAuthentication().getName());
            throw new RuntimeException("User id does not exist.");
        }

        AppAccount appAccount = appAccountRepository.findAppAccountByUserIdEquals(userId)
                .orElseThrow(() -> {
                    logger.error("App account does not exist for user " + SecurityContextHolder.getContext().getAuthentication().getName());
                    return new RuntimeException("App Account does not exist.");
                });

        double newSoldOfAppAccount = appAccount.getSold() - amount;
        if (newSoldOfAppAccount < 0) {
            logger.error("Your AppAccount cannot have a negative sold. Error for user " + SecurityContextHolder.getContext().getAuthentication().getName());
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
        logger.info("User " + SecurityContextHolder.getContext().getAuthentication().getName() + " has successfully made a transaction from App to Bank.");
        return savedBankTransaction;
    }




    public BankTransaction makeANewTransactionFromBankAccountToAppAccount(Double amount) {
        if (amount <= 0) {
            logger.error("Amount is not correct for transaction from Bank to App for user " + SecurityContextHolder.getContext().getAuthentication().getName());
            throw new RuntimeException("Amount is not correct.");
        }

        Long userId = idOfUserAuthenticationService.getUserId();
        if (userId == null) {
            logger.error("User id does not exist for transaction from Bank to App for user " + SecurityContextHolder.getContext().getAuthentication().getName());
            throw new RuntimeException("User id does not exist.");
        }

        Optional<AppAccount> appAccount = appAccountRepository.findAppAccountByUserIdEquals(userId);
        if (appAccount.isEmpty()) {
            logger.error("App account does not exist for user " + SecurityContextHolder.getContext().getAuthentication().getName());
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
        logger.info("User " + SecurityContextHolder.getContext().getAuthentication().getName() + " has successfully made a transaction from Bank to App.");
        return savedBankTransaction;
    }


    public List<TransactionDTO> getListOfBankTransactionForCurrentUser() {
        Long userId = idOfUserAuthenticationService.getUserId();

        if (userId == null) {
            logger.error("User " + SecurityContextHolder.getContext().getAuthentication().getName() + " tried to fetch list of bank transaction but User Id does not exist.");
            throw new RuntimeException("User id does not exist.");
        }

        logger.info("User " + SecurityContextHolder.getContext().getAuthentication().getName() + " has successfully fetched list of bank transaction.");
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
