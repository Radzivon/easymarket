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
    private long id;
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

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @ManyToOne
    @JoinTable(name = "trip_has_city",
            joinColumns =
                    {@JoinColumn(name = "cargo_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "trip_id", referencedColumnName = "id")})
    private Trip trip;
}