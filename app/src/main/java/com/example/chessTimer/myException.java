package com.example.chessTimer;

public class myException extends Exception {
    private int code;

    //getter
    public int getCode() {
        return code;
    }
    public String getMessange() {
        return super.getMessage();
    }

    //costruttore
    public myException(String message) {
        super(message);
    }
    public myException(String message, int code) {
        super(message);
        this.code = code;
    }

    //Override
    @Override
    public String toString() {
        return "****** " + super.getMessage() + " ******";
    }
}
