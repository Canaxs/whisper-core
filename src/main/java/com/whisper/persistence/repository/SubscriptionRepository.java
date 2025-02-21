package com.whisper.persistence.repository;

import com.whisper.persistence.entity.Subscription;
import com.whisper.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription getSubscriptionsByUser(User user);
}
