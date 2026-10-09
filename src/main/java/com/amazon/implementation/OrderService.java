package com.amazon.implementation;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazon.dto.OrderRequest;
import com.amazon.dto.PaymentRequest;
import com.amazon.email.EmailService;
import com.amazon.email.EmailSubject;
import com.amazon.email.template.OrderEmailTemplate;
import com.amazon.entity.Cart;
import com.amazon.entity.CartItem;
import com.amazon.entity.Order;
import com.amazon.entity.OrderItem;
import com.amazon.entity.Payment;
import com.amazon.entity.User;
import com.amazon.enumeration.OrderStatus;
import com.amazon.enumeration.PaymentStatus;
import com.amazon.exception.EmailSendingException;
import com.amazon.exception.OrderNotFoundException;
import com.amazon.exception.PaymentNotFoundException;
import com.amazon.exception.UserNotFoundException;
import com.amazon.repository.ICartRepository;
import com.amazon.repository.IOrderRepository;
import com.amazon.repository.IUserRepository;
import com.amazon.service.IOrderService;
import com.amazon.service.IPaymentService;

import jakarta.mail.MessagingException;

@Service
public class OrderService implements IOrderService {

	private final IOrderRepository orderRepository;
	private final IUserRepository userRepository;
	private final EmailService emailService;
	private final OrderEmailTemplate template;
	private final IPaymentService paymentService;
	private final ICartRepository cartRepository;
	
	public OrderService(IOrderRepository orderRepository,IUserRepository userRepository,
			EmailService emailService, OrderEmailTemplate template,IPaymentService paymentService,ICartRepository cartRepository) {
		this.orderRepository=orderRepository;
		this.userRepository=userRepository;
		this.emailService=emailService;
		this.template=template;
		this.paymentService=paymentService;
		this.cartRepository=cartRepository;
	}

	@Override
	@Transactional
	public String placeOrder(OrderRequest orderRequest) throws UserNotFoundException{
	    if (orderRequest == null)
	        throw new IllegalArgumentException("Invalid order details");

	    User user = userRepository.findById(orderRequest.getUserId())
	            .orElseThrow(() -> new UserNotFoundException("User not found"));

	    Cart cart = user.getCart();
	    if (cart == null ||  cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
	        throw new IllegalStateException("Cannot place order with empty cart");
	    }

	    Order order = new Order();
	    order.setUser(user);
	    order.setOrderStatus(OrderStatus.PENDING);
	    order.setOrderItem(convertCartItemToOrderItem(cart.getCartItems(), order));
	    
	    BigDecimal total = order.getOrderItem().stream()
	            .map(oi -> BigDecimal.valueOf(oi.getPrice()).multiply(BigDecimal.valueOf(oi.getQuantity())))
	            .reduce(BigDecimal.ZERO, BigDecimal::add);
	    
	    order.setTotalAmount(total);
	    
	    Payment payment = orderRequest.getPayment();
	    if (payment == null) {
	        throw new PaymentNotFoundException("Payment details are required");
	    }
	    payment.setPaymentStatus(PaymentStatus.PENDING); // or whatever's appropriate
	   
	    payment.setAmount(total);
	   
	    order.setPayment(payment);
	    
	    Order savedOrder = orderRepository.save(order); // cascades and saves Payment too
	    cart.getCartItems().clear();
	    user.getOrder().add(savedOrder);
	    PaymentRequest paymentRequest = new PaymentRequest();
	    paymentRequest.setPaymentMethod(payment.getPaymentMethod());
	    paymentService.makePayment(savedOrder.getOrderId(), paymentRequest);
	    
	    //Email Sending
	    try {
			emailService.sentEmail(user.getEmail(), EmailSubject.ORDER_PLACED, template.build(savedOrder));

		}catch(MessagingException e) {
			throw new EmailSendingException("Unable to send registration email");
		}
		cartRepository.save(cart);
	    return "Order is successfully placed.";
	}

	@Override
	public Order getOrderById(Long orderId) throws OrderNotFoundException {
		if(orderId==null)
			throw new IllegalArgumentException("Invalid order id");
		
		return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not available."));
	}

	@Override
	public List<Order> getOrdersByUser(Long userId) throws UserNotFoundException {
		if(userId==null) 
			throw new IllegalArgumentException("Invalid user id");
		User user=userRepository.findById(userId).orElseThrow(() -> 
			new UserNotFoundException("User not found."));
		return orderRepository.findByUser(user);
	}

	@Override
	@Transactional
	public String cancelOrder(Long orderId) throws OrderNotFoundException {
		if(orderId==null)
			throw new IllegalArgumentException("Invalid order id");
		Order order=orderRepository.findById(orderId).orElseThrow(() ->
		new OrderNotFoundException("Order not available"));
		this.updateOrderStatus(orderId, OrderStatus.CANCELLED);
		return "Order is cancelled";
	}

	@Override
	@Transactional
	public void updateOrderStatus(Long orderId, OrderStatus status) throws OrderNotFoundException {
		if(orderId==null || status==null)
			throw new IllegalArgumentException("Invalid order details.");
		Order order=orderRepository.findById(orderId).orElseThrow(() ->
				new OrderNotFoundException("Order not available"));
		if (order.getOrderStatus() == OrderStatus.DELIVERED) {
		    throw new IllegalStateException(
		        "Delivered order cannot be cancelled"
		    );
		}
		order.setOrderStatus(status);
		orderRepository.save(order);
	}
	
	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public List<Order> orderHistory(Long userId) throws UserNotFoundException {
		
		return getOrdersByUser(userId);
	}

	@Override
	public OrderStatus orderStatus(Long orderId) throws OrderNotFoundException{
		if(orderId==null)
			throw new IllegalArgumentException("Invalid order id");
		Order order=orderRepository.findById(orderId).orElseThrow(() ->
				new OrderNotFoundException("Order not available"));
		
		return order.getOrderStatus();
	}
	
	private List<OrderItem> convertCartItemToOrderItem(List<CartItem> cartItem,Order order){
	return cartItem.stream().map(item ->{
		OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(item.getQuantity());
        orderItem.setPrice(item.getProduct().getPrice() * item.getQuantity());
        orderItem.setOrder(order);
        orderItem.setProduct(item.getProduct());
        return orderItem;
	}).toList();
	}

}
