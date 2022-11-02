package com.school.ecommerceupch.controllers.exceptions;

public class UserRoleAlreadyExistsException extends Exception{
    public UserRoleAlreadyExistsException(String message) {
        super(message);
    }
}
