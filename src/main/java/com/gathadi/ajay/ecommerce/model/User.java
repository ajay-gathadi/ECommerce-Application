package com.gathadi.ajay.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    @NotBlank
    @Size(max = 20)
    private String username;

    @Email
    @Column(unique = true)
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private Set<Product> products = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "users_addresses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getUserId() != null && getUserId().equals(user.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // --- Synchronization Helper Methods ---

    public void addProduct(Product product) {
        this.products.add(product);
        product.setUser(this); // Synchronizes the owning side
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.setUser(null); // Dereferences the owning side, triggering orphanRemoval
    }

    public void addRole(Role role) {
        this.roles.add(role);
        // role.getUsers().add(this); // Required if Role entity has a bidirectional Set<User>
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        // role.getUsers().remove(this); // Required if Role entity has a bidirectional Set<User>
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
        address.getUsers().add(this); // Synchronizes the inverse side
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
        address.getUsers().remove(this); // Dereferences the inverse side
    }
}
