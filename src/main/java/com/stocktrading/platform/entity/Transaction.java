package com.stocktrading.platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Transaction")


public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    @Column(name = "Ticker", nullable = false)
    private String ticker;

    @Column(name = "Email Address", nullable = false)
    private String emailId;

    @Column(name = "Execution Date", nullable = false)
    private LocalDate date;
    @Column(name = "Order Price", nullable = false)
    private float price;
    @Column(name = "Status", nullable = false)
    private String status;
    @Column(name = "Quantity", nullable = false)
    private Long quantity;
}
