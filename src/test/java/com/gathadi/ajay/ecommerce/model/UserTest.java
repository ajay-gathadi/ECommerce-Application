package com.gathadi.ajay.ecommerce.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class UserTest {
    public ModelMapper modelMapper;

    public UserTest() {
        modelMapper = new ModelMapper();
        // Tell ModelMapper to read private fields directly instead of relying on Getters
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    }

    @Test
    public void checkWhatAddressesVariablePrintsOnCreation() {
        User user = new User();
        log.info("Address: {}", user.getAddresses());
        log.info(user.getProducts().toString());

        User user1 = new User("ajaygathadi", "ajaygathadi@gmail.com", "password");
        log.info("Before adding address: {}", user1.getAddresses());
        user1.getAddresses().add(new Address("Pawan Stotra", "Vasant Valley Road", "Kalyan", "421301",
                "Maharashtra", "India"));

        var readableAddresses = user1.getAddresses().stream()
                .map(address -> modelMapper.map(address, java.util.HashMap.class))
                .toList();

        log.info("After adding address: {}", readableAddresses);
    }

    @Test
    public void testEntityEqualityBasedOnId() {
        // SCENARIO 1: Two different objects representing the SAME database row
        User user1 = new User("ajay", "ajay@test.com", "pass1");
        user1.setUserId(1L);

        User user2 = new User("ajay-updated", "ajay-new@test.com", "pass2");
        user2.setUserId(1L);

        assertTrue(user1.equals(user2), "Users with the same ID should be equal.");
        assertEquals(user1.hashCode(), user2.hashCode(), "Users with the same ID should have the same hashCode.");

        // SCENARIO 2: Two different objects representing DIFFERENT database rows
        User user3 = new User("sarah", "sarah@test.com", "pass3");
        user3.setUserId(2L);

        assertFalse(user1.equals(user3), "Users with different IDs should NOT be equal.");

        // SCENARIO 3: Two new (transient) objects are never equal
        User newUserA = new User("new", "new@test.com", "pass4");
        User newUserB = new User("new", "new@test.com", "pass4");

        assertFalse(newUserA.equals(newUserB), "Two transient (unsaved) users should never be equal.");
    }

    @Test
    public void checkTheWorkingOfForeignEntityInAGivenEntity() {
        User user = new User("ajay", "ajay@test.com", "pass1");
        log.info(user.toString());

        Role userRole = new Role(AppRole.USER);
        user.addRole(userRole);
        

        log.info("Simplified presentation: {} ", user.getRoles().stream().map(Role::getRoleName).toList());
    }
}
