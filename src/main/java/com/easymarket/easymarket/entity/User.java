package com.easymarket.easymarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String email;
    @JsonIgnore
    @Column(name = "pass")
    private String password;
    private Role role;
    @Column(name = "block")
    private Boolean isBlock;

    @ToString.Exclude
    @OneToMany( fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Set<Cargo> cargo;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Set<Trip> trips;
}
