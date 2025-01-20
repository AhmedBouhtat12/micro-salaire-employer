package com.example.serviceauthent.repository;

import com.example.serviceauthent.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Account, Integer>
{
    Optional<Account> findByEmail(String email);
}
