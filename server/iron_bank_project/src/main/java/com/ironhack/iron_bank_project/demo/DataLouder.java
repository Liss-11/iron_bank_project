package com.ironhack.iron_bank_project.demo;

import com.ironhack.iron_bank_project.enums.StatusEnum;
import com.ironhack.iron_bank_project.model.Admin;
import com.ironhack.iron_bank_project.model.Customer;
import com.ironhack.iron_bank_project.model.ThirdParty;
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

        var address1 = new Address("Peralada", "Figueres", "17600", "Spain");
        var user1 = new Customer("antonio", "antonio@delcastillo.com", passwordEncoder.encode("Antonio123"),
                LocalDate.parse("1992-04-11"), address1, StatusEnum.ACTIVE);
      //  user1.setRole(RoleEnum.USER);
       // user1.setStatus(StatusEnum.ACTIVE);
        userRepository.save(user1);

        var user2 = new Admin("alissia", "frolova.alissia@gmail.com", passwordEncoder.encode("Mushu0311"));
     //   user2.setRole(RoleEnum.ADMIN);
      //  user2.setStatus(StatusEnum.ACTIVE);
        userRepository.save(user2);

        var user3 = new ThirdParty("Ara", "ara@gossa.com", passwordEncoder.encode("araLoka."), "araLoka", StatusEnum.ACTIVE);
        userRepository.save(user3);


    }


}
