package com.ecommercemicro.userservice.data.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String mailAddress;
    @NonNull
    private String password;
    @NonNull
    private String name;
    @NonNull
    private String lastName;
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;

}
