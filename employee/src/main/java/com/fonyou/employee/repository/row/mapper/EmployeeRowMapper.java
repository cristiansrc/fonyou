/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fonyou.employee.repository.row.mapper;

import com.fonyou.employee.model.Employee;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Cristhiam Reina
 */
public class EmployeeRowMapper implements RowMapper<Employee> {
    
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Employee employee = new Employee();
        
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setDateAdmission(rs.getDate("date_admission"));
        employee.setDateFinish(rs.getDate("date_finish"));
        employee.setBaseSalary(rs.getDouble("base_salary"));
        
        return employee;
    }
}
