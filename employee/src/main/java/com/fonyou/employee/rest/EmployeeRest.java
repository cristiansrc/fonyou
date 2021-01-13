/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fonyou.employee.rest;

import com.fonyou.employee.exception.EmployeeNotFoundException;
import com.fonyou.employee.exception.InvalidaDataException;
import com.fonyou.employee.model.Employee;
import com.fonyou.employee.model.ReponseEmployee;
import com.fonyou.employee.model.ReponseListEmployee;
import com.fonyou.employee.model.RequestPay;
import com.fonyou.employee.model.Response;
import com.fonyou.employee.model.ResponsePay;
import com.fonyou.employee.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Cristhiam Reina
 */

@Api(tags = "Employee")
@RestController
@RequestMapping("")
public class EmployeeRest {
    
    @Autowired
    private EmployeeService employeeService;
    
    @ApiOperation("Calcula el valor del salario de un empleado")
    @ApiResponses(value = {
                    @ApiResponse(code = 400, message = "Los campos no se encuentran"),
                    @ApiResponse(code = 200, message = "Se calculo correctamente el salario.") })
    @RequestMapping(method = RequestMethod.POST, value = "/pay", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePay> pay (@RequestBody RequestPay requestPay){
        ResponsePay responsePay = new ResponsePay();
        Response response = new Response();
        
        try {
            
            response.setHttpStatus(HttpStatus.OK);
            response.setResponseMessage("El valor del pago se calculo correctamente.");
            responsePay.setPay(employeeService.calculationPay(requestPay));
           
        } catch (InvalidaDataException ex) {
            Logger.getLogger(EmployeeRest.class.getName()).log(Level.SEVERE, null, ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setResponseMessage(ex.getMessage());
        }
        
        responsePay.setResponse(response);
        
        return new ResponseEntity<>(responsePay, response.getHttpStatus());
    }
    
    @ApiOperation("Guarda en base de datos un empleado")
    @ApiResponses(value = {
                    @ApiResponse(code = 400, message = "Los campos no se encuentran"),
                    @ApiResponse(code = 200, message = "El empleado se guardo correctamente.") })
    @RequestMapping(method = RequestMethod.POST, value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> employeeSave(@RequestBody Employee employee){
        Response response = new Response();
        try {
            employeeService.create(employee);
            response.setHttpStatus(HttpStatus.OK);
            response.setResponseMessage("El empleado se guardo correctamente.");
        } catch (InvalidaDataException ex) {
            Logger.getLogger(EmployeeRest.class.getName()).log(Level.SEVERE, null, ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setResponseMessage(ex.getMessage());
        }
        
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    
    @ApiOperation("Actualiza en base de datos un empleado")
    @ApiResponses(value = {
                    @ApiResponse(code = 400, message = "Los campos no se encuentran"),
                    @ApiResponse(code = 200, message = "El empleado se actualizo correctamente.") })
    @RequestMapping(method = RequestMethod.PUT, value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> employeeUpdate(@RequestBody Employee employee){
        Response response = new Response();
        try {
            employeeService.update(employee);
            response.setHttpStatus(HttpStatus.OK);
            response.setResponseMessage("El empleado se actualizo correctamente.");
        } catch (InvalidaDataException ex) {
            Logger.getLogger(EmployeeRest.class.getName()).log(Level.SEVERE, null, ex);
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            response.setResponseMessage(ex.getMessage());
        } catch (EmployeeNotFoundException ex) {
            Logger.getLogger(EmployeeRest.class.getName()).log(Level.SEVERE, null, ex);
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setResponseMessage(ex.getMessage());
        }
        
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    
    @ApiOperation("Elimina en base de datos un empleado")
    @ApiResponses(value = {
                    @ApiResponse(code = 404, message = "El empleado no se encontro"),
                    @ApiResponse(code = 200, message = "El empleado se elimino correctamente")})
    @RequestMapping(method = RequestMethod.DELETE, value = "/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> employeeDelete(
            @ApiParam(name="id", value = "Identificador del empleado.", required = true) 
            @PathVariable("id") Integer id){
        
        Response response = new Response();
        try {
            employeeService.delete(id);
            response.setHttpStatus(HttpStatus.OK);
            response.setResponseMessage("El empleado se elimino correctamente");
        } catch (EmployeeNotFoundException ex) {
            Logger.getLogger(EmployeeRest.class.getName()).log(Level.SEVERE, null, ex);
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setResponseMessage(ex.getMessage());
        }
        
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    
    @ApiOperation("Consulta de todos los empleados que se encuentran en base de datos")
    @ApiResponses(value = {
                    @ApiResponse(code = 200, message = "Se consulto los empleados correctamente")})
    @RequestMapping(method = RequestMethod.GET, value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReponseListEmployee> get(){
        
        Response response = new Response();
        ReponseListEmployee reponseListEmployee = new ReponseListEmployee(); 
        response.setHttpStatus(HttpStatus.OK);
        response.setResponseMessage("Se consulto los empleados correctamente");
        reponseListEmployee.setEmployes(employeeService.get());
        reponseListEmployee.setResponse(response);
        
        return new ResponseEntity<>(reponseListEmployee, response.getHttpStatus());
    }
    
    @ApiOperation("Consulta de un empleado que se encuentran en base de datos")
    @ApiResponses(value = {
                    @ApiResponse(code = 200, message = "Se consulto un empleado correctamente"),
                    @ApiResponse(code = 404, message = "El empleado no se encontro")})
    @RequestMapping(method = RequestMethod.GET, value = "/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReponseEmployee> getOne(@PathVariable("id") Integer id){
        
        Response response = new Response();
        ReponseEmployee reponseEmployee = new ReponseEmployee(); 
        try {
            reponseEmployee.setEmployee(employeeService.getOne(id));
            response.setHttpStatus(HttpStatus.OK);
            response.setResponseMessage("Se consulto el empleado correctamente");
        } catch (EmployeeNotFoundException ex) {
            Logger.getLogger(EmployeeRest.class.getName()).log(Level.SEVERE, null, ex);
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setResponseMessage("No se encontro el empleado");
        }
        
        reponseEmployee.setResponse(response);
        
        return new ResponseEntity<>(reponseEmployee, response.getHttpStatus());
    }
}
