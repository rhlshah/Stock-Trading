package com.stocktrading.platform.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

public class LimitOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oid;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "Ticker", nullable = false)
    private String ticker;

    @Column(name = "Email Address", nullable = false)
    private String emailId;

    @Column(name = "Expired Date", nullable = false)
    private LocalDate expireDate;

    @Column(name = "Execution Date", nullable = false)
    private LocalDate executionDate;

    @Column(name = "Order Price", nullable = false)
    private float price;

    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "Quantity", nullable = false)
    private Long quantity;


}
