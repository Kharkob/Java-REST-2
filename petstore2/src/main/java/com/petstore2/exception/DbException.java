package com.petstore2.exception;


public class DbException extends RuntimeException {


    public DbException() {
    }
    
    public DbException (String message) {
        super(message);
    }

    public DbException(Throwable cause) {
        super(cause);
    }

   
}
