package com.easymarket.model;

import javax.persistence.*;

@Entity
@Table(name = "cargo")
public class Cargo {
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
    private int transportationCost;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transporter_id")
    private User transporter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    public Cargo() {
    }

    public Cargo(String name, float weight, float width, float length, float height, String location, int transportationCost, User owner, User transporter, User manager, Trip trip) {
        this.name = name;
        this.weight = weight;
        this.width = width;
        this.length = length;
        this.height = height;
        this.location = location;
        this.transportationCost = transportationCost;
        this.owner = owner;
        this.transporter = transporter;
        this.manager = manager;
        this.trip = trip;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTransportationCost() {
        return transportationCost;
    }

    public void setTransportationCost(int transportationCost) {
        this.transportationCost = transportationCost;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getTransporter() {
        return transporter;
    }

    public void setTransporter(User transporter) {
        this.transporter = transporter;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
