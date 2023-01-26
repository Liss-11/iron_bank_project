package com.ironhack.iron_bank_project.repository;

import com.ironhack.iron_bank_project.model.CustomersToAdd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersToAddRepository extends JpaRepository<CustomersToAdd, Long> {
}
