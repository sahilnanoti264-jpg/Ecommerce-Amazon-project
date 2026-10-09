package com.amazon.email.template;

import org.springframework.stereotype.Component;

import com.amazon.entity.Payment;

@Component
public class PaymentEmailTemplate {

    public String build(Payment payment) {

        return """
                Hello %s,

                Your payment was successful.

                Payment Id : %d
                Amount     : ₹%.2f
                Status     : %s

                Thank you.

                Regards,
                Amazon Team
                """
                .formatted(
                        payment.getOrder().getUser(),
                        payment.getPaymentId(),
                        payment.getAmount(),
                        payment.getPaymentStatus()
                );
    }
}
