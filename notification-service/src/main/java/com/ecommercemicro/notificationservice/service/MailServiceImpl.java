package com.ecommercemicro.notificationservice.service;

import com.ecommercemicro.notificationservice.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;


    @Override
    public void sendMail(OrderEvent orderEvent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ecommerceapp09@gmail.com");
        message.setTo(orderEvent.getMailAddress());
        message.setSubject("Siparişinizin Durumu Hakkında!");
        message.setText("Sayın " + orderEvent.getName() + " " + orderEvent.getLastName() + ",\n" +
                orderEvent.getOrderNumber() + " numaralı siparişinizin durumu güncellenmiştir.\n\n" +
                "Siparişinizin güncel durumu: " + orderEvent.getStatus() + "\n\nBizi tercih ettiğiniz için teşekkür ederiz.");

        javaMailSender.send(message);
    }
}
