package cz.muni.fi.pv243.ars.persistence.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.muni.fi.pv243.ars.persistence.enumeration.AccommodationType;
import cz.muni.fi.pv243.ars.persistence.validation.AddressConstraint;

/**
 * Created by jsmolar on 5/19/18.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Offer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 25)
    private String name;

    @NotNull
    @AddressConstraint
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @NotNull
    private Integer capacity;

    @NotNull
    @Min(0)
    private Integer price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccommodationType accommodationType;

    private Boolean isAnimalFriendly;

    private Boolean isSmokerFriendly;

    @ManyToOne
    private User user;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "offer", fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "offer", fetch = FetchType.EAGER)
    private Set<UserComment> userComments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Offer setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Offer setName(String name) {
        this.name = name;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public Offer setAddress(Address address) {
        this.address = address;
        return this;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Offer setCapacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public AccommodationType getAccommodationType() {
        return accommodationType;
    }

    public Offer setAccommodationType(AccommodationType accommodationType) {
        this.accommodationType = accommodationType;
        return this;
    }

    public Boolean getAnimalFriendly() {
        return isAnimalFriendly;
    }

    public Offer setAnimalFriendly(Boolean animalFriendly) {
        isAnimalFriendly = animalFriendly;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Boolean getSmokerFriendly() {
        return isSmokerFriendly;
    }

    public Offer setSmokerFriendly(Boolean smokerFriendly) {
        isSmokerFriendly = smokerFriendly;
        return this;
    }

    public Offer addReservation(Reservation reservation) {
        reservations.add(reservation);
        return this;
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Offer addUserComment(UserComment userComment) {
        userComments.add(userComment);
        return this;
    }

    public void removeUserComment(UserComment userComment) {
        userComments.remove(userComment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Offer offer = (Offer) o;

        if (!getName().equals(offer.getName()))
            return false;
        if (!getAddress().equals(offer.getAddress()))
            return false;
        return getAccommodationType() == offer.getAccommodationType();

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getAccommodationType() != null ? getAccommodationType().hashCode() : 0 );
        return result;
    }
}
