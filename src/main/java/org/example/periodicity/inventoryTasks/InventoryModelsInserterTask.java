package org.example.periodicity.inventoryTasks;

import org.example.dataBaseCommunication.InventoryCommDb;
import org.example.models.InventoryModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InventoryModelsInserterTask {
        public static void inventoryModelInserter(List<InventoryModel> invList) throws SQLException, IOException {
           InventoryCommDb.inventoryCommunicator(invList);
        }
}
