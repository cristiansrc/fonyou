/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fonyou.employee.service;

import com.fonyou.employee.constant.EmployeeConstant;
import com.fonyou.employee.exception.EmployeeNotFoundException;
import com.fonyou.employee.exception.InvalidaDataException;
import com.fonyou.employee.model.Employee;
import com.fonyou.employee.model.RequestPay;
import com.fonyou.employee.model.Pay;
import com.fonyou.employee.repository.EmployeeRepository;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Cristhiam Reina
 */

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public void create(Employee employee) throws InvalidaDataException{
        this.validDataEmployee(false, employee);
        employeeRepository.insert(employee);
    }
    
    public void update(Employee employee) throws InvalidaDataException, EmployeeNotFoundException{
        this.validDataEmployee(true, employee);
        
        if(employeeRepository.selectOne(employee.getId()).getId() == null){
            throw new EmployeeNotFoundException();
        } else {
            employeeRepository.update(employee);
        }
        
    }
    
    public Employee getOne(Integer id) throws EmployeeNotFoundException{
        
        Employee employee = employeeRepository.selectOne(id);
        
        if(employee.getId() == null){
            throw new EmployeeNotFoundException();
        } 
        
        return employee;
    }
    
    public void delete(Integer id) throws EmployeeNotFoundException{
        this.getOne(id);
        employeeRepository.delete(id);
    }
    
    public List<Employee> get(){
        return employeeRepository.select();
    }
    
    public Pay calculationPay(RequestPay requestPay) throws InvalidaDataException {
        
        this.validDataEmploye(requestPay);
        
        Employee employee = employeeRepository.selectOne(requestPay.getId());
        
        Calendar admission = Calendar.getInstance();
        admission.setTime(employee.getDateAdmission());
        Integer yearAdmission = admission.get(Calendar.YEAR);
        Integer monthAdmission = admission.get(Calendar.MONTH);
        Integer daysAdmission = admission.get(Calendar.DAY_OF_MONTH);
        daysAdmission = (daysAdmission == 31) ? 30 : daysAdmission;
        
        Integer yearFinish = 1000;
        Integer monthFinish = 30;
        Integer daysFinish = 40;
        

        Double dayValue = employee.getBaseSalary() / EmployeeConstant.MONTH_DAYS;
        
        if(employee.getDateFinish() != null){
            Calendar finish = Calendar.getInstance();
            finish.setTime(employee.getDateFinish());
            yearFinish = finish.get(Calendar.YEAR);
            monthFinish = finish.get(Calendar.MONTH);
            daysFinish = finish.get(Calendar.DAY_OF_MONTH);
            daysFinish = (daysFinish == 31) ? 30 : daysFinish;
        }
        
        if(
            yearAdmission > requestPay.getYear() ||
            (
                requestPay.getYear() == yearAdmission &&
                monthAdmission > requestPay.getMonth())){
            
            throw new InvalidaDataException(EmployeeConstant.CONTRACT_NOT_STARTED);
        }
        
        if(
            yearFinish < requestPay.getYear() ||
            (
                requestPay.getYear() == yearFinish &&
                monthFinish < requestPay.getMonth())){
            
            throw new InvalidaDataException(EmployeeConstant.CONTRACT_IS_OVER);
        }
        
        Integer payDays = this.calculatePayDays(
                yearAdmission, monthAdmission, yearFinish, monthFinish, 
                requestPay, daysAdmission, daysFinish);
        
        Pay responsePay = new Pay();
        responsePay.setBaseSalary(dayValue * payDays);
        
        return responsePay;
    }
    
    private Integer calculatePayDays(
            Integer yearAdmission, Integer monthAdmission, Integer yearFinish,
            Integer monthFinish, RequestPay requestPay, Integer daysAdmission, 
            Integer daysFinish){
        
        
        Integer payDays = EmployeeConstant.MONTH_DAYS;
        
        if(
            (yearFinish + 0) == requestPay.getYear() &&
            (monthFinish + 1) == requestPay.getMonth()){
            
            payDays = daysFinish;
        }
        
        if(
            (yearAdmission + 0) == requestPay.getYear() &&
            (monthAdmission + 1) == requestPay.getMonth()){
            
            payDays = payDays - (daysAdmission - 1);
        }
        
        return payDays; 
    }
    
    private void validDataEmploye(RequestPay requestPay) throws InvalidaDataException{
        StringBuilder stringBuilder = new StringBuilder();
        String command = EmployeeConstant.EMPTY;
        
        if(
            requestPay.getId() == null &&
            requestPay.getId() < 1){
            
            stringBuilder.append(EmployeeConstant.ID);
            stringBuilder.append(EmployeeConstant.SPACE);
            stringBuilder.append(EmployeeConstant.NOT_FOUND);
            command = EmployeeConstant.COMMAN;
        }
        
        if(
            requestPay.getMonth() == null || 
            requestPay.getMonth() < 1 ||
            requestPay.getMonth() > 12){
            
            stringBuilder.append(EmployeeConstant.MONTH);
            stringBuilder.append(EmployeeConstant.SPACE);
            stringBuilder.append(EmployeeConstant.NOT_FOUND);
            command = EmployeeConstant.COMMAN;
        }
        
        if(
            requestPay.getYear() == null || 
            requestPay.getYear() < 1920){
            
            stringBuilder.append(EmployeeConstant.YEAR);
            stringBuilder.append(EmployeeConstant.SPACE);
            stringBuilder.append(EmployeeConstant.NOT_FOUND);
            command = EmployeeConstant.COMMAN;
        }
            
        if(!command.equalsIgnoreCase(EmployeeConstant.EMPTY)){
            throw new InvalidaDataException(stringBuilder.toString());
        }    
            
    }
    
    private void validDataEmployee(boolean update, Employee employee) throws InvalidaDataException{
        
        StringBuilder stringBuilder = new StringBuilder();
        String command = EmployeeConstant.EMPTY;
        
        stringBuilder.append(EmployeeConstant.DATA_INVALID);
        
        if(update && (employee.getId() == null || employee.getId() < 1)){
            stringBuilder.append(EmployeeConstant.ID);
            stringBuilder.append(EmployeeConstant.SPACE);
            stringBuilder.append(EmployeeConstant.NOT_FOUND);
            command = EmployeeConstant.COMMAN;
        }
        
        if(
            employee.getName() == null || 
            employee.getName().trim().equalsIgnoreCase(EmployeeConstant.EMPTY)){
            
            stringBuilder.append(command);
            stringBuilder.append(EmployeeConstant.NAME);
            stringBuilder.append(EmployeeConstant.SPACE);
            stringBuilder.append(EmployeeConstant.NOT_FOUND);   
            command = EmployeeConstant.COMMAN;
        }
        
        if(
            employee.getLastName() == null || 
            employee.getLastName().trim().equalsIgnoreCase(EmployeeConstant.EMPTY)){
            
            stringBuilder.append(command);
            stringBuilder.append(EmployeeConstant.LAST_NAME);
            stringBuilder.append(EmployeeConstant.SPACE);
            stringBuilder.append(EmployeeConstant.NOT_FOUND);   
            command = EmployeeConstant.COMMAN;
        }
        
        if(employee.getDateAdmission() == null){
            
            stringBuilder.append(command);
            stringBuilder.append(EmployeeConstant.DATE_ADMISSION);
            stringBuilder.append(EmployeeConstant.SPACE);
            stringBuilder.append(EmployeeConstant.NOT_FOUND);   
            command = EmployeeConstant.COMMAN;
        }
        
        if(employee.getBaseSalary() == null){
            
            stringBuilder.append(command);
            stringBuilder.append(EmployeeConstant.BASE_SALARY);
            stringBuilder.append(EmployeeConstant.SPACE);
            stringBuilder.append(EmployeeConstant.NOT_FOUND);   
            command = EmployeeConstant.COMMAN;
        }
        
        
        if(!command.equalsIgnoreCase(EmployeeConstant.EMPTY)){
            throw new InvalidaDataException(stringBuilder.toString());
        }
        
    }
    
}
