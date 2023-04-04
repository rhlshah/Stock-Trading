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
@Table(name = "Stock")
public class Stock {
    @Id
    @Column(name = "Ticker")
    private String ticker;
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "Current Price", nullable = false)
    private float curPrice;
    @Column(name = "Volume", nullable = false)
    private Long volume;
    @Column(name = "Available", nullable = false)
    private Long available;
}
