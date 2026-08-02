package com.gathadi.ajay.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "Building Name must be 5 characters.")
    private String buildingName;

    @NotBlank
    @Size(min = 5, message = "Street must be 5 characters.")
    private String street;

    @NotBlank
    @Size(min = 5, message = "City must be 5 characters.")
    private String city;

    @NotBlank
    @Size(min = 6, message = "Pin Code must be 6 characters.")
    private String pinCode;

    @NotBlank
    @Size(min = 5, message = "State must be 5 characters.")
    private String state;

    @NotBlank
    @Size(min = 5, message = "Country must be 5 characters.")
    private String country;

    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(String buildingName, String street, String city, String pinCode, String state, String country) {
        this.buildingName = buildingName;
        this.street = street;
        this.city = city;
        this.pinCode = pinCode;
        this.state = state;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return getAddressId() != null && getAddressId().equals(address.getAddressId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
