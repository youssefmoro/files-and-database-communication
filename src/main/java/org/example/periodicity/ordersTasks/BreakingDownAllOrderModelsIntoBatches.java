package org.example.periodicity.ordersTasks;

import org.example.models.OrderModel;
import java.util.ArrayList;
import java.util.List;

import static org.example.properties.Config.BATCH_FILES_PARSED;

public class BreakingDownAllOrderModelsIntoBatches {
    public static List<List<OrderModel>> breakingDownAllOrderModelsIntoBatches(List<OrderModel> allOrders)
    {
        int counter=0;
        List<List<OrderModel>> listOfLists=new ArrayList<>();
        int numOfListsRequired=allOrders.size()/BATCH_FILES_PARSED;
        float diff=(float)((float)allOrders.size()/(float)BATCH_FILES_PARSED)-numOfListsRequired;
        numOfListsRequired=(diff==0)?(numOfListsRequired):(numOfListsRequired+1);
        for(int i=0;i<numOfListsRequired;i++)
        {
            List<OrderModel> innerList = new ArrayList<>();
            for (int j=0;j<BATCH_FILES_PARSED;j++)
            {
                if (counter < allOrders.size()) {
                    innerList.add(allOrders.get(counter));
                    counter++;
                } else {
                    break; // Break the loop if all accounts have been processed
                }
            }
            listOfLists.add(innerList);
        }
        return listOfLists;
    }
}
