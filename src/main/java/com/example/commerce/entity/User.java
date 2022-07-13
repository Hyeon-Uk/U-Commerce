package com.example.commerce.entity;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name="users")
public class User extends BaseEntity{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    @NotNull
    private String name;
    @Column(name="email")
    @NotNull
    private String email;
    @Column(name="password")
    @NotNull
    private String password;
    @Column(name="address")
    @NotNull
    private String address;
    @Column(name="phone")
    @NotNull
    private String phone;
    @Column(name="mileage")
    private int mileage;

    @Builder
    public User(String name,String email, String password, String address, String phone,int mileage) {
        this.name=name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.mileage=mileage;
    }

}
