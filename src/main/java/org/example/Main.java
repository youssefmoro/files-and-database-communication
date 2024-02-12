package org.example;

import org.example.appLogic.Accounts;
import org.example.service.AccountsServices;
import org.example.service.InventoryServices;
import org.example.service.OrdersServices;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import static org.example.appLogic.Inventory.inventoryExecuteTasks;
import static org.example.appLogic.Orders.ordersExecuteTasks;
import static org.example.properties.Config.*;


public class Main {
    public static final ExecutorService executor = Executors.newScheduledThreadPool(MAX_THREADS);
     private static boolean OnOffProcessFlag=false;
    private static boolean allDoneFlag=true;

    public static void main(String[] args) {
        // delete your database and start over
        AccountsServices.deleteAccounts();
        OrdersServices.deleteOrders();
        InventoryServices.deleteInventory();
        //set the timer
        Timer timer = new Timer();
        TimerTask projectTask = new TimerTask() {
            @Override
            public void run() {
                OnOffProcessFlag = !OnOffProcessFlag; // here we Toggle the flag
                if (OnOffProcessFlag&&allDoneFlag) {
                    System.out.println("Process is ON");
                    long startTimeAccounts = System.nanoTime();
                    Accounts.accountsExecuteTasks();
                    // dataSource.close();
                    long endTimeAccounts = System.nanoTime();
                    long elapsedTime = endTimeAccounts - startTimeAccounts;
                    System.out.println("Accounts fullcycle for a time is " + elapsedTime);
                    long startTimeOrders = System.nanoTime();
                    ordersExecuteTasks();
                    long endTimeOrders = System.nanoTime();
                    long elapsedTimeOrders = endTimeOrders - startTimeOrders;
                    System.out.println("Orders fullcycle for a time is " + elapsedTimeOrders);
                    long startTimeInv = System.nanoTime();
                    inventoryExecuteTasks();
                    long endTimeInv = System.nanoTime();
                    long elapsedTimeInv = endTimeInv - startTimeInv;
                    System.out.println("Inv fullcycle for a time is " + elapsedTimeInv);
                   allDoneFlag=false;
                } else {
                    System.out.println("Process is on sleepMode");
                    allDoneFlag=true;
                }
            }
        };

        // Schedule the task to run every 30 sec configurable (300,000 milliseconds)
        timer.schedule(projectTask, 0, REPEATPROCESS);
        }

}
//..................................................
//public static void main(String[] args) {
//
//    CompletableFuture<List<File>> task1 = CompletableFuture.supplyAsync(() -> {
//        // Task 1: Get a list of all CSV files in the given path
//        List<File> list= Arrays.asList(fileListing(ACCOUNTS_FOLDER_PATH));
//        return list;
//    }, executor);
//
//    CompletableFuture<List<AccountModel>> task2 = task1.thenApplyAsync(list -> {
//        // Task 2: Parse CSV files and return a list of DataModels
//        return list.parallelStream()
//                .flatMap(file -> accountsModelConstrutor(file).stream())
//                .collect(Collectors.toList());
//    }, executor);
//
//    CompletableFuture<List<List<MysqlxCrud.DataModel>>> task3 = task2.thenApplyAsync(dataModels -> {
//        // Task 3: Break down data models into batches
//        return breakIntoBatches(dataModels, 50);
//    }, executor);
//
//    CompletableFuture<Void> task4 = task3.thenAcceptAsync(batches -> {
//        // Task 4: Insert each batch of data models into the database
//        for (List<DataModel> batch : batches) {
//            insertBatchIntoDatabase(batch);
//        }
//    }, executor);
//
//    CompletableFuture<Void> finalTask = task4.thenRunAsync(() -> {
//        // Final Task: Repeat the first task or handle completion
//        // ...
//    }, executor);
//
//    // Wait for the completion of all tasks
//    CompletableFuture.allOf(task1, task2, task3, task4, finalTask).join();
//
//    // Shutdown executor after all tasks are completed
//    executor.shutdown();
//}
//..................................................
// valid soln to optimize only the parsing time by doing that on parts but we still have the same problem of not optimizing the
//insertion by breaking down it into smaller parts "VALID SOLN"
//public class Main {
//    private static final ExecutorService accountsExecutor = Executors.newScheduledThreadPool(4);
//    public static void main(String[] args)throws SQLException {
//        //delete your database and start over
//        AccountsServices.deleteAccounts();
//        InventoryServices.deleteInventory();
//        OrdersServices.deleteOrders();
//        // task1 only listens to the path and returns a list of csvFiles inside that path
//        long startTimeAccounts=System.nanoTime();
//        CompletableFuture<List<File>> task1 = CompletableFuture.supplyAsync(() -> {
//            List<File> list= Arrays.asList(fileListing(ACCOUNTS_FOLDER_PATH));
//            return list;
//
//        }, accountsExecutor);
//        //task 2 is slighlty complicated,we will make looping, parsing and insertion inside it as inner tasks
//        CompletableFuture<List<CompletableFuture<List<AccountModel>>>> task2 = task1.thenApplyAsync(list->{
//            // we will map each file to a completablefuture representing the parsing task
//            return list.stream().map(file->CompletableFuture.supplyAsync(()-> {
//                //here we will parse a csv file and return a list of accountsmodel
//                List<AccountModel> accList= null;
//                try {
//                    accList = accountsModelConstrutor(file);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                return accList;
//            },accountsExecutor)).collect(Collectors.toList());
//        },accountsExecutor);
//        CompletableFuture<Void> task3 = task2.thenAcceptAsync(parsingTasks->
//        {
//            // Task 3: Wait for all parsing tasks to complete and insert DataModels into the database
//            List<AccountModel> accModels = parsingTasks.stream()
//                    .map(CompletableFuture::join)
//                    .flatMap(List::stream)
//                    .collect(Collectors.toList());
//            try {
//                accountsModelInserter(accModels);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }, accountsExecutor);
//        // Wait for the completion of all tasks
//        CompletableFuture.allOf(task1, task2, task3).join();
//        // Shutdown executor after all files are processed
//        long endTimeAccounts=System.nanoTime();
//        long elapsedTime=endTimeAccounts-startTimeAccounts;
//        System.out.println("Accounts fullcycle for a time is "+elapsedTime);
//        accountsExecutor.shutdown();
//
//    }
//
//}
//failed soln 1
//for (File file:list) {
//        //task 3 parse the data from each file
//        CompletableFuture<List<AccountModel>> parseTask = CompletableFuture.supplyAsync(()->{
//            List<AccountModel> accList= null;
//            try {
//                accList = accountsModelConstrutor(file);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            return accList;
//        }, accountsExecutor);
//
//        parseTask.thenAcceptAsync(accList->{
//            // Task 4: Insert AccountModels into the database
//            try {
//                accountsModelInserter(accList);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        },accountsExecutor).join();
//
//    }
//}, accountsExecutor);
//        //then we will wait for the completion of all tasks
//        CompletableFuture.allOf(task1, task2).join();
//// Shutdown executor after all files are processed
//        accountsExecutor.shutdown();
// before concurrency
////delete your database and start over
//        AccountsServices.deleteAccounts();
//        InventoryServices.deleteInventory();
//        OrdersServices.deleteOrders();
//       // tasks scheduled to parse and insert the data taking turns
//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
//        AccountsFullTaskPeriodic task1 = new AccountsFullTaskPeriodic("Accounts");
//        OrdersFullTaskPeriodic task2 = new OrdersFullTaskPeriodic("Orders");
//        org.example.InventoryFullTaskPeriodic task3 = new org.example.InventoryFullTaskPeriodic("Inventory");
//        // Schedule orders' task to run every 1 second
//        executorService.scheduleWithFixedDelay(task2, 0, 1, TimeUnit.SECONDS);
//        // Schedule accounts' task to run every 1 second
//        executorService.scheduleWithFixedDelay(task1, 0, 1, TimeUnit.SECONDS);
//
//         //Schedule Inventory's task to run every 1 second
//        executorService.scheduleWithFixedDelay(task3, 0, 1, TimeUnit.SECONDS);
//        //(number of threads that can work simultaneously without causing context switch)
//        System.out.println("available CPUs are: "+Runtime.getRuntime().availableProcessors());