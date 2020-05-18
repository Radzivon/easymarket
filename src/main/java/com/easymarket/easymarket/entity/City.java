package com.easymarket.easymarket.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "city")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "trip_cities",
            joinColumns = @JoinColumn(name = "city_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id"))
    private List<Trip> trips;
}
