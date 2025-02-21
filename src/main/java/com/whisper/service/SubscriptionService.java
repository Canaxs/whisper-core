package com.whisper.service;

import com.whisper.persistence.entity.Subscription;

public interface SubscriptionService {

    Subscription getSubscribe();

    Boolean writeLimitDrop();
}
