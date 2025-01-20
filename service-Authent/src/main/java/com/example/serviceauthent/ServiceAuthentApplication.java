package com.example.serviceauthent;

import com.example.serviceauthent.model.Account;
import com.example.serviceauthent.model.Role;
import com.example.serviceauthent.repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing
public class ServiceAuthentApplication {

    public ServiceAuthentApplication(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthentApplication.class, args);
    }
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner run(UserRepo utilisateurRepository) {
        return args -> {
            // Vérifier si un utilisateur par défaut existe déjà
            if (utilisateurRepository.findByEmail("admin@example.com").isEmpty()) {
                // Créer un utilisateur admin par défaut
                Account admin = Account.builder()
                        .id(UUID.randomUUID())
                        .email("admin@example.com")
                        .password(passwordEncoder.encode("password")) // Mot de passe encodé
                        .role(Role.EMPLOYER) // Assurez-vous que le rôle ADMIN existe
                        .build();

                utilisateurRepository.save(admin);
                System.out.println("Utilisateur administrateur créé : admin@example.com / password");
            }
        };
    }

}
