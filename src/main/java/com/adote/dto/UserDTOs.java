package com.adote.dto;

public class UserDTOs {
    public record CreateUserRequest(String name, String email, String password) {}
    public record UpdateUserRequest(String name) {}
}
