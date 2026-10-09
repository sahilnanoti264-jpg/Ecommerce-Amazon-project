package com.amazon.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.amazon.dto.ProductRequest;
import com.amazon.dto.ProductResponse;
import com.amazon.exception.ProductNotFoundException;

public interface IProductService {
	public ProductResponse insertProduct(ProductRequest productRequest);
	public List<ProductResponse> insertProductList(List<ProductRequest> productRequestList);
	public List<ProductResponse> getAllProduct() throws ProductNotFoundException;
	public ProductResponse getProductById(Long productId) throws ProductNotFoundException;
	public String deleteProduct(Long productId) throws ProductNotFoundException ;
	public ProductResponse updateProduct(Long productId,ProductRequest productRequest) throws ProductNotFoundException;
	
	//Pagination and sorting methods
	public Page<ProductResponse> getAllProductPages(int pageNo, int pageSize);
	public Iterable<ProductResponse> getProductSortByPrice();
	public Iterable<ProductResponse> getEmployeesSortedByName();
}
