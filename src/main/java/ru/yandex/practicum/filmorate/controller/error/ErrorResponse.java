package ru.yandex.practicum.filmorate.controller.error;

public class ErrorResponse {
    private String description;

    public ErrorResponse(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
