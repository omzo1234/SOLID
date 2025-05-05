package com.example.library.service.Impl;


import com.example.library.model.Member;
import com.example.library.service.interfaces.NotificationService;
import com.example.library.service.interfaces.NotificationStrategy;


import org.springframework.beans.factory.annotation.Qualifier;


import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationStrategy notificationStrategy;

public NotificationServiceImpl(
@Qualifier("emailNotificationStrategy") NotificationStrategy strategy) {
this.notificationStrategy = strategy;
}
@Override
public void sendNotification(Member member, String message) {
notificationStrategy.sendNotification(member, message);
}
}