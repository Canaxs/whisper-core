package com.whisper.enums;

import java.util.ArrayList;
import java.util.List;

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
    public static String convertTR(Category step) {
        return switch (step) {
            case SPORT -> "Spor";
            case TECHNOLOGY -> "Teknoloji";
            case POLITICS -> "Politika";
            case FINANCE -> "Finans";
            case AGENDA -> "Gündem";
            case WORLD -> "Dünya";
            case MAGAZINE -> "Magazin";
            default -> null;
        };
    }
}
