package dataTransferCommProject.example.RestApis.Repo;

import dataTransferCommProject.example.RestApis.Models.InventoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<InventoryModel,Long> {
}
