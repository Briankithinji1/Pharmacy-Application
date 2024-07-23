package main.java.com.example.Pharmacy.Application.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Veterinarian extends User {

    private String phoneNumber;
    private String address;
    private String qualification;

    @ManyToMany(mappedBy = "assignedVet")
    private List<Customer> customers;
}
