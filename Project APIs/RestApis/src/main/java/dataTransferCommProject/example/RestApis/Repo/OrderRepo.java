package dataTransferCommProject.example.RestApis.Repo;

import dataTransferCommProject.example.RestApis.Models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<OrderModel,Long> {

}
