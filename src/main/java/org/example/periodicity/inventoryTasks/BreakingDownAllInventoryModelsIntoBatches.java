package org.example.periodicity.inventoryTasks;

import org.example.models.AccountModel;
import org.example.models.InventoryModel;

import java.util.ArrayList;
import java.util.List;

import static org.example.properties.Config.BATCH_FILES_PARSED;

public class BreakingDownAllInventoryModelsIntoBatches {
        public static List<List<InventoryModel>> breakingDownAllInvenotryModelsIntoBatches(List<InventoryModel> allInventory)
        {
            int counter=0;
            List<List<InventoryModel>> listOfLists=new ArrayList<>();
            int numOfListsRequired=allInventory.size()/BATCH_FILES_PARSED;
            float diff=(float)((float)allInventory.size()/(float)BATCH_FILES_PARSED)-numOfListsRequired;
            numOfListsRequired=(diff==0)?(numOfListsRequired):(numOfListsRequired+1);
            for(int i=0;i<numOfListsRequired;i++)
            {
                List<InventoryModel> innerList = new ArrayList<>();
                for (int j=0;j<BATCH_FILES_PARSED;j++)
                {
                    if (counter < allInventory.size()) {
                        innerList.add(allInventory.get(counter));
                        counter++;
                    } else {
                        break; // Break the loop if all Inventorys have been processed
                    }
                }
                listOfLists.add(innerList);
            }
            return listOfLists;
        }
}
