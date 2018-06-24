package cz.muni.fi.pv243.ars.persistence.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import cz.muni.fi.pv243.ars.persistence.enumeration.AccommodationType;
import cz.muni.fi.pv243.ars.persistence.validation.AddressConstraint;

/**
 * Created by jsmolar on 5/19/18.
 */
@Entity
@XmlRootElement
public class Offer implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @AddressConstraint
    @OneToOne(mappedBy = "offer", cascade = CascadeType.ALL)
    private Address address;

    private Integer capacity;

    @Enumerated
    private AccommodationType accommodationType;

    private Boolean isAnimalFriendly;

    private Boolean isSmokerFriendly;

    @ManyToOne
    private User user;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "offer")
    private Set<Reservation> reservations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Offer setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Offer offer = (Offer) o;

        if (!getAddress().equals(offer.getAddress()))
            return false;
        if (!getCapacity().equals(offer.getCapacity()))
            return false;
        if (getAccommodationType() != offer.getAccommodationType())
            return false;
        if (isAnimalFriendly != null ?
            !isAnimalFriendly.equals(offer.isAnimalFriendly) :
            offer.isAnimalFriendly != null)
            return false;
        return isSmokerFriendly != null ?
            isSmokerFriendly.equals(offer.isSmokerFriendly) :
            offer.isSmokerFriendly == null;

    }

    @Override
    public int hashCode() {
        int result = getAddress().hashCode();
        result = 31 * result + getCapacity().hashCode();
        result = 31 * result + (getAccommodationType() != null ? getAccommodationType().hashCode() : 0);
        result = 31 * result + (isAnimalFriendly != null ? isAnimalFriendly.hashCode() : 0);
        result = 31 * result + (isSmokerFriendly != null ? isSmokerFriendly.hashCode() : 0);
        return result;
    }

}
