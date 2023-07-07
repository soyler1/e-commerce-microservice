package com.ecommercemicro.notificationservice;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@RequiredArgsConstructor
public class MailSender {

    private final JavaMailSender javaMailSender;

    public void sendMailOnOrderEvent(OrderEvent orderEvent){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ecommerceapp09@gmail.com");
        message.setTo(orderEvent.getMailAddress());
        message.setSubject("Siparişinizin Durumu Hakkında!");
        String body ="Sayın " + orderEvent.getName() + " " + orderEvent.getLastName() + ",\n" +
                orderEvent.getOrderNumber() + " numaralı siparişinizin durumu güncellenmiştir.\n\n" +
                "Siparişinizin güncel durumu: " + orderEvent.getStatus() + "\n\nBizi tercih ettiğiniz için teşekkür ederiz.";

        javaMailSender.send(message);
    }
}
