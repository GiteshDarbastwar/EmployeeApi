package com.GtasteriX.UserAPI.Service;

import com.GtasteriX.UserAPI.DTO.Employee;
import com.GtasteriX.UserAPI.Exception.DataNotFoundException;
import com.GtasteriX.UserAPI.Exception.IdNotFoundException;
import com.GtasteriX.UserAPI.Exception.NameNotFoundException;
import com.GtasteriX.UserAPI.Exception.ValidationException;
import com.GtasteriX.UserAPI.Repository.EmpRepo;
import com.GtasteriX.UserAPI.Util.ECodeUtil;
import org.hibernate.annotations.processing.Find;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmpService {
    @Autowired
    private EmpRepo empRepo;

    public Employee saveEmployee(Employee employee) {
        validateEmployee(employee); // Call the validation method before saving

        int eCode = ECodeUtil.generateECode();
        employee.setECode(eCode);

        return empRepo.save(employee);
    }

    private void validateEmployee(Employee employee) {
        String mobileRegex = "^[6-9]\\d{9}$";
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        if (employee.getMobileNo() == null || !employee.getMobileNo().matches(mobileRegex)) {
            throw new ValidationException("Invalid mobile number");
        }

        if (employee.getEmail() == null || !employee.getEmail().matches(emailRegex)) {
            throw new ValidationException("Invalid email ID");
        }
    }

    public List<Employee> getAllEmployee() {
        List<Employee> allEmployee = empRepo.findAll();
        if (allEmployee.isEmpty()) {
            throw new DataNotFoundException("Data Not Found");
        }
        return allEmployee;
    }

    public Employee updateEmployeeById(Integer id, Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = empRepo.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setEName(updatedEmployee.getEName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setRole(updatedEmployee.getRole());
            existingEmployee.setCompany(updatedEmployee.getCompany());
            existingEmployee.setMobileNo(updatedEmployee.getMobileNo());
            existingEmployee.setLocation(updatedEmployee.getLocation());
            existingEmployee.setExperience(updatedEmployee.getExperience());
            existingEmployee.setSalary(updatedEmployee.getSalary());
            return empRepo.save(existingEmployee);
        } else {
            throw new IdNotFoundException("Employee not found with ID: " + id);
        }
    }


    public Employee deleteEmpByID(Integer id){
       Optional<Employee> deleteEmployee=empRepo.findById(id);
       if (deleteEmployee.isPresent()){
           Employee deletedEmployee=empRepo.getById(id);
           empRepo.deleteById(id);
           return deletedEmployee;

       }
       else{
           throw new IdNotFoundException("Employee not found with ID: " + id);
       }

    }

    public Employee findEmpByID(Integer id){
        Optional<Employee> Emp=empRepo.findById(id);
        if(Emp.isPresent()){
            Employee employee=Emp.get();
            return  employee;
        }
        else {
            throw new IdNotFoundException("Employee not found with ID:" + id);
        }

    }

    public List<Employee> empByName(String name){
        List<Employee>emp=empRepo.findByEName(name);
        if(emp.isEmpty()){

            throw new NameNotFoundException("Employee not found with Name:" + name);
        }
        return emp;
    }


//    public Employee updateProductByFields(int id, Map<String, Object> fields) {
//        Optional<Employee> existingProduct = empRepo.findById(id);
//
//        if (existingProduct.isPresent()) {
//            fields.forEach((key, value) -> {
//                Field field = ReflectionUtils.findField(Employee.class,key);
//                field.setAccessible(true);
//                ReflectionUtils.setField(field, existingProduct.get(), value);
//            });
//            return empRepo.save(existingProduct.get());
//        }
//        return null;
//    }


//    public Employee patchEmployeeById(Integer id, Map<String, Object> updates) {
//        Optional<Employee> optionalEmployee = empRepo.findById(id);
//        if (optionalEmployee.isPresent()) {
//
//
//            Employee existingEmployee = optionalEmployee.get();
//            updates.forEach((key, value) -> {
//                try {
//                    Field field = Employee.class.getDeclaredField(key);
//                    field.setAccessible(true);
//                    field.set(existingEmployee, value);
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    // handle exception
//                    throw new RuntimeException("Failed to update field: " + key, e);
//                }
//            });
//            return empRepo.save(existingEmployee);
//        } else {
//            throw new IdNotFoundException("Employee not found with ID: " + id);
//        }
//    }
public Employee patchEmployeeById(Integer id, Map<String, Object> updates) {
    Optional<Employee> optionalEmployee = empRepo.findById(id);
    if (optionalEmployee.isPresent()) {
        Employee existingEmployee = optionalEmployee.get();
        updates.forEach((key, value) -> {
            try {
                Field field = Employee.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(existingEmployee, value);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("No such field: " + key, e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot access field: " + key, e);
            }
        });
        return empRepo.save(existingEmployee);
    } else {
        throw new IdNotFoundException("Employee not found with ID: " + id);
    }
}

}









