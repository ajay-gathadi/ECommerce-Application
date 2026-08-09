package com.gathadi.ajay.ecommerce.repository;

import com.gathadi.ajay.ecommerce.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Slf4j
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void whenSavingUserWithDuplicateUserNameAndEmail_thenThrowException() {
        User user1 = new User("ajay", "ajay@test.com", "pass1");
        log.info("User1 Object: {}", user1);

        User persist = entityManager.persist(user1);
        log.info("Persisting entity: {}", persist);
        entityManager.flush();

        User user2 = new User("ajay", "ajay@test.com", "pass2");
        log.info("Attempting to persist second user with duplicate: {}", user2);

        assertThrows(Exception.class, () -> {
            entityManager.persist(user2);
            entityManager.flush();
        });
    }
}