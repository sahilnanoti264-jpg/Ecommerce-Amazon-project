package com.amazon.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.dto.ProductRequest;
import com.amazon.dto.ProductResponse;
import com.amazon.service.IProductService;

@RestController
@RequestMapping("product")
public class ProductController {
	private final IProductService productService;
	
	public ProductController(IProductService productService) {
		this.productService=productService;
	} 
	
	@GetMapping
	public ResponseEntity<List<ProductResponse>> getProducts() {
		return ResponseEntity.ok(productService.getAllProduct());
	}
	
	@PostMapping("/add") 
	public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) { 
		productService.insertProduct(productRequest); 
		return ResponseEntity.status(HttpStatus.CREATED).body("Product is successfully added."); 
	}
	
	@PostMapping("/addProductList")
	public ResponseEntity<String> addProductList(@RequestBody List<ProductRequest> productRequestList){
		productService.insertProductList(productRequestList);
		return ResponseEntity.status(HttpStatus.CREATED).body("Product list is successfully added."); 
	}
	
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<String> removeProduct(@PathVariable Long productId){
		return ResponseEntity.ok(productService.deleteProduct(productId));
	}
	
	@PutMapping("/update/{productId}")
	public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody ProductRequest productRequest) {
		productService.updateProduct(productId, productRequest);
		return ResponseEntity.ok("Product is successfully updated");
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId){
		
		return ResponseEntity.ok(productService.getProductById(productId));
	}
	
	@GetMapping("/pages")
	public ResponseEntity<Page<ProductResponse>> getAllProductPages( @RequestParam(defaultValue = "0") int pageNo,
	        @RequestParam(defaultValue = "10") int pageSize){
		return ResponseEntity.ok(productService.getAllProductPages(pageNo,pageSize));
	}
	
	@GetMapping("/sort/price")
	public ResponseEntity<Iterable<ProductResponse>> getProductSortByPrice(){
		return ResponseEntity.ok(productService.getProductSortByPrice());
	}
	
	@GetMapping("/sort/name")
	public ResponseEntity<Iterable<ProductResponse>> getProductSortByName(){
		return ResponseEntity.ok(productService.getEmployeesSortedByName());
	}
}
