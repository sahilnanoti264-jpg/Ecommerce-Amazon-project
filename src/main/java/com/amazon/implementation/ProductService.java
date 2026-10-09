package com.amazon.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazon.dto.ProductRequest;
import com.amazon.dto.ProductResponse;
import com.amazon.entity.Product;
import com.amazon.exception.ProductNotFoundException;
import com.amazon.repository.IProductRepository;
import com.amazon.service.IProductService;

@Service("product")
public class ProductService implements IProductService {
	
	private final IProductRepository productRepository;
	
	public ProductService(IProductRepository productRepository) {
		this.productRepository=productRepository;
	}
	
	@Override
	@Transactional
	public ProductResponse insertProduct(ProductRequest productRequest) {
		if(productRequest==null)
			throw new IllegalArgumentException("Product details invalid.");
		Product product=new Product();
		BeanUtils.copyProperties(productRequest, product);
		Product savedProduct = productRepository.save(product);
		return toResponse(savedProduct);
	}
	
	@Override
	public List<ProductResponse> insertProductList(List<ProductRequest> productRequestList) {
		if(productRequestList==null || productRequestList.isEmpty()) 
			throw new IllegalArgumentException("Product list is invalid.");
		
		List<Product> productList=productRequestList.stream()
				.map(request ->{
					Product product =new Product();
					BeanUtils.copyProperties(request, product);
					return product;
				}).toList();
		
		List<Product> saveProducts = productRepository.saveAll(productList);
		
		List<ProductResponse> productResponseList=saveProducts.stream()
				.map(product -> {
					return toResponse(product);
				}).toList();
		
		return productResponseList;
	}

	@Override
	public List<ProductResponse> getAllProduct() throws ProductNotFoundException {
		List<Product> productList=productRepository.findAll();  
		if(productList.isEmpty()) throw new ProductNotFoundException("Product is not available");
		
		List<ProductResponse> productResponseList=productList.stream()
				.map(product -> {
					return toResponse(product);
				}).toList();
		return productResponseList;
	}

	@Override
	public ProductResponse getProductById(Long productId) throws ProductNotFoundException {
		if (productId == null)
	        throw new IllegalArgumentException("Invalid productId");
		Product product = productRepository.findById(productId).orElseThrow(() ->
		new ProductNotFoundException("Product not found"));
		
		return toResponse(product);
	}

	@Override
	public String deleteProduct(Long productId) throws ProductNotFoundException {
		if (productId == null)
	        throw new IllegalArgumentException("Invalid productId");
		
		Product product =productRepository.findById(productId).orElseThrow(() -> 
				new ProductNotFoundException("Product not found"));
		productRepository.delete(product);
		return "Product is deleted successfully";
	}

	@Override
	public ProductResponse updateProduct(Long productId,ProductRequest productRequest) throws ProductNotFoundException {
		if (productId == null || productRequest == null)
		        throw new IllegalArgumentException("Invalid product details");
		        
		Product product=productRepository.findById(productId).orElseThrow(() -> 
				new ProductNotFoundException("Product not found"));
		BeanUtils.copyProperties(productRequest, product);
		Product savedProduct = productRepository.save(product);
		
		return toResponse(savedProduct);
	}
	
	private ProductResponse toResponse(Product product) {
	    ProductResponse response = new ProductResponse();
	    BeanUtils.copyProperties(product, response);
	    return response;
	}

	@Override
	public Page<ProductResponse> getAllProductPages(int pageNo, int pageSize) {
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Product> pageProduct=productRepository.findAll(pageable);
		
		Page<ProductResponse> pageResponse=pageProduct.map(product ->{
			ProductResponse response=new ProductResponse();
			BeanUtils.copyProperties(product, response);
			return response;
		});
		return pageResponse;
	}

	@Override
	public Iterable<ProductResponse> getProductSortByPrice() {
		Sort sort=Sort.by(Direction.ASC,"price");
		return sortConvertToResponse(sort);
	}

	@Override
	public Iterable<ProductResponse> getEmployeesSortedByName() {
		Sort sort=Sort.by(Direction.ASC,"productName");
		return sortConvertToResponse(sort);
	}
	
	private Iterable<ProductResponse> sortConvertToResponse(Sort sort){
		Iterable<Product> iterable=productRepository.findAll(sort);
		
		List<ProductResponse> productResponseList=new ArrayList<>();
		
		for(Product product: iterable) {
			ProductResponse productResponse=new ProductResponse();
			BeanUtils.copyProperties(product, productResponse);
			productResponseList.add(productResponse);
		}
		return productResponseList;
	}
	
}
