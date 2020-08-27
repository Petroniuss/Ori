package com.softy.oribackend.exception;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(String id) {
        super("Player with id: " + id + " doesn't exist.");
    }
}
