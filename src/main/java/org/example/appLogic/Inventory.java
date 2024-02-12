package org.example.appLogic;


import org.apache.poi.ss.usermodel.*;

import org.example.periodicity.inventoryTasks.BreakingDownAllInventoryModelsIntoBatches;
import org.example.periodicity.inventoryTasks.Insert_InventoryBatchIntoDB;
import org.example.periodicity.inventoryTasks.InventoryModelsConstructorTask;
import org.example.utils.Constants;
import org.example.models.InventoryModel;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.example.Main.executor;
import static org.example.periodicity.commonTasks.PathListnerTask.fileListing;
import static org.example.properties.Config.BATCH_FILES_PARSED;
import static org.example.properties.Config.INVENTORY_FOLDER_PATH;

public class Inventory {
    private static String cypherInventory = "sdfdsfdsfsd5ds4ff5s";
    private static List<String> prevInventoryParsed=new ArrayList<>();
    public static List<InventoryModel> inventoryBatch=new ArrayList<>(BATCH_FILES_PARSED);
    public static List<InventoryModel> inventoryOnlyOneFileParser(File inputFile) throws IOException {
        List<InventoryModel> inventoryOfOneFile = new ArrayList<>();
        try {
            int flag=Constants.LOW;
            if (inputFile != null) {
                    if (!inputFile.isDirectory() && (inputFile.getName().startsWith("Inv")||inputFile.getName().endsWith("xlxs")) && (!inputFile.getName().contains("- Copy")) && (!prevInventoryParsed.contains(inputFile.getName() + cypherInventory))) {
                        prevInventoryParsed.add(inputFile.getName().concat(cypherInventory));
                        Workbook workbook = WorkbookFactory.create(inputFile);
                        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                            Sheet sheet = workbook.getSheetAt(i);
                            for (Row row : sheet) {
                                if (row.getRowNum() == 0) {
                                } else {
                                    String itemName = null;
                                    double itemPrice = 0.0;
                                    int inventoryQuantity = 0;

                                    for (Cell cell : row) {
                                        switch (cell.getCellType()) {
                                            case STRING:
                                                String stringCellValue = cell.getStringCellValue();
                                                itemName = stringCellValue;
                                                break;
                                            case NUMERIC:
                                                if (cell.getColumnIndex() == Constants.COL1) {
                                                    double numericCellValue = cell.getNumericCellValue();
                                                    itemPrice = numericCellValue;
                                                } else if (cell.getColumnIndex() == Constants.COL2) {
                                                    int numericCellValue = (int) cell.getNumericCellValue();
                                                    inventoryQuantity = numericCellValue;
                                                }
                                        }
                                    }
                                    InventoryModel inventory = new InventoryModel(itemName, itemPrice, inventoryQuantity);
                                    inventoryOfOneFile.add(inventory);
                                }
                            }
                        }
                    }
            }
        } catch (IOException e) {
            throw new IOException();
        }
        return inventoryOfOneFile;
    }
    public static List<InventoryModel> inventoryParser(String path) throws IOException {
        List<InventoryModel> inventoryPassed = new ArrayList<>();
        try {
            int flag=Constants.LOW;
            Path filePath = Paths.get(path);
            File[] files = filePath.toFile().listFiles();
            if (files != null) {
                for (File inputFile : files) {
                    if (!inputFile.isDirectory() && inputFile.getName().startsWith("Inventory") && (!inputFile.getName().contains("- Copy")) && (!prevInventoryParsed.contains(inputFile.getName() + cypherInventory))) {
                        prevInventoryParsed.add(inputFile.getName().concat(cypherInventory));
                        Workbook workbook = WorkbookFactory.create(inputFile);
                        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                            Sheet sheet = workbook.getSheetAt(i);
                            for (Row row : sheet) {
                                if (row.getRowNum() == 0) {
                                } else {
                                    String itemName = null;
                                    double itemPrice = 0.0;
                                    int inventoryQuantity = 0;

                                    for (Cell cell : row) {
                                        switch (cell.getCellType()) {
                                            case STRING:
                                                String stringCellValue = cell.getStringCellValue();
                                                itemName = stringCellValue;
                                                break;
                                            case NUMERIC:
                                                if (cell.getColumnIndex() == Constants.COL1) {
                                                    double numericCellValue = cell.getNumericCellValue();
                                                    itemPrice = numericCellValue;
                                                } else if (cell.getColumnIndex() == Constants.COL2) {
                                                    int numericCellValue = (int) cell.getNumericCellValue();
                                                    inventoryQuantity = numericCellValue;
                                                }
                                                // we will continue if there are other types
                                        }
                                    }
                                    InventoryModel inventory = new InventoryModel(itemName, itemPrice, inventoryQuantity);
                                    inventoryPassed.add(inventory);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException();
        }
        return inventoryPassed;
    }
    public static void inventoryExecuteTasks() {
        CompletableFuture<List<File>> task1_Inv = CompletableFuture.supplyAsync(() -> {
            // Task 1: Get a list of all excel files in the given path
            List<File> list = Arrays.asList(fileListing(INVENTORY_FOLDER_PATH));
            return list;
        }, executor);

        CompletableFuture<List<InventoryModel>> task2_Inv = task1_Inv.thenApplyAsync(list ->
                list.parallelStream()
                        .flatMap(file -> CompletableFuture.supplyAsync(() -> {
                            try {
                                return InventoryModelsConstructorTask.inventoryModelsConstrutor(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }).join().stream())
                        .collect(Collectors.toList()), executor);

        CompletableFuture<List<List<InventoryModel>>> task3_Inv = task2_Inv.thenApplyAsync(BreakingDownAllInventoryModelsIntoBatches::breakingDownAllInvenotryModelsIntoBatches, executor);

        CompletableFuture<Void> task4_Inv = task3_Inv.thenAcceptAsync(batches -> {
            List<CompletableFuture<Void>> futures = batches.parallelStream()
                    .map(batch -> CompletableFuture.runAsync(() -> {
                        try {
                            Insert_InventoryBatchIntoDB.insertInventoryBatchIntoDataBase(batch);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }, executor))
                    .collect(Collectors.toList());
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }, executor);

        CompletableFuture<Void> finalTaskInv = task4_Inv.thenRunAsync(() -> {
            List<File> previousFiles = task1_Inv.join(); // Get the list of files from the previous task
            List<File> currentFiles = Arrays.asList(fileListing(INVENTORY_FOLDER_PATH)); // Get the current list of files
            if (!currentFiles.equals(previousFiles)) {
                executeInventoryTasksHelper();
                // Repeat all tasks if new files are found
            }
        }, executor);

        // Wait for the completion of all tasks
        CompletableFuture.allOf(task1_Inv, task2_Inv, task3_Inv, task4_Inv, finalTaskInv).join();
        // executor.shutdown();
        // Shutdown executor after all tasks are completed

    }
    public static void executeInventoryTasksHelper() {
        inventoryExecuteTasks();
        // Recursive call to executeTasks method
    }
}

