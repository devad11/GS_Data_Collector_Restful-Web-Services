package com.gs_data_collector.rest.webservices.restfulwebservices;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptEncoderTest {
	
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		for(int i=1; i<=10; i++) {
			String encodedString = encoder.encode("password");
			System.out.println(encodedString);
		}
	}

}
