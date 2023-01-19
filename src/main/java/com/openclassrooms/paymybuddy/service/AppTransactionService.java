package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.AppTransaction;
import com.openclassrooms.paymybuddy.model.TransactionDTO;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.AppTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppTransactionService {

    private final AppTransactionRepository appTransactionRepository;
    private final AppAccountService appAccountService;
    private final IdOfUserAuthenticationService idOfUserAuthenticationService;
    private final UserService userService;

    @Autowired
    public AppTransactionService(AppTransactionRepository appTransactionRepository, AppAccountService appAccountService, IdOfUserAuthenticationService idOfUserAuthenticationService, UserService userService) {
        this.appTransactionRepository = appTransactionRepository;
        this.appAccountService = appAccountService;
        this.idOfUserAuthenticationService = idOfUserAuthenticationService;
        this.userService = userService;
    }

/*
/// ADD UPDATED OF SOLD ACCOUNT
 */
    public AppTransaction makeANewAppTransaction(Long receiverId, Double amount) {
            if (receiverId != null && amount != null && amount > 0) {
                if (idOfUserAuthenticationService.userIdExists(receiverId)) {
                    Long senderId = idOfUserAuthenticationService.getUserId();

                    appAccountService.updateSoldOfAppAccount(senderId, -amount);
                    appAccountService.updateSoldOfAppAccount(receiverId, amount);

                    AppTransaction transactionToSave = new AppTransaction(
                            senderId,
                            receiverId,
                            LocalDateTime.now(),
                            amount
                    );
                    return appTransactionRepository.save(transactionToSave);
                } else {
                    throw new RuntimeException("Receiver id: " + receiverId + " does not exist.");
                }
            } else {
                throw new RuntimeException("An error occurred.");
            }
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
