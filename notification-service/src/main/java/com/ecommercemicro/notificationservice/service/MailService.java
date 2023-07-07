package com.ecommercemicro.notificationservice.service;

import com.ecommercemicro.notificationservice.OrderEvent;

public interface MailService {
    void sendMail(OrderEvent orderEvent);
}
