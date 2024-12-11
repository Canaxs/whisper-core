package com.whisper.enums;

import com.whisper.persistence.entity.Subscription;
import com.whisper.persistence.entity.User;

public enum Package {
    FREE,
    BASIC,
    STANDARD,
    PREMIUM;

    public static Subscription convert(String step) {
        Subscription subscription = new Subscription();
        switch (step.toUpperCase()) {
            case "FREE":
                subscription.setPlanName(step.toUpperCase());
                subscription.setExclusive(false);
                subscription.setEarning(false);
                subscription.setWriteLimit(5);
                break;
            case "BASIC":
                subscription.setPlanName(step.toUpperCase());
                subscription.setExclusive(false);
                subscription.setEarning(true);
                subscription.setWriteLimit(10);
                break;
            case "STANDARD":
                subscription.setPlanName(step.toUpperCase());
                subscription.setExclusive(false);
                subscription.setEarning(true);
                subscription.setWriteLimit(30);
                break;
            case "PREMIUM":
                subscription.setPlanName(step.toUpperCase());
                subscription.setExclusive(true);
                subscription.setEarning(true);
                subscription.setWriteLimit(1000);
                break;
        };
        return subscription;
    }
}
