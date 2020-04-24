package com.easymarket.easymarket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

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
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "cargo_user",
            joinColumns = { @JoinColumn(name = "cargo_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> users;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;
}