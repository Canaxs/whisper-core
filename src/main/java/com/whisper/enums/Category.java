package com.whisper.enums;

public enum Category {
    SPORT,
    TECHNOLOGY,
    POLITICS,
    FINANCE,
    AGENDA,
    WORLD,
    MAGAZINE;

    public static Category convert(String step) {
        return switch (step) {
            case "SPORT" -> Category.SPORT;
            case "TECHNOLOGY" -> Category.TECHNOLOGY;
            case "POLITICS" -> Category.POLITICS;
            case "FINANCE" -> Category.FINANCE;
            case "AGENDA" -> Category.AGENDA;
            case "WORLD" -> Category.WORLD;
            case "MAGAZINE" -> Category.MAGAZINE;
            default -> null;
        };
    }
}
