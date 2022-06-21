package com.example.commerce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {
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
