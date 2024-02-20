package dataTransferCommProject.example.RestApis.RESTcontroller;

import dataTransferCommProject.example.RestApis.Models.AccountModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import dataTransferCommProject.example.RestApis.Business.*;

import java.util.List;

import static dataTransferCommProject.example.RestApis.Utils.ConstantsAndConfig.DONE;

@RestController
public class AccountsApiController {
    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/")
    public String getPage() {
        return "welcome";
    }

    @GetMapping(value = "/allAccounts")
    public List<AccountModel> getAllAccount() {
        return accountService.getAllAccountsInsideRepo();
    }

    @PostMapping(value = "/saveAccount")
    public String createAccount(@RequestBody @Valid AccountModel account) {
        accountService.saveAccount(account);
        return "saved";
    }

    @PutMapping(value = "/update/{accountId}")
    public String updateAccount(@PathVariable long accountId, @RequestBody @Valid AccountModel account) {
        if (accountService.updateAccount(account, accountId) == DONE) {
            return "updated successfully";
        } else {
            return "update failed";
        }
    }

    @DeleteMapping(value = "/delete/{accountId}")
    public String deleteAccount(@PathVariable long accountId) {
        if (accountService.deleteAccount(accountId) == DONE) {
            return "deleted account with id:" + accountId;
        } else return "deletion failed";

    }
    @DeleteMapping(value = "/deleteAll")
    public String deleteAllAccounts() {
      accountService.deleteAllAccounts();
            return "All deleted";
    }
    @PostMapping(value = "/saveAllAccounts")
    public String createAccounts(@RequestBody List<AccountModel> accounts) {
        accountService.addListOfAccounts(accounts);
        return "saved successfully";
    }
}
