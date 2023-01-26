package com.ironhack.iron_bank_project.demo;

import com.ironhack.iron_bank_project.enums.RoleEnum;
import com.ironhack.iron_bank_project.model.Customer;
import com.ironhack.iron_bank_project.repository.UserRepository;
import com.ironhack.iron_bank_project.utils.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
@Profile("demo")
public class DataLouder {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Bean
    public void loadFakeData(){
        try {
            loadDataUsers();
        } catch (Exception e) {
            System.err.printf("Couldn't load fake data: %s\n", e.getMessage());
        }
    }

    public void loadDataUsers(){

        var address = new Address("Congrés", "Barcelona", "08031", "Spain");
        var user = new Customer();
        user.setName("alissia");
        user.setEmail("frolova.alissia@gmail.com");
        user.setPassword(passwordEncoder.encode("Mushu0311"));
        user.setDateOfBirth(LocalDate.parse("1992-04-11"));
        user.setRole(RoleEnum.USER);
        user.setPrimaryAddress(address);
        userRepository.save(user);

    }


}
