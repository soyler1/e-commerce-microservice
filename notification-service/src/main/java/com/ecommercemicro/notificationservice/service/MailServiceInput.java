package com.ecommercemicro.notificationservice.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MailServiceInput {
    private String to;
    private String subject;
    private String body;
}
