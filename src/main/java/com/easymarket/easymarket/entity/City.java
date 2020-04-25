package com.easymarket.easymarket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @ManyToOne
    @JoinTable(name = "trip_has_city",
            joinColumns =
                    {@JoinColumn(name = "city_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "trip_id", referencedColumnName = "id")})
    private Trip trip;

}
