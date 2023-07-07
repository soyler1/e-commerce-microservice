package com.ecommercemicro.userservice.data.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String addressBody;
    @NonNull
    private Integer zipCode;
    @NonNull
    private String district;
    @NonNull
    private String city;
    @NonNull
    private String country;
}
