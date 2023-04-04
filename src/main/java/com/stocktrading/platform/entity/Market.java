package com.stocktrading.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Market")
public class Market {
    @Id
    @Column(name = "Primary Key")
    private int pk = 1;
    @Column(name = "Start Time")
    private LocalTime start;
    @Column(name = "End Time")
    private LocalTime end;
    //private ArrayList<Integer> schedule;
}
