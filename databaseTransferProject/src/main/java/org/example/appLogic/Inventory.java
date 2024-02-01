package org.example.appLogic;


import org.apache.poi.ss.usermodel.*;

import org.example.utils.Constants;
import org.example.models.InventoryModel;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    static String cypherInventory = "sdfdsfdsfsd5ds4ff5s";
    static List<String> prevInventoryParsed=new ArrayList<>();
    public static List<InventoryModel> inventoryParser(String path) throws IOException, FileNotFoundException {
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
                                if (flag == Constants.LOW) {
                                    flag = Constants.HIGH;
                                } else if (flag == Constants.HIGH) {
                                    String itemName = null;
                                    double itemPrice = 0.0;
                                    int inventoryQuantity = 0;
                                    // if sheet is not trusted(organised) i will remove the if cond and loop over each row
//                                if (StringUtils.isEmpty(row.getCell(0).getStringCellValue())) {
//                                    // Reached empty row after headers, stop loop
//                                    break;
//                                }
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
}

