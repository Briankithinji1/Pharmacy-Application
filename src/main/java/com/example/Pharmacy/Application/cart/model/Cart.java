package main.java.com.example.Pharmacy.Application.cart.model;

import com.example.Pharmacy.Application.cart.enums.CartStatus;
import com.example.Pharmacy.Application.product.Product;
import com.example.Pharmacy.Application.user.model.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private Integer quantity;
    private Double totalPrice;
    private CartStatus status;
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Cart(Product product, Integer quantity) {
//        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.createdDate = new Date();
    }
}
