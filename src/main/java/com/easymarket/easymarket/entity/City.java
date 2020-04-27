package com.easymarket.easymarket.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "city")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @ToString.Exclude
    @ManyToOne
    @JoinTable(name = "trip_has_city",
            joinColumns =
                    {@JoinColumn(name = "city_id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "trip_id")})
    private Trip trip;

}
