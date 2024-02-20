package dataTransferCommProject.example.RestApis.RESTcontroller;

import dataTransferCommProject.example.RestApis.Models.AccountModel;
import dataTransferCommProject.example.RestApis.Models.InventoryModel;
import dataTransferCommProject.example.RestApis.Repo.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InventoryApiController {
    @Autowired
    private InventoryRepo inventoryRepo;

    @GetMapping(value = "/AllInventory")
    public List<InventoryModel> getAllInventoryRepo() {
        return inventoryRepo.findAll();
    }
    @PostMapping(value = "/saveInventory")
    public String saveInventory(@RequestBody InventoryModel inventory)
    {
        inventoryRepo.save(inventory);
        return "saved";
    }
    @PutMapping(value="/update{inventoryId}")
    public String updateInventory(@PathVariable long id,@RequestBody InventoryModel inventoryRecord)
    {
        InventoryModel updatedInventory=inventoryRepo.findById(id).get();
        updatedInventory.setInventoryQuantity(inventoryRecord.getInventoryQuantity());
        updatedInventory.setItemName(inventoryRecord.getItemName());
        updatedInventory.setItemPrice(inventoryRecord.getItemPrice());
        //updatedInventory.setOrderId(inventoryRecord.getOrderId());
        inventoryRepo.save(updatedInventory);
        return "updated";
    }
    @DeleteMapping(value = "/delete/{inventoryId}")
    public String deleteInventoryRecord(@PathVariable long invId)
    {
        InventoryModel deleteInventory=inventoryRepo.findById(invId).get();
        inventoryRepo.delete(deleteInventory);
        return "deleted inventoryrecord number: "+invId;
    }
}
