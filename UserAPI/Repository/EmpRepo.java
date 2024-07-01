package com.GtasteriX.UserAPI.Repository;

import com.GtasteriX.UserAPI.DTO.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepo extends JpaRepository<Employee,Integer> {

    @Query("SELECT e FROM Employee e WHERE e.EName = ?1")
    List<Employee> findByEName(String eName);


}


