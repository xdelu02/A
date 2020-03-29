package com.example.chessTimer;

public class myException extends Exception {
    //costruttore
    public myException(String message) {
        super(message);
    }

    //Override
    @Override
    public String toString() {
        return super.getMessage();
    }
}
