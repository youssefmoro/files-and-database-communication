package org.example.periodicity;
import org.example.dataBaseCommunication.AccountsCommDb;
import org.example.models.AccountModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import static org.example.appLogic.Accounts.accountsBatch;
import static org.example.appLogic.Accounts.accountsOnlyOneFileParser;
import static org.example.appLogic.GeneralServices.fileListing;
import static org.example.properties.Config.ACCOUNTS_FOLDER_PATH;
import static org.example.properties.Config.BATCH_FILES_PARSED;

public class AccountsFullTaskPeriodic implements Runnable {

    private String taskName;

    public AccountsFullTaskPeriodic(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run()  {
        // List<AccountModel> accList;
        //                accList = accountsParser("D:\\task1 24-1-2024\\Accounts Folder");
//                accList.forEach(System.out::println);
        try {
            long startTimeAccounts=System.nanoTime();
            List<AccountModel> accList;
            //accList = accountsParser(ACCOUNTS_FOLDER_PATH);
            //TASK1 (NOT BASED ON ANY TASK)
           File[] accountsFiles= (File[]) fileListing(ACCOUNTS_FOLDER_PATH);
           //..............
            for (File file : accountsFiles) {
                try {
                    //TASK2 -BASED ON TASK 1 MUST HAVING A ONE FILE AT LEAST IN HAND ,JUST PARSE IT AND RETURN A LIST OF ACCOUNTS
                    accList = accountsOnlyOneFileParser(file);
                    //....................
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                long parsingTimeAccounts=System.nanoTime();
                long parsingElapsedTime=parsingTimeAccounts-startTimeAccounts;
                System.out.println("accounts' Parsing time is:"+parsingElapsedTime);
                //TASK 3 -BASED ON TASK 2 WHEN HAVING A LIST OF ACCOUNTS IN HAND - INSERT INTO DATABASE
                AccountsCommDb.accountsCommunicator(accList);
                //........................
            }
            System.out.println("accounts' Thread is running...");
            long endTimeAccounts=System.nanoTime();
            long elapsedTime=endTimeAccounts-startTimeAccounts;
            System.out.println("Accounts fullcycle for a time is "+elapsedTime);

        } catch (IOException e) {
            throw new RuntimeException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}