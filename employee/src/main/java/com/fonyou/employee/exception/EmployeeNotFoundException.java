/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fonyou.employee.exception;

import com.fonyou.employee.constant.EmployeeConstant;

/**
 *
 * @author crist
 */
public class EmployeeNotFoundException extends Exception {
    
    public EmployeeNotFoundException() {
        super(EmployeeConstant.EMP_NOT_FOUND);
    }
}
