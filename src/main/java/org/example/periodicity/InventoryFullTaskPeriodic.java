package org.example;

import org.example.dataBaseCommunication.InventoryCommDb;

import java.sql.SQLException;

public class InventoryFullTaskPeriodic implements Runnable {
    private String taskName;

    public InventoryFullTaskPeriodic(String taskName) {
        this.taskName = taskName;
    }
    @Override
    public void run() {
        //List<InventoryModel> invList;
        //invList=inventoryParser("D:\\task1 24-1-2024\\Inventory Folder");
        //invList.forEach(System.out::println);
        try {
            InventoryCommDb.inventoryCommunicator();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Inventory's Thread is running...");

    }
}
