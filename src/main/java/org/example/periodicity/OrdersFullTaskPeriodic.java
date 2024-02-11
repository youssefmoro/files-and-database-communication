//package org.example.periodicity;
//import org.example.dataBaseCommunication.OrdersCommDb;
//import org.example.models.OrderModel;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.sql.SQLException;
//import java.util.List;
//
//import static org.example.appLogic.GeneralServices.fileListing;
//import static org.example.appLogic.Orders.ordersOnlyOneFileParser;
//import static org.example.properties.Config.ACCOUNTS_FOLDER_PATH;
//import static org.example.properties.Config.ORDERS_FOLDER_PATH;
//
//
//public class OrdersFullTaskPeriodic implements Runnable {
//    private String taskName;
//
//    public OrdersFullTaskPeriodic(String taskName) {
//        this.taskName = taskName;
//    }
//    @Override
//    public void run() {
//        long startTimeOrders=System.nanoTime();
//        List<OrderModel> orderList;
//        //orderList=orderParser("D:\\task1 24-1-2024\\Orders Folder");
//        // orderList.forEach(System.out::println);
//        File[] ordersFiles= (File[]) fileListing(ORDERS_FOLDER_PATH);
//        for (File file : ordersFiles) {
//            try {
//                orderList = ordersOnlyOneFileParser(file);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            long parsingTimeOrders=System.nanoTime();
//            long parsingElapsedTime=parsingTimeOrders-startTimeOrders;
//            System.out.println("Orders' Parsing time is:"+parsingElapsedTime);
//            try {
//                OrdersCommDb.ordersCommunicator(orderList);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        System.out.println("Orders' Thread is running...");
//        long endTimeOrders=System.nanoTime();
//        long elapsedTime=endTimeOrders-startTimeOrders;
//        System.out.println("Orders fullcycle for a time is "+elapsedTime);
//            }
//    }
