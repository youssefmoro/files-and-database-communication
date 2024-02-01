package org.example;

import org.example.dataBaseCommunication.InventoryCommDb;

public class InventoryParsingPeriodic implements Runnable {
    private String taskName;

    public InventoryParsingPeriodic(String taskName) {
        this.taskName = taskName;
    }
    @Override
    public void run() {
        //List<InventoryModel> invList;
        //invList=inventoryParser("D:\\task1 24-1-2024\\Inventory Folder");
        //invList.forEach(System.out::println);
        InventoryCommDb.inventoryConnector();
        System.out.println("Inventory's Thread is running...");
        try {
            Thread.sleep(10000);  // Pause for 10 seconds
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}
