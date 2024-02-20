package dataTransferCommProject.example.RestApis.Repo;

import dataTransferCommProject.example.RestApis.Models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<AccountModel,Long> {
}
