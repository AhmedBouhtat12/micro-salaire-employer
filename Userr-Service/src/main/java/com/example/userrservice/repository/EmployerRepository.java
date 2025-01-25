package com.example.userrservice.repository;

import com.example.userrservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EmployerRepository extends JpaRepository<User, Long> {
}
