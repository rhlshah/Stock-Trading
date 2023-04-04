package com.stocktrading.platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    @Column(name = "Ticker", nullable = false)
    private String ticker;
    @Column(name = "Opening Price", nullable = false)
    private float open;
    @Column(name = "Day High", nullable = false)
    private float high;
    @Column(name = "Day Low", nullable = false)
    private float low;

}
