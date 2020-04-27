package com.easymarket.easymarket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "trip")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "current_city")
    private String currentCity;
    private String car;
    private TripCondition tripCondition;
    private Boolean isPaid;
    @OneToMany
    @JoinTable(name = "trip_has_cargo",
            joinColumns =
                    { @JoinColumn(name = "trip_id")},
            inverseJoinColumns =
                    { @JoinColumn(name = "cargo_id") })
    private Set<Cargo> cargo;
    @OneToMany
    @JoinTable(name = "trip_has_city",
            joinColumns =
                    { @JoinColumn(name = "trip_id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "city_id") })
    private List<City> cities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
