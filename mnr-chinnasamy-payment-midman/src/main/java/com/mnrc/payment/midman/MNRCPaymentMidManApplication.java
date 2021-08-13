package com.mnrc.payment.midman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.mnrc.core"})
public class MNRCPaymentMidManApplication {

	public static void main(String[] args) {
		SpringApplication.run(MNRCPaymentMidManApplication.class, args);
	}

}