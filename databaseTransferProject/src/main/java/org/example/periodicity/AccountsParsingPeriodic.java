package org.example;
import org.example.dataBaseCommunication.AccountsCommDb;
public class AccountsParsingPeriodic implements Runnable{

        private String taskName;

        public AccountsParsingPeriodic(String taskName) {
            this.taskName = taskName;
        }
        @Override
        public void run() {
           // List<AccountModel> accList;
            //                accList = accountsParser("D:\\task1 24-1-2024\\Accounts Folder");
//                accList.forEach(System.out::println);
            AccountsCommDb.accountsConnector();
            System.out.println("accounts' Thread is running...");
            try {
                Thread.sleep(10000);  // Pause for 10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
              }
        }

