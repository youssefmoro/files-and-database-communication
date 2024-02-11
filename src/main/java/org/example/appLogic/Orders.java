package org.example.appLogic;
import org.example.models.AccountModel;
import org.example.models.OrderModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.example.properties.Config.BATCH_FILES_PARSED;
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
}

