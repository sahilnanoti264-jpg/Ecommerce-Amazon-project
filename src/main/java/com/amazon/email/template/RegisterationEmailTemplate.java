package com.amazon.email.template;


import org.springframework.stereotype.Component;

import com.amazon.entity.User;

@Component
public class RegisterationEmailTemplate {
	 public String registration(User user){

	        return """
	                Hello %s,

	                Welcome to Amazon.

	                Your account has been created successfully.

	                Regards,
	                Amazon Team
	                """
	                .formatted(user.getFirstName()+" "+user.getLastName());
	    }
}
