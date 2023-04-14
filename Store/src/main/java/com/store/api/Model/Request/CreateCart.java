package com.store.api.Model.Request;

import java.util.List;

import com.store.api.Model.Pojo.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCart {
	private List<Product> products;
}
