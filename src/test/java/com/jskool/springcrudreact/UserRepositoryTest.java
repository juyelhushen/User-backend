package com.jskool.springcrudreact;

import com.jskool.springcrudreact.entity.User;
import com.jskool.springcrudreact.repository.UserRepo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    //User Repository Layer Unit Tests


    @Autowired
    UserRepo repository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveUserTest() throws Exception {
        User user = User.builder()
                .name("John")
                .email("john@gmail.com")
                .username("John cadre")
                .password("Juyel123")
                .build();

        repository.save(user);
        assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    public void testUserByIdTest() throws Exception {
        User user = repository.findById(1L).get();
        assertThat(user.getId()).isEqualTo(1L);
    }


    @Test
    @Order(2)
    public void getUserAllTest() throws Exception {
        List<User> userAll = repository.findAll();
        assertThat(userAll.size()).isGreaterThan(0);
    }


    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateUserTest() throws Exception {
        User user = repository.findById(1L).get();
        user.setEmail("test@example.com");
        User userUpdated = repository.save(user);
        assertThat(userUpdated.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteUserTest() throws Exception {
        User user = repository.findById(1L).get();
        repository.delete(user);
        User user2 = null;

        Optional<User> userOptional = repository.findByEmail("test@example.com");
        if(userOptional.isPresent()) {
            user2 = userOptional.get();
        }
        assertThat(user2).isNull();
    }

}
