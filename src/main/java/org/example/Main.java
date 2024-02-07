package org.example;

import org.example.service.*;
import org.example.periodicity.*;

import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args)throws SQLException {
        //delete your database and start over
        AccountsServices.deleteAccounts();
        InventoryServices.deleteInventory();
        OrdersServices.deleteOrders();
       // tasks scheduled to parse and insert the data taking turns
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        AccountsFullTaskPeriodic task1 = new AccountsFullTaskPeriodic("Accounts");
        OrdersFullTaskPeriodic task2 = new OrdersFullTaskPeriodic("Orders");
        org.example.InventoryFullTaskPeriodic task3 = new org.example.InventoryFullTaskPeriodic("Inventory");
        // Schedule orders' task to run every 1 second
        executorService.scheduleWithFixedDelay(task2, 0, 1, TimeUnit.SECONDS);
        // Schedule accounts' task to run every 1 second
        executorService.scheduleWithFixedDelay(task1, 0, 1, TimeUnit.SECONDS);

         //Schedule Inventory's task to run every 1 second
        executorService.scheduleWithFixedDelay(task3, 0, 1, TimeUnit.SECONDS);
        //(number of threads that can work simultaneously without causing context switch)
        System.out.println("available CPUs are: "+Runtime.getRuntime().availableProcessors());

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