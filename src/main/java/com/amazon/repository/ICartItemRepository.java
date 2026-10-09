package com.amazon.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amazon.entity.CartItem;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {

	public List<CartItem> findByCart_CartId(Long cartId);

	@Query("SELECT COALESCE(SUM(c.quantity), 0) FROM cartItem c WHERE c.cart.cartId = :cartId")
	public Long getTotalQuantity(@Param("cartId") Long cartId);

	@Query("""
		    SELECT COALESCE(SUM(c.product.price * c.quantity), 0)
		    FROM cartItem c
		    WHERE c.cart.cartId = :cartId
		""")
	Double calculateTotalAmount(@Param("cartId") Long cartId);

	public Optional<CartItem> findByCart_CartIdAndProduct_ProductId(
	        Long cartId,
	        Long productId
	);
	
	@Query("""
		    SELECT COALESCE(SUM(c.product.price * c.quantity), 0)
		    FROM cartItem c
		    WHERE c.cart.cartId = :cartId
		""")
    public BigDecimal totalAmount(@Param("cartId") Long cartId);
	

}