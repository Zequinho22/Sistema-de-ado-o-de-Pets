package com.adote.dto;

public class AuthDTOs {
    public record LoginRequest(String email, String password) {}
    public record LoginResponse(String token, String userId, String name, String email) {}
}
