package com.org.project.TrainTicketingManagement.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

@Configuration
public class PaypalConfiguration {
	/*
	@Value("${paypal.client.id}")
	private String clientId;
	@Value("${paypal.client.secret}")
	private String clientSecret;
	@Value("${paypal.mode}")
	private String mode;*/
	
	private static final String CLIENT_ID = "AdcS5m6OaZvmTadm65jWxUsuaRt5-hSjC4A4oDcKm6RhzntH5vnPd56gNPMJTcbUAluniECbn4_rBnkn";
    private static final String CLIENT_SECRET = "ENO40_ULYF6tmDa4woudyv7zi3yyvxv8XxtwVqIF6vskbJnXTGsYt_nCcr_KjaZh5l5Em7UlgrzGbmpV";
    private static final String MODE = "sandbox";

	@Bean
	public Map<String, String> paypalSdkConfig() {
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", MODE);
		return configMap;
	}

	@Bean
	public OAuthTokenCredential oAuthTokenCredential() {
		return new OAuthTokenCredential(CLIENT_ID, CLIENT_SECRET, paypalSdkConfig());
	}

	@Bean
	public APIContext apiContext() throws PayPalRESTException {
		APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
		context.setConfigurationMap(paypalSdkConfig());
		return context;
	}

}
