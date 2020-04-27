package com.easymarket.easymarket.entity;

import lombok.*;

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
    private Long id;
    @Column(name = "current_city")
    private String currentCity;
    private String car;
    @Enumerated(EnumType.STRING)
    private TripCondition tripCondition;
    private Boolean isPaid;
    @OneToMany
    @JoinTable(name = "trip_has_cargo",
            joinColumns =
                    {@JoinColumn(name = "trip_id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "cargo_id")})
    private List<Cargo> cargo;

    @OneToMany
    @JoinTable(name = "trip_has_city",
            joinColumns =
                    {@JoinColumn(name = "trip_id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "city_id")})
    private List<City> cities;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
