package com.easymarket.easymarket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @OneToOne(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cargo cargo;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "trip_has_city",
            joinColumns =
                    { @JoinColumn(name = "trip_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "city_id", referencedColumnName = "id") })
    private List<City> cities;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
