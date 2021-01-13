/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fonyou.employee.model;

import java.util.List;

/**
 *
 * @author Cristhiam Reina
 */
public class ReponseListEmployee {
    private Response response;
    private List<Employee> Employes;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<Employee> getEmployes() {
        return Employes;
    }

    public void setEmployes(List<Employee> Employes) {
        this.Employes = Employes;
    }
    
    
}
