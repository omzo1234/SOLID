package com.example.library.service.interfaces;

import com.example.library.model.Member; // Correction de l'import
import org.springframework.stereotype.Component;

public abstract class NotificationStrategy {
    public abstract void sendNotification(Member member, String message);
}

@Component
class EmailNotificationStrategy extends NotificationStrategy {
    @Override
    public void sendNotification(Member member, String message) {
        // Implementation for sending email notification to the member
        System.out.println("Sending email to " + member.getEmail() + ": " + message);
    }
}

@Component
class ConsoleNotificationStrategy extends NotificationStrategy {
    @Override
    public void sendNotification(Member member, String message) {
        // Implementation for sending console notification to the member
        System.out.println("Console notification to " + member.getName() + ": " + message);
    }
}
