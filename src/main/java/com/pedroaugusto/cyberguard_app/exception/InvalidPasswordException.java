package com.pedroaugusto.cyberguard_app.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Invalid password");
    }
}
