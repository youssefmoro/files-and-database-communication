package org.example;

import org.example.dataBaseCommunication.OrdersCommDb;


public class OrdersParsingPeriodic implements Runnable {
    private String taskName;

    public OrdersParsingPeriodic(String taskName) {
        this.taskName = taskName;
    }
    @Override
    public void run() {
        //List<OrderModel> orderList;
        //orderList=orderParser("D:\\task1 24-1-2024\\Orders Folder");
        // orderList.forEach(System.out::println);
        OrdersCommDb.ordersConnector();
        System.out.println("Orders' Thread is running...");
        try {
            Thread.sleep(10000);  // Pause for 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            }
    }
