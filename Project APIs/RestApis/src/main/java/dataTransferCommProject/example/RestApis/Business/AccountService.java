package dataTransferCommProject.example.RestApis.Business;

import dataTransferCommProject.example.RestApis.Models.AccountModel;
import dataTransferCommProject.example.RestApis.Repo.AccountRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static dataTransferCommProject.example.RestApis.Utils.ConstantsAndConfig.*;

@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;
    public List<AccountModel> getAllAccountsInsideRepo()
    {
       return accountRepo.findAll();
    }
    public AccountModel saveAccount(AccountModel account)
    {
        return accountRepo.save(account);
    }
    public int updateAccount(AccountModel account,long idToBeUpdated)
    {
        AccountModel updatedAccount=accountRepo.findById(idToBeUpdated).get();
        updatedAccount.setFullName(account.getFullName());
        updatedAccount.setAddress(account.getAddress());
        updatedAccount.setEmail(account.getEmail());
        updatedAccount.setCardNumber(account.getCardNumber());
        updatedAccount.setMobileNumber(account.getMobileNumber());
        accountRepo.save(updatedAccount);
        return DONE;
        //(security features)TIME OUT CAN BE ADDED TO RETURN FAILED IF PROCESS NOT DONE WITHIN THIS AMOUNT OF TIME
    }
    public int deleteAccount(long accountId)
    {
        AccountModel deleteAccount=accountRepo.findById(accountId).get();
        accountRepo.delete(deleteAccount);
        return DONE;
        //(security features)TIME OUT CAN BE ADDED TO RETURN FAILED IF PROCESS NOT DONE WITHIN THIS AMOUNT OF TIME
    }
    public int deleteAllAccounts()
    {
        accountRepo.deleteAll();
        return DONE;
        //(security features)TIME OUT CAN BE ADDED TO RETURN FAILED IF PROCESS NOT DONE WITHIN THIS AMOUNT OF TIME
    }
    @Transactional
    public int addListOfAccounts(List<AccountModel> accounts)
    {
        if (accounts.size()>BATCH_ACCOUNTS) {
            List<List<AccountModel>> listOfList = breakingDownAllAccountModelsIntoBatches(accounts);
            for (List<AccountModel> accBatch : listOfList) {
                accountRepo.saveAll(accBatch);
            }
            return DONE;
        }
        else
        {
         accountRepo.saveAll(accounts);
         return DONE;
        }
    }
    public static List<List<AccountModel>> breakingDownAllAccountModelsIntoBatches(List<AccountModel> allAccounts)
    {
        int counter=0;
        List<List<AccountModel>> listOfLists=new ArrayList<>();
        int numOfListsRequired=allAccounts.size()/BATCH_ACCOUNTS;
        float diff=(float)((float)allAccounts.size()/(float)BATCH_ACCOUNTS)-numOfListsRequired;
        numOfListsRequired=(diff==0)?(numOfListsRequired):(numOfListsRequired+1);
        for(int i=0;i<numOfListsRequired;i++)
        {
            List<AccountModel> innerList = new ArrayList<>();
            for (int j=0;j<BATCH_ACCOUNTS;j++)
            {
                if (counter < allAccounts.size()) {
                    innerList.add(allAccounts.get(counter));
                    counter++;
                } else {
                    break; // Break the loop if all accounts have been processed
                }
            }
            listOfLists.add(innerList);
        }
        return listOfLists;
    }
}
