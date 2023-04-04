package com.stocktrading.platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CompositeType;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Portfolio")
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    @Column(name = "Email Address")
    private String emailId;
    @Column(name = "Ticker", nullable = false)
    private String ticker;
    @Column(name = "Quantity", nullable = false)
    private Long quantity;
}
