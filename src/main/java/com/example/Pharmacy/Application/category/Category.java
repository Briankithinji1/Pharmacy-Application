package com.example.Pharmacy.Application.category;

import com.example.Pharmacy.Application.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    private String description;
    private String imageUri;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    
    // Being used in ProductRepositoryTest 
    public Category(long categoryId, String categoryName, String description, String imageUri) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.imageUri = imageUri;
    }
}
