package org.example.periodicity.accountsTasks;

import org.example.dataBaseCommunication.AccountsCommDb;
import org.example.models.AccountModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AccountModelsInserterTask {
    public static void accountsModelInserter(List<AccountModel> accList) throws SQLException, IOException {
        AccountsCommDb.accountsCommunicator(accList);
    }
}
