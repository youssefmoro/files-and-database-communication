package org.example.periodicity.accountsTasks;

import org.example.models.AccountModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.example.appLogic.Accounts.accountsOnlyOneFileParser;

public class AccountsModelsConstrutorTask {

    public static List<AccountModel> accountsModelConstrutor(File file) throws IOException {
        List<AccountModel> accList=accountsOnlyOneFileParser(file);
        return accList;
    }
}
