package com.ecommercemicro.notificationservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String orderNumber;
    private String mailAddress;
    private String name;
    private String lastName;
    private String status;
}
