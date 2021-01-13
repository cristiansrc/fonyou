/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fonyou.employee.model;

import org.springframework.http.HttpStatus;

/**
 * Clase en el que se mapea la informacion del estado de la respuesta de los servicios
 * @author Cristhiam Reina <cristiansrc@gmail.com>
 */
public class Response {

    private HttpStatus httpStatus = HttpStatus.OK;
    private Integer responseCode;
    private String responseMessage;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        responseCode = httpStatus.value();
        this.httpStatus = httpStatus;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

}
