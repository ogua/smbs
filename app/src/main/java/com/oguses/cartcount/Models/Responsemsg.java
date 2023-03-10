package com.oguses.cartcount.Models;

public class Responsemsg {
    private String error;
    private String message;

    public Responsemsg(String error, String message) {
        this.error = error;
        this.message = message;
    }


    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

}
