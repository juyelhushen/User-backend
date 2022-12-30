package com.jskool.springcrudreact;

import com.jskool.springcrudreact.entity.User;
import com.jskool.springcrudreact.repository.UserRepo;
import com.jskool.springcrudreact.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo repository;

    @InjectMocks
    private UserService service;

    private User user;


    @BeforeEach
    void setup() throws Exception {
        user = User.builder()
                .id(1L)
                .name("John")
                .username("John wick")
                .password("password")
                .email("john@gmail.com")
                .build();

        // MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser() {
        //given(repository.findByEmail(user.getEmail())).willReturn(Optional.empty());
        given(repository.save(user)).willReturn(user);

        User saveUser = service.AddUser(user);
        assertThat(saveUser).isNotNull();
    }



    //userById
    //
//    @DisplayName("JUnit test for getAllUser method which throws exception")
    @Test
    public void getAllUserTest() throws Exception {
        User user2 = User.builder()
                .id(2L)
                .name("Juyel")
                .username("Juyel wick")
                .password("password123")
                .email("juyel@gmail.com")
                .build();

        given(repository.findAll()).willReturn(List.of(user, user2));
        List<User> users = service.getAllUser();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);
    }

    //userById
    @Test
    public void userByIdTest() throws Exception {
        given(repository.findById(1L)).willReturn(Optional.of(user));
        User userbyId = service.userById(user.getId()).get();
        assertThat(userbyId).isNotNull();
    }


    //update
    @Test
    public void updateUserTest() {
        given(repository.save(user)).willReturn(user);
            user.setUsername("Test");
            user.setEmail("update@test.com");
            User updatedUser = service.updateUser(userId,user);

        //calling update method from service layer
       // User updatedUser = service.updateUser(1L,user);

        assertThat(updatedUser.getEmail()).isEqualTo("update@test.com");
        assertThat(updatedUser.getUsername()).isEqualTo("Test");
    }

    //Delete Unit Test by J5
    @Test
    public void userDeleteByIdTest()  {
        long userId = 1L;
        willDoNothing().given(repository).deleteById(userId);
        service.deleteUserById(userId);
        verify(repository, times(1)).deleteById(userId);
    }


}
