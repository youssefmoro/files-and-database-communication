package org.example;

import org.example.service.*;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) throws IOException {
        //AccountsServices.deleteAccounts();
        //InventoryServices.deleteInventory();
       // OrdersServices.deleteOrders();
        //DatabaseServices.deleteAccounts();
        //AccountsCommDb.accountsConnector();
//        //org.example.dataBaseCommunication.Orders.ordersConnector();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        org.example.AccountsParsingPeriodic task1 = new org.example.AccountsParsingPeriodic("Accounts");
        org.example.OrdersParsingPeriodic task2 = new org.example.OrdersParsingPeriodic("Orders");
        org.example.InventoryParsingPeriodic task3 = new org.example.InventoryParsingPeriodic("Inventory");
        // Schedule accounts' task to run every 1 second
        executorService.scheduleAtFixedRate(task1, 0, 1, TimeUnit.SECONDS);

        // Schedule orders' task to run every 1 second
        executorService.scheduleAtFixedRate(task2, 0, 1, TimeUnit.SECONDS);
         //Schedule Inventory's task to run every 1 second
        executorService.scheduleAtFixedRate(task3, 0, 1, TimeUnit.SECONDS);

        // Sleep for 5 seconds to allow the tasks to run multiple times
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //List<InventoryModel> invList=new ArrayList<>();
        //invList.forEach(System.out::println);


        // Shutdown the executor service
        //executorService.shutdown();

//        List<AccountModel> accList=new ArrayList<>();
//        List<OrderModel> orderList;
//        List<InventoryModel> invList=new ArrayList<>();
//        orderList=orderParser("D:\\task1 24-1-2024\\CSV and Excel folder");
//        accList = accountsParser("D:\\task1 24-1-2024\\CSV and Excel folder");
//        invList=inventoryParser("D:\\task1 24-1-2024\\CSV and Excel folder");
//        orderList.forEach(System.out::println);
//        accList.forEach(System.out::println);
//        invList.forEach(System.out::println);
//        System.out.println();

    }

}