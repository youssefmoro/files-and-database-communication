//package org.example.appLogic;
//
//import org.example.models.AccountModel;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//import static org.example.appLogic.Accounts.accountsOnlyOneFileParser;
//import static org.example.properties.Config.ACCOUNTS_FOLDER_PATH;
//
//public class GeneralServices {
//    public static Object fileListing (String path)
//    {
//        Path realpath = Paths.get(path);
//        File[] files = realpath.toFile().listFiles();
//        return files;
//    }
//
//    public static List<AccountModel> AccountslistFeeder(File[] files, List<AccountModel> accountsFiles) {
//        for (AccountModel file : accountsFiles) {
//            try {
//                accList = accountsOnlyOneFileParser(file);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return accountsFiles;
//    }
//}
