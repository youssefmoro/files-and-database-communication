package org.example.periodicity.ordersTasks;

import org.example.models.AccountModel;
import org.example.models.OrderModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.example.appLogic.Accounts.accountsOnlyOneFileParser;
import static org.example.appLogic.Orders.ordersOnlyOneFileParser;

public class OrdersModelsConstructorTask {
        public static List<OrderModel> ordersModelsConstrutor(File file) throws IOException {
            List<OrderModel> orderList=ordersOnlyOneFileParser(file);
            return orderList;
        }
}
