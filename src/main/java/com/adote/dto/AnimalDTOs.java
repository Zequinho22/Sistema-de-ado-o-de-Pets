package com.adote.dto;

public class AnimalDTOs {
    public record CreateAnimalRequest(String name, String type, String gender) {}
    public record UpdateAnimalStatusRequest(Boolean available) {}
}
