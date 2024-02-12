package org.example.appLogic;

import org.example.models.AccountModel;
import org.example.periodicity.accountsTasks.BreakingDownAllAccountModelsIntoBatches;
import org.example.periodicity.accountsTasks.InsertAccountsBatchIntoDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.example.Main.executor;
import static org.example.periodicity.accountsTasks.AccountsModelsConstrutorTask.accountsModelConstrutor;
import static org.example.periodicity.commonTasks.PathListnerTask.fileListing;
import static org.example.properties.Config.ACCOUNTS_FOLDER_PATH;
import static org.example.properties.Config.BATCH_FILES_PARSED;
import static org.example.utils.Constants.*;
import static org.example.validation.Validation.*;


public class Accounts {
    private static List<String> prevAccountsParsed = new ArrayList<>();
   private static String cypherAccounts = "123asa15as6das45das56d4";
    private static List<AccountModel> accountsBatch=new ArrayList<>(BATCH_FILES_PARSED);
        public static List<AccountModel> accountsOnlyOneFileParser(File inputFile) throws IOException {
             List<AccountModel> accountsOfOneFile = new ArrayList<>();
            if ((!inputFile.isDirectory()) && ((inputFile.getName().startsWith("acc")) || (inputFile.getName().endsWith(".csv"))) && (!inputFile.getName().contains("- Copy"))&& (!prevAccountsParsed.contains(inputFile.getName() + cypherAccounts))) {
                prevAccountsParsed.add(inputFile.getName().concat(cypherAccounts));
                //processedAccounts.add(inputFile.getName());
                //System.out.println(inputFile.getName());
                FileReader fileReader = new FileReader(inputFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                int iterator = 0;
                while ((line = bufferedReader.readLine()) != null) {

                    String[] tokenWords;
                    tokenWords = line.split(",");
                    if (tokenWords.length == ACCOUNT_RECORD_TOKENS_SIZE) {
                        String accountID = tokenWords[FIRST_TOKEN];
                        String mobileNumber = tokenWords[SECOND_TOKEN];
                        String email = tokenWords[THIRD_TOKEN];
                        String fullName = tokenWords[FOURTH_TOKEN];
                        String address = tokenWords[FIFTH_TOKEN];
                        String cardNumber = tokenWords[SIXTH_TOKEN];
                        if (allNumericValidatorInString(accountID) && mobileNumberValidatorInString(mobileNumber) && emailValidator(email)) {
                            AccountModel account = new AccountModel(Integer.parseInt(accountID), mobileNumber, fullName, email, address, cardNumber);
                            accountsOfOneFile.add(account);
                            accountsBatch.add(account);
                        }
                    }
                }
            }
            return accountsOfOneFile;
        }
    public static void accountsExecuteTasks() {
        long startTimeAccounts=System.nanoTime();
        CompletableFuture<List<File>> task1 = CompletableFuture.supplyAsync(() -> {
            // Task 1: Get a list of all CSV files in the given path
            List<File> list = Arrays.asList(fileListing(ACCOUNTS_FOLDER_PATH));
            return list;
        }, executor);
        //SOLN2
        CompletableFuture<List<AccountModel>> task2 = task1.thenApplyAsync(list ->
                list.parallelStream()
                        .flatMap(file -> CompletableFuture.supplyAsync(() -> {
                            try {
                                return accountsModelConstrutor(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }).join().stream())
                        .collect(Collectors.toList()), executor);
        //SOLN 1
//        CompletableFuture<List<AccountModel>> task2 = task1.thenApplyAsync(list -> {
//            // Task 2: Parse CSV files and return a list of DataModels
//            return list.parallelStream()
//                    .flatMap(file -> {
//                        try {
//                            return accountsModelConstrutor(file).stream();
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    })
//                    .collect(Collectors.toList());
//        }, executor);

        // Task 3: Break down data models into batches
        CompletableFuture<List<List<AccountModel>>> task3 = task2.thenApplyAsync(BreakingDownAllAccountModelsIntoBatches::breakingDownAllAccountModelsIntoBatches, executor);

        //SLON 3
        CompletableFuture<Void> task4 = task3.thenAcceptAsync(batches -> {
            List<CompletableFuture<Void>> futures = batches.parallelStream()
                    .map(batch -> CompletableFuture.runAsync(() -> {
                        try {
                            InsertAccountsBatchIntoDB.insertBatchIntoDataBase(batch);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);

                        }
                    }, executor))
                    .collect(Collectors.toList());
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }, executor);
        //SOLN2
//        CompletableFuture<Void> task4 = task3.thenAcceptAsync(batches ->
//                batches.parallelStream().forEach(batch -> {
//                    try {
//                        InsertOrdersBatchIntoDB.insertBatchIntoDataBase(batch);
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                }), executor);
        //SOLN1
//        CompletableFuture<Void> task4 = task3.thenAcceptAsync(batches -> {
//            // Task 4: Insert each batch of data models into the database
//            for (List<AccountModel> batch : batches) {
//                try {
//                    InsertOrdersBatchIntoDB.insertBatchIntoDataBase(batch);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }, executor);

        CompletableFuture<Void> finalTask = task4.thenRunAsync(() -> {
            List<File> previousFiles = task1.join(); // Get the list of files from the previous task
            List<File> currentFiles = Arrays.asList(fileListing(ACCOUNTS_FOLDER_PATH)); // Get the current list of files
            if (!currentFiles.equals(previousFiles)) {
                executeAccountsTasksHelper();
                // Repeat all tasks if new files are found
            }
        }, executor);

        // Wait for the completion of all tasks
        CompletableFuture.allOf(task1, task2, task3, task4, finalTask).join();
        // executor.shutdown();
        // Shutdown executor after all tasks are completed

    }
    public static void executeAccountsTasksHelper() {
        accountsExecuteTasks(); // Recursive call to executeTasks method
    }

}


