package org.example.appLogic;
import org.example.models.OrderModel;
import org.example.periodicity.ordersTasks.BreakingDownAllOrderModelsIntoBatches;
import org.example.periodicity.ordersTasks.InsertOrdersBatchIntoDB;
import org.example.periodicity.ordersTasks.OrdersModelsConstructorTask;

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

import static org.example.Main.*;
import static org.example.periodicity.commonTasks.PathListnerTask.fileListing;
import static org.example.properties.Config.BATCH_FILES_PARSED;
import static org.example.properties.Config.ORDERS_FOLDER_PATH;
import static org.example.utils.Constants.*;
import static org.example.validation.Validation.*;

public class Orders {
    static List<String> prevOrdersParsed = new ArrayList<>();
    static String cypherOrders = "ajhlkcxvnxjkds55456";
    public static List<OrderModel> ordersBatch=new ArrayList<>(BATCH_FILES_PARSED);

    public static List<OrderModel> orderParser(String path) throws IOException {
        List<OrderModel> orderPassed = new ArrayList<>();
        Path filePath = Paths.get(path);
        File[] files = filePath.toFile().listFiles();
        if (files != null) {
            for (File inputFile : files) {
               orderPassed.addAll(ordersOnlyOneFileParser(inputFile));
            }
        }
        return orderPassed;
    }

    public static List<OrderModel> ordersOnlyOneFileParser(File inputFile) throws IOException {
        List<OrderModel> ordersOfOneFile = new ArrayList<>();
        if (!inputFile.isDirectory() && (inputFile.getName().startsWith("ord") || (inputFile.getName().endsWith(".csv"))) && (!inputFile.getName().contains("- Copy")) && (!prevOrdersParsed.contains(inputFile.getName() + cypherOrders))) {
            FileReader fileReader = new FileReader(inputFile);
            prevOrdersParsed.add(inputFile.getName().concat(cypherOrders));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int iterator = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokenWords = line.split(",");
                if ((tokenWords.length == ORDERS_RECORD_TOKENS_SIZE) && (allNumericValidatorInString(tokenWords[FIRST_TOKEN])) && (allNumericValidatorInString(tokenWords[SECOND_TOKEN]))) {
                    String orderNumber = tokenWords[FIRST_TOKEN];
                    String accountId = tokenWords[SECOND_TOKEN];
                    String tempToken = tokenWords[THIRD_TOKEN];
                    String[] tempFinal = tempToken.split(";");
                    String itemId = null;
                    String itemQuantity = null;
                    if ((allNumericValidatorInString(tempFinal[FIRST_TOKEN]))&&(allNumericValidatorInString(tempFinal[SECOND_TOKEN]))) {
                        itemId = tempFinal[FIRST_TOKEN];
                        itemQuantity = tempFinal[SECOND_TOKEN];
                        OrderModel order = new OrderModel(Integer.parseInt(orderNumber), accountId, itemId,itemQuantity);
                        ordersOfOneFile.add(order);
                        ordersBatch.add(order);
                    }
                }
            }
        }
        return ordersOfOneFile;
    }
    public static void ordersExecuteTasks() {
        CompletableFuture<List<File>> task1_Orders = CompletableFuture.supplyAsync(() -> {
            // Task 1: Get a list of all CSV files in the given path
            List<File> list = Arrays.asList(fileListing(ORDERS_FOLDER_PATH));
            return list;
        }, executor);

        CompletableFuture<List<OrderModel>> task2_Orders = task1_Orders.thenApplyAsync(list ->
                list.parallelStream()
                        .flatMap(file -> CompletableFuture.supplyAsync(() -> {
                            try {
                                return OrdersModelsConstructorTask.ordersModelsConstrutor(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }).join().stream())
                        .collect(Collectors.toList()), executor);

        CompletableFuture<List<List<OrderModel>>> task3_Orders = task2_Orders.thenApplyAsync(BreakingDownAllOrderModelsIntoBatches::breakingDownAllOrderModelsIntoBatches, executor);

        CompletableFuture<Void> task4_Orders = task3_Orders.thenAcceptAsync(batches -> {
            List<CompletableFuture<Void>> futures = batches.parallelStream()
                    .map(batch -> CompletableFuture.runAsync(() -> {
                        try {
                            InsertOrdersBatchIntoDB.insertBatchIntoDataBase(batch);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }, executor))
                    .collect(Collectors.toList());
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }, executor);

        CompletableFuture<Void> finalTaskOrders = task4_Orders.thenRunAsync(() -> {
            List<File> previousFiles = task1_Orders.join(); // Get the list of files from the previous task
            List<File> currentFiles = Arrays.asList(fileListing(ORDERS_FOLDER_PATH)); // Get the current list of files
            if (!currentFiles.equals(previousFiles)) {
                executeOrderTasksHelper();
                // Repeat all tasks if new files are found
            }
        }, executor);

        // Wait for the completion of all tasks
        CompletableFuture.allOf(task1_Orders, task2_Orders, task3_Orders, task4_Orders, finalTaskOrders).join();
        // executor.shutdown();
        // Shutdown executor after all tasks are completed

    }
    public static void executeOrderTasksHelper() {
        ordersExecuteTasks();
        // Recursive call to executeTasks method
    }
}

