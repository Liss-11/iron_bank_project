package com.ironhack.iron_bank_project.users.service;

import com.ironhack.iron_bank_project.enums.UserStatus;
import com.ironhack.iron_bank_project.exception.UserNotFoundException;
import com.ironhack.iron_bank_project.users.model.Admin;
import com.ironhack.iron_bank_project.users.model.Customer;
import com.ironhack.iron_bank_project.users.model.ThirdParty;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.users.repository.UserRepository;
import com.ironhack.iron_bank_project.utils.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
//@Profile("demo")
class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        var address1 = new Address("Peralada", "Figueres", "17600", "Spain");
        var user1 = new Customer("antonio", "antonio@delcastillo.com", "Antonio123",
                LocalDate.parse("2003-04-11"), address1, UserStatus.ACTIVE, "Your favorite color", "Red");
        user1.setId("123456");
        users.add(user1);

        var address2 = new Address("Casstellets", "Basrcelona", "66786", "Spain");
        var user2 = new Customer("laura", "laura@castellanos.com", "Laura123",
                LocalDate.parse("2000-06-15"), address2, UserStatus.ACTIVE, "Your dog's name", "Ara");
        users.add(user2);

        var user3 = new Admin("alissia", "frolova.alissia@gmail.com", "Mushu0311");
        users.add(user3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void deleteUserByIdTestIfUserExists() {
        Optional <User> myUser = Optional.of(users.get(0));
        when(userRepository.findById("123456")).thenReturn(myUser);
        var resp1 = ResponseEntity.ok("User deleted Successfully!");
        userService.deleteUserById("123456");
        assertEquals(resp1, userService.deleteUserById("123456"));
    }

    @Test
    void deleteUserByIdTestIfUserDoesntExist() {
        when(userRepository.findById("123456")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, ()-> userService.deleteUserById("123456"));
    }



    @Test
    void deleteActualUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void changeStatus() {
    }
}