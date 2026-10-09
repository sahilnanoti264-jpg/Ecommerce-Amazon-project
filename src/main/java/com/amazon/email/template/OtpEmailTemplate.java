package com.amazon.email.template;

import org.springframework.stereotype.Component;

import com.amazon.entity.User;

@Component
public class OtpEmailTemplate {

    public String build(User user, String otp) {

        return """
                Hello %s,

                Your OTP is : %s

                This OTP is valid for 5 minutes.

                Regards,
                Amazon Team
                """
                .formatted(user.getFirstName(), otp);
    }
}