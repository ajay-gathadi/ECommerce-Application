package com.gathadi.ajay.ecommerce.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

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
}
