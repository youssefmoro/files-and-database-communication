package dataTransferCommProject.example.RestApis.RESTcontroller;

import dataTransferCommProject.example.RestApis.Models.AccountModel;
import dataTransferCommProject.example.RestApis.Models.OrderModel;
import dataTransferCommProject.example.RestApis.Repo.OrderRepo;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrdersApiController {
    @Autowired
    private OrderRepo orderRepo;
    @GetMapping(value = "/AllOrders")
    public List<OrderModel> getAllOrderRepo() {
        return orderRepo.findAll();
    }
    @PostMapping(value = "/saveOrder")
    public String saveOrder(@RequestBody OrderModel order)
    {
        orderRepo.save(order);
        return "saved";
    }
    @PutMapping(value="/update/{orderId}")
    public String updateOrder(@PathVariable long orderId,@RequestBody OrderModel order)
    {
        OrderModel updatedOrder=orderRepo.findById(orderId).get();
       updatedOrder.setOrderQuantity(order.getOrderQuantity());
       updatedOrder.setAccountId(order.getAccountId());
       updatedOrder.setItemId(order.getItemId());
       //updatedOrder.setOrderNumber(order.getOrderNumber());
        orderRepo.save(updatedOrder);
        return "updated";
    }
    @DeleteMapping(value = "/delete/{orderId}")
    public String deleteOrder(@PathVariable long orderId)
    {
        OrderModel deleteOrder=orderRepo.findById(orderId).get();
        orderRepo.delete(deleteOrder);
        return "deleted order with id:"+orderId;
    }
}
