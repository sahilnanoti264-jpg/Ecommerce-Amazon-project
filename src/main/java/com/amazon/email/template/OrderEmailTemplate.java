package com.amazon.email.template;

import org.springframework.stereotype.Component;

import com.amazon.entity.Order;

@Component
public class OrderEmailTemplate {

    public String build(Order order) {

        return """
                Hello %s,

                Your order has been placed successfully.

                Order Id : %d
                Amount   : ₹%.2f
                Status   : %s

                Thank you for shopping with us.

                Regards,
                Amazon Team
                """
                .formatted(
                        order.getUser().getFirstName(),
                        order.getOrderId(),
                        order.getTotalAmount(),
                        order.getOrderStatus()
                );
    }
}
