package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.controller.AppAccountController;
import com.openclassrooms.paymybuddy.model.AppTransaction;
import com.openclassrooms.paymybuddy.model.Fees;
import com.openclassrooms.paymybuddy.model.TransactionDTO;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.AppTransactionRepository;
import com.openclassrooms.paymybuddy.repository.FeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.openclassrooms.paymybuddy.utils.CalculateFees.calculateFee;

@Service
@Transactional
public class AppTransactionService {

    private final AppTransactionRepository appTransactionRepository;
    private final AppAccountService appAccountService;
    private final IdOfUserAuthenticationService idOfUserAuthenticationService;
    private final UserService userService;
    private final FeeRepository feeRepository;

    private static final Logger logger = LogManager.getLogger(AppTransactionService.class);


    @Autowired
    public AppTransactionService(AppTransactionRepository appTransactionRepository, AppAccountService appAccountService, IdOfUserAuthenticationService idOfUserAuthenticationService, UserService userService, FeeRepository feeRepository) {
        this.appTransactionRepository = appTransactionRepository;
        this.appAccountService = appAccountService;
        this.idOfUserAuthenticationService = idOfUserAuthenticationService;
        this.userService = userService;
        this.feeRepository = feeRepository;
    }

    public AppTransaction makeANewAppTransaction(Long receiverId, Double amount) {
        validateTransaction(receiverId, amount);
        Long senderId = idOfUserAuthenticationService.getUserId();
        checkFunds(amount);
        updateAccounts(senderId, receiverId, amount);
        AppTransaction transactionToSave = createTransaction(senderId, receiverId, amount);
        AppTransaction savedAppTransaction = appTransactionRepository.save(transactionToSave);
        saveFees(savedAppTransaction);
        logger.info("User " + SecurityContextHolder.getContext().getAuthentication().getName() + " has made a new AppTransaction to " + receiverId);
        return savedAppTransaction;
    }

    private void validateTransaction(Long receiverId, Double amount) {
        if (receiverId == null || amount == null || amount <= 0) {
            logger.error("Invalid receiverId or amount error for user " + SecurityContextHolder.getContext().getAuthentication().getName() + " while making a new AppTransaction.") ;
            throw new IllegalArgumentException("Invalid receiverId or amount.");
        }
        if (!idOfUserAuthenticationService.userIdExists(receiverId)) {
            logger.error("Receiver id does not exist error for user " + SecurityContextHolder.getContext().getAuthentication().getName() + " while making a new AppTransaction.") ;
            throw new IllegalArgumentException("Receiver id does not exist.");
        }
    }

    private void checkFunds(Double amount) {
        Double actualSoldOfSenderId = appAccountService.getSoldOfAccount();
        if (actualSoldOfSenderId - amount < 0) {
            logger.error("Not enough funds for AppTransaction for user " + SecurityContextHolder.getContext().getAuthentication().getName());
            throw new IllegalArgumentException("You don't have enough funds to make this transaction.");
        }
    }

    private void updateAccounts(Long senderId, Long receiverId, Double amount) {
        appAccountService.updateSoldOfAppAccount(senderId, -amount);
        appAccountService.updateSoldOfAppAccount(receiverId, amount);
    }

    private AppTransaction createTransaction(Long senderId, Long receiverId, Double amount) {
        return new AppTransaction(senderId, receiverId, LocalDateTime.now(), amount);
    }

    private void saveFees(AppTransaction savedAppTransaction) {
        double feeToPerceive = calculateFee(savedAppTransaction.getAmount());
        Fees fees = new Fees(
                null,
                savedAppTransaction.getId(),
                savedAppTransaction.getLocalDateTime(),
                feeToPerceive
        );
        feeRepository.save(fees);
    }



    public List<TransactionDTO> getListOfAppTransactionForCurrentUser() {
        Long userId = idOfUserAuthenticationService.getUserId();
        List <AppTransaction> appTransactionList = appTransactionRepository.findByIdOfCurrentUser(userId);
        List<TransactionDTO> listToReturn = new ArrayList <>();


        appTransactionList.forEach(appTransaction -> {
            User senderInformation = userService.getUserInformation(appTransaction.getSenderId());
            User receiverInformation = userService.getUserInformation(appTransaction.getReceiverId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            TransactionDTO transactionDTO = new TransactionDTO(
                    appTransaction.getLocalDateTime().format(formatter),
                    senderInformation.getFirstName() + " " + senderInformation.getLastName(),
                    receiverInformation.getFirstName() + " " + receiverInformation.getLastName(),
                    appTransaction.getAmount()
            );

            listToReturn.add(transactionDTO);
        });

        return listToReturn;
    }
}
