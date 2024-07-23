package main.java.com.example.Pharmacy.Application.user.repository;

import main.java.com.example.Pharmacy.Application.user.model.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {

    boolean existsPharmacistByUserId(Long userId);
    boolean existsPharmacistByEmail(String email);

    Optional<Pharmacist> findPharmacistsByEmail(String email);
}
