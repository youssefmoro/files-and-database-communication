package org.example.periodicity.accountsTasks;

import org.example.models.AccountModel;

import java.util.ArrayList;
import java.util.List;

import static org.example.properties.Config.BATCH_FILES_PARSED;

public class BreakingDownAllAccountModelsIntoBatches {
    public static List<List<AccountModel>> breakingDownAllAccountModelsIntoBatches(List<AccountModel> allAccounts)
    {
        int counter=0;
        List<List<AccountModel>> listOfLists=new ArrayList<>();
        int numOfListsRequired=allAccounts.size()/BATCH_FILES_PARSED;
        float diff=(float)((float)allAccounts.size()/(float)BATCH_FILES_PARSED)-numOfListsRequired;
        numOfListsRequired=(diff==0)?(numOfListsRequired):(numOfListsRequired+1);
        for(int i=0;i<numOfListsRequired;i++)
        {
            List<AccountModel> innerList = new ArrayList<>();
            for (int j=0;j<BATCH_FILES_PARSED;j++)
            {
                if (counter < allAccounts.size()) {
                    innerList.add(allAccounts.get(counter));
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
