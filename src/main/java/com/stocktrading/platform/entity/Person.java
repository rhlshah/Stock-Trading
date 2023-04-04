package com.stocktrading.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Person")
public class Person {
    //private Long uid;
    @Id
    @Column(name = "Email Address")
    private String emailId;
    @Column(name = "Full Name", nullable = false)
    private String name;
    @Column(name = "Balance", nullable = false)
    private float balance;
    @Column(name = "Password", nullable = false)
    private String password;
    @Column(name = "Roles", nullable = false)
    private String roles;

    @Override
    public String toString() {
        return "Person{" +
                "emailId='" + emailId + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
