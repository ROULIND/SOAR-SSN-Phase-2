/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ssn.v1.exceptions;

/**
 *
 * @author dimitriroulin
 */
public class DoesNotExistException extends Exception {
    private String message;

    public DoesNotExistException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
