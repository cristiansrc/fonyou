/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fonyou.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

/**
 *
 * @author Cristhiam Reina
 */
public class Employee {
    
    private Integer id;
    
    private String name;
    
    private String lastName;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateAdmission;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateFinish;
    
    private Double baseSalary;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateAdmission() {
        return dateAdmission;
    }

    public void setDateAdmission(Date dateAdmission) {
        this.dateAdmission = dateAdmission;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }
    
    
}
