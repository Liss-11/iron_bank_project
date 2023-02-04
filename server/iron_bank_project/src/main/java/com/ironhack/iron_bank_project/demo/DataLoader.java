package com.ironhack.iron_bank_project.demo;

import com.ironhack.iron_bank_project.accounts.model.CheckingAccount;
import com.ironhack.iron_bank_project.accounts.model.CreditCardAccount;
import com.ironhack.iron_bank_project.accounts.model.SavingAccount;
import com.ironhack.iron_bank_project.accounts.model.StudentCheckingAccount;
import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.enums.UserStatus;
import com.ironhack.iron_bank_project.users.model.Admin;
import com.ironhack.iron_bank_project.users.model.Customer;
import com.ironhack.iron_bank_project.users.model.ThirdParty;
import com.ironhack.iron_bank_project.users.repository.ThirdPartyRepository;
import com.ironhack.iron_bank_project.users.repository.UserRepository;
import com.ironhack.iron_bank_project.utils.Address;
import com.ironhack.iron_bank_project.utils.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
@Profile("demo")
public class DataLoader {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final  ThirdPartyRepository thirdPartyRepository;

    private final AccountRepository accountRepository;

    @Bean
    public void loadFakeData(){
        try {
            loadDataUsersandAccounts();
        } catch (Exception e) {
            System.err.printf("Couldn't load fake data: %s\n", e.getMessage());
        }
    }

    public void loadDataUsersandAccounts(){

        var address1 = new Address("Peralada", "Figueres", "17600", "Spain");
        var user1 = new Customer("antonio", "antonio@delcastillo.com", passwordEncoder.encode("Antonio123"),
                LocalDate.parse("2003-04-11"), address1, UserStatus.ACTIVE, "Your favorite color", "Red");
        userRepository.save(user1);

        var address2 = new Address("Casstellets", "Basrcelona", "66786", "Spain");
        var user2 = new Customer("laura", "laura@castellanos.com", passwordEncoder.encode("Laura123"),
                LocalDate.parse("2000-06-15"), address2, UserStatus.ACTIVE, "Your dog's name", "Ara");
        userRepository.save(user2);

        var user3 = new Admin("alissia", "frolova.alissia@gmail.com", passwordEncoder.encode("Mushu0311"));
        userRepository.save(user3);

        var user4 = new ThirdParty("Ara", "ara@gossa.com", passwordEncoder.encode("araLoka."), UserStatus.ACTIVE);
        thirdPartyRepository.save(user4);

        //ACCOUNTS

        var account1 = new CheckingAccount(BigDecimal.valueOf(260.0), user1, user2, "Anaconda123");
        accountRepository.save(account1);

        var account2 = new StudentCheckingAccount(BigDecimal.valueOf(200.0), user1, user2, "Anaconda123");
        accountRepository.save(account2);

        var account3 = new SavingAccount(BigDecimal.valueOf(101.0), user1, user2, "Anaconda123",
                BigDecimal.valueOf(100.0), BigDecimal.valueOf(0.0025));
        accountRepository.save(account3);

        var account4 = new CreditCardAccount(BigDecimal.valueOf(100.0), user1, user2, BigDecimal.valueOf(200.0), BigDecimal.valueOf(0.1), account2);
        accountRepository.save(account4);

        account2.setBalance(new Money(BigDecimal.valueOf(-2)));
        accountRepository.save(account2);

    }



}
