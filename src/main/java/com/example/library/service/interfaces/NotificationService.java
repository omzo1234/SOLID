package com.example.library.service.interfaces;

import com.example.library.model.Member;
public interface NotificationService {
    void sendNotification(Member member, String message);

}
