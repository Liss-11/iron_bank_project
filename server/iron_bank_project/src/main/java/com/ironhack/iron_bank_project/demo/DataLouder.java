package com.ironhack.iron_bank_project.demo;

import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.enums.UserStatus;
import com.ironhack.iron_bank_project.users.model.Admin;
import com.ironhack.iron_bank_project.users.model.Customer;
import com.ironhack.iron_bank_project.users.model.ThirdParty;
import com.ironhack.iron_bank_project.users.repository.ThirdPartyRepository;
import com.ironhack.iron_bank_project.users.repository.UserRepository;
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

    private final  ThirdPartyRepository thirdPartyRepository;

    private final AccountRepository accountRepository;

    @Bean
    public void loadFakeData(){
        try {
            loadDataAccounts();
        } catch (Exception e) {
            System.err.printf("Couldn't load fake data: %s\n", e.getMessage());
        }
    }

    public void loadDataUsers(){

        var address1 = new Address("Peralada", "Figueres", "17600", "Spain");
        var user1 = new Customer("antonio", "antonio@delcastillo.com", passwordEncoder.encode("Antonio123"),
                LocalDate.parse("1992-04-11"), address1, UserStatus.ACTIVE);
        userRepository.save(user1);

        var user2 = new Admin("alissia", "frolova.alissia@gmail.com", passwordEncoder.encode("Mushu0311"));
        userRepository.save(user2);

        var user3 = new ThirdParty("Ara", "ara@gossa.com", passwordEncoder.encode("araLoka."), UserStatus.ACTIVE);
        thirdPartyRepository.save(user3);
    }

    public void loadDataAccounts(){
        loadDataUsers();

    }


}
