package org.example.periodicity.inventoryTasks;

import org.example.models.InventoryModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.example.appLogic.Inventory.inventoryOnlyOneFileParser;

public class InventoryModelsConstructorTask {

        public static List<InventoryModel> inventoryModelsConstrutor(File file) throws IOException {
            List<InventoryModel> invList = inventoryOnlyOneFileParser(file);
            return invList;
        }
}
