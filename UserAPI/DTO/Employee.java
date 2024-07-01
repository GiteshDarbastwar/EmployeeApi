package com.GtasteriX.UserAPI.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer EID;
    private Integer ECode;
    private  String EName;
    private String Email;
    private String Role;
    private String Company;
    private String MobileNo;
    private String Location;
    private String Experience;
    private Long Salary;

}
