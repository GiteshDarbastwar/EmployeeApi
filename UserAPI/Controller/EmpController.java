package com.GtasteriX.UserAPI.Controller;

import com.GtasteriX.UserAPI.DTO.Employee;

import com.GtasteriX.UserAPI.Exception.DataNotFoundException;
import com.GtasteriX.UserAPI.Exception.IdNotFoundException;
import com.GtasteriX.UserAPI.Exception.NameNotFoundException;
import com.GtasteriX.UserAPI.Exception.ValidationException;
import com.GtasteriX.UserAPI.Service.EmpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmpController {
    @Autowired
    private EmpService empService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveEmployee(@RequestBody Employee employee) {
        try {
            Employee savedEmployee = empService.saveEmployee(employee);
            return
                    ResponseEntity.status(HttpStatus.OK).body(savedEmployee);
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }
@GetMapping("/EmployeeList")
    public ResponseEntity<Object> findAll() {
        try {
            List<Employee> empList = empService.getAllEmployee();
            return ResponseEntity.status(HttpStatus.OK).body(empList);
        } catch (DataNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateEmployeeById(@PathVariable Integer id, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = empService.updateEmployeeById(id, employee);
            return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
        } catch (IdNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
        public ResponseEntity<Object> deletEmpolyeeById(@PathVariable Integer id){
            try{

                Employee  removedEmployee=empService.deleteEmpByID(id);
                return  ResponseEntity.status(HttpStatus.OK).body(removedEmployee);

        }
            catch (IdNotFoundException exception){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
            }
    }

    @GetMapping("/EmpByID/{id}")
    public ResponseEntity<Object> getByID(@PathVariable Integer id){
        try{
            Employee employee=empService.findEmpByID(id);
            return ResponseEntity.status(HttpStatus.OK).body(employee);
        }
        catch (IdNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
    @GetMapping("/EmpByName/{name}")
    public ResponseEntity<Object> getByName(@PathVariable String name){
        try{
            List<Employee> Employees=empService.empByName(name);
            return ResponseEntity.status(HttpStatus.OK).body(Employees);

        }
        catch (NameNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PatchMapping("/updateEmpByPatch/{id}")
    public ResponseEntity<Object> patchEmployee(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        try {
            Employee updatedEmployee = empService.patchEmployeeById(id, updates);
            return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
        } catch (IdNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }



    }

