package com.telusko.titans.pms.exceptions;

public class ProductNotFoundException extends RuntimeException {   

	public ProductNotFoundException(String message) {
        super(message);
    }
}