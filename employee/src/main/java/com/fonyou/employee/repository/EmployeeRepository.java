/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fonyou.employee.repository;

import com.fonyou.employee.model.Employee;
import com.fonyou.employee.repository.row.mapper.EmployeeRowMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Cristhiam Reina
 */

@Repository
public class EmployeeRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void insert(Employee employee){
        String sql = 
            "INSERT INTO employee \n" +
            "   (name, last_name, date_admission, date_finish, base_salary) \n" +
            "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, employee.getName(), employee.getLastName(), 
                employee.getDateAdmission(), employee.getDateFinish(), 
                employee.getBaseSalary());
    }
    
    public void update(Employee employee){
        String sql = 
            "UPDATE employee\n" +
            "SET\n" +
            "	name = ?,\n" +
            "	last_name = ?,\n" +
            "	date_admission = ?,\n" +
            "	date_finish = ?,\n" +
            "	base_salary = ?\n" +
            "WHERE id = ?;";
        
        jdbcTemplate.update(sql, employee.getName(), employee.getLastName(), 
                employee.getDateAdmission(), employee.getDateFinish(), 
                employee.getBaseSalary(), employee.getId());
        
    }
    
    public void delete(Integer id){
        String sql = "DELETE FROM employee WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    public List<Employee> select(){
        String sql = "SELECT * from employee order by id desc";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }
    
    public Employee selectOne(Integer id){
        String sql = "SELECT * from employee WHERE id = ?";
        List<Employee> employes = jdbcTemplate.query(sql, new EmployeeRowMapper(), id);
        return (employes != null && employes.size() > 0) ? employes.get(0) : new Employee(); 
    }
}
