package org.example.appLogic;
import org.example.models.OrderModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static org.example.validation.Validation.*;

public class Orders {
    static List<String> prevOrdersParsed = new ArrayList<>();
    static String cypherOrders = "ajhlkcxvnxjkds55456";

    public static List<OrderModel> orderParser(String path) throws IOException {
        List<OrderModel> orderPassed=new ArrayList<>();
        try {
            Path filePath = Paths.get(path);
            File[] files = filePath.toFile().listFiles();
            if (files != null) {
                for (File inputFile : files) {
                    if (!inputFile.isDirectory() && (inputFile.getName().startsWith("ord") || (inputFile.getName().endsWith(".csv"))) && (!inputFile.getName().contains("- Copy")) && (!prevOrdersParsed.contains(inputFile.getName() + cypherOrders))) {
                        //System.out.println(inputFile.getName());
                        FileReader fileReader = new FileReader(inputFile);
                        prevOrdersParsed.add(inputFile.getName().concat(cypherOrders));
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String line;
                        int iterator = 0;
                        while ((line = bufferedReader.readLine()) != null) {
                            String[] tokenWords = line.split(",");
                            String orderNumber = tokenWords[0];
                            String accountId = tokenWords[1];
                            String tempToken = tokenWords[2];
                            String[] tempFinal = tempToken.split(";");
                            String itemId = tempFinal[0];
                            String itemQuantity = tempFinal[1];
                            if (allNumericValidatorInString(orderNumber) && allNumericValidatorInString(accountId) && allNumericValidatorInString(itemId) && allNumericValidatorInString(itemQuantity)) {
                                OrderModel order = new OrderModel(Long.parseLong(orderNumber), Long.parseLong(accountId), Long.parseLong(itemId), Long.parseLong(itemQuantity));
                                orderPassed.add(order);
                            }
                        }
                    }
                }
            }
        }catch (IOException e)
        {
            throw new IOException();
        }
        return orderPassed;
    }
}

