package com.easymarket.easymarket.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "cargo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Cargo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float weight;
    private float width;
    private float length;
    private float height;
    private String location;
    @Column(name = "transportation_cost")
    private BigDecimal transportationCost;
    @Enumerated(EnumType.STRING)
    private CargoCondition cargoCondition;
    private Boolean isPaid;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", columnDefinition="integer")
    private Trip trip;

}