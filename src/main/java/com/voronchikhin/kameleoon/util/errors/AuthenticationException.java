package com.voronchikhin.kameleoon.util.errors;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String msg){
        super(msg);
    }
}
