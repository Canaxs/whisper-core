package com.whisper.service.impl;

import com.whisper.persistence.entity.Subscription;
import com.whisper.persistence.entity.User;
import com.whisper.persistence.repository.SubscriptionRepository;
import com.whisper.persistence.repository.UserRepository;
import com.whisper.service.SubscriptionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserRepository userRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Subscription getSubscribe() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return subscriptionRepository.getSubscriptionsByUser(userRepository.findByUsername(user).get());
    }

    @Override
    public Boolean writeLimitDrop() {
        try {
            User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
            Subscription subscription = subscriptionRepository.getSubscriptionsByUser(user);
            subscription.setWriteLimit(subscription.getWriteLimit() - 1);
            subscriptionRepository.save(subscription);
            return true;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
