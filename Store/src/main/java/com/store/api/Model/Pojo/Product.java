package com.store.api.Model.Pojo;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "stock")
	private int stock;
	
	@Builder.Default
	@JsonIgnore //Por alguna razon si muestro esto la app peta
	@ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
	private Set<Cart> carts = new HashSet<>();
	
	public void increaseStock(int amount) {
        stock += amount;
    }
    
    public void decreaseStock(int amount) {
        if (stock < amount) {
            throw new RuntimeException("Insufficient stock");
        }
        stock -= amount;
    }
}
