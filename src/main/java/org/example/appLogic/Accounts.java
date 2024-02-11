package org.example.appLogic;

import org.example.models.AccountModel;

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


public class Accounts {
    //static List<String> processedAccounts = new ArrayList<>();
    //    static Map<File, Boolean> uniqueFilesMap = new HashMap<>();
//    static List<File> uniqueFiles;
    public  static List<String> prevAccountsParsed = new ArrayList<>();
    public static String cypherAccounts = "123asa15as6das45das56d4";
    public static List<AccountModel> accountsBatch=new ArrayList<>(BATCH_FILES_PARSED);

    public static List<AccountModel> accountsParser(String path) throws IOException {
        List<AccountModel> accountsPassed = new ArrayList<>();
        try {
            Path filePath = Paths.get(path);
            File[] files = filePath.toFile().listFiles();
            if (files != null) {
                for (File inputFile : files) {
                    accountsPassed.addAll(accountsOnlyOneFileParser(inputFile));
                            }
                        }
        } catch (Exception e) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return accountsPassed;
    }
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

}


//public class ReadCSVExample1 throws FileNotFoundException
//{
//
////parsing a CSV file into Scanner class constructor
//        Scanner sc = new Scanner(new File("F:\\CSVDemo.csv"));
//        sc.useDelimiter(",");   //sets the delimiter pattern
//        while (sc.hasNext())  //returns a boolean value
//        {
//            System.out.print(sc.next());  //find and returns the next complete token from this scanner
//        }
//        sc.close();  //closes the scanner
//
//}
//public void CSVParser() {
//        String csvFolder = "D:\\task1 24-1-2024\\CSV and Excel folder";
//
//        try (CSVReader reader = new CSVReader(new FileReader(csvFolder))) {
//            String[] nextLine;
//            while ((nextLine = reader.readNext()) != null) {
//                // Process the data in each line
//                for (String data : nextLine) {
//                    System.out.print(data + " ");
//                }
//                System.out.println();
//            }
//        } catch (IOException | CsvException e) {
//            e.printStackTrace();
//        } catch (CsvValidationException e) {
//            throw new RuntimeException(e);
//        }
//
//
//}

