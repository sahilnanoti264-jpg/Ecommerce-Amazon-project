package com.amazon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
	@NotBlank(message="Product name is required")
	private String productName;
	@NotBlank(message="category is required")
	private String category;
	@NotNull(message="Price is required")
	@Positive(message="Price must be positive")
	private Double price;
	@NotBlank(message="Description is required")
	private String description;
}
