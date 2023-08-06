package com.org.project.TrainTicketingManagement.dto;

import org.springframework.core.io.InputStreamResource;

import com.org.project.TrainTicketingManagement.domain.Client;

import lombok.Data;

@Data
public class EmailMessage {
	
	private String toMail;
	private String subject;
	private String body;
	private InputStreamResource attachment;
	
	public EmailMessage(Client client) {
		this.toMail = client.getEmail();
		this.subject = " WELCOME TO EXPO TRAIN TICKETING!";
		
		StringBuffer mailBody = new StringBuffer();
		
		mailBody.append("Dear "+client.getFirstName()+",\r\n\r\n");

		mailBody.append("We are thrilled to welcome you to EXPO TRAIN TICKETING!, your one-stop solution for hassle-free train ticketing and travel management! As a valued customer, you are now part of a modern and convenient platform designed to elevate your train journey experience.\r\n");
		mailBody.append("Here at EXPO TRAIN TICKETING!, we understand the importance of seamless travel and aim to make your booking process as effortless as possible. Whether you're a frequent traveler or embarking on your first train adventure, our user-friendly interface and cutting-edge features are tailored to cater to your needs.\r\n");
		mailBody.append("Key Benefits of EXPO TRAIN TICKETING!\r\n");
		mailBody.append("Easy Online Booking: Skip the long queues at ticket counters! With just a few clicks, you can book your train tickets from the comfort of your home or on the go using our secure online portal.\r\n");
		mailBody.append("Real-Time Tracking: Stay informed at every step of your journey. Our integrated GPS technology provides real-time updates on train locations, expected arrival and departure times, and any potential delays.\r\n");
		mailBody.append("Choose Your Seat: Personalize your travel experience by selecting your preferred seats using our interactive seat map. You can even upgrade your class of travel if available!\r\n");
		mailBody.append("Safe and Secure Transactions: Your security is our top priority. We partner with trusted payment gateways to ensure that your online transactions are safe and seamless.\r\n");
		mailBody.append("Prompt Customer Support: Have questions or need assistance? Our friendly customer support team is here to help you. Feel free to reach out to us anytime.\r\n");
		mailBody.append("Environmentally Conscious: Join us in our commitment to the environment. By choosing digital ticketing, you are contributing to a greener and more sustainable way of traveling.\r\n");
		mailBody.append("We are confident that EXPO TRAIN TICKETING! will enhance your travel experience and make your journeys more enjoyable. Thank you for choosing us as your preferred platform for train ticketing and management.\r\n");
		mailBody.append("Should you have any feedback, suggestions, or inquiries, please don't hesitate to contact us. We value your input and are continuously striving to improve our services.\r\n");
		mailBody.append("Welcome aboard! Get ready to embark on a seamless and delightful train travel experience with EXPO TRAIN TICKETING!\r\n\r\n\r\n");

		mailBody.append("Safe travels!\r\n");
		mailBody.append("Best regards,\r\n");
		mailBody.append("EXPO TRAIN TICKETING!\r\n");
		mailBody.append("+94-00-000-00-00\r\n");
		
		this.body = mailBody.toString();
	}

	public EmailMessage(String email, InputStreamResource inputStreamResource) {
		this.toMail = email;
		this.subject = " EXPO TRAIN TICKET";
		
		StringBuffer mailBody = new StringBuffer();
		
		mailBody.append("Dear Traveler,\r\n\r\n");

		mailBody.append("We are thrilled to welcome you to EXPO TRAIN TICKETING!, your one-stop solution for hassle-free train ticketing and travel management! As a valued customer, you are now part of a modern and convenient platform designed to elevate your train journey experience.\r\n");
		mailBody.append("Here at EXPO TRAIN TICKETING!, we understand the importance of seamless travel and aim to make your booking process as effortless as possible. Whether you're a frequent traveler or embarking on your first train adventure, our user-friendly interface and cutting-edge features are tailored to cater to your needs.\r\n");
		mailBody.append("Key Benefits of EXPO TRAIN TICKETING!\r\n");
		mailBody.append("Easy Online Booking: Skip the long queues at ticket counters! With just a few clicks, you can book your train tickets from the comfort of your home or on the go using our secure online portal.\r\n");
		mailBody.append("Real-Time Tracking: Stay informed at every step of your journey. Our integrated GPS technology provides real-time updates on train locations, expected arrival and departure times, and any potential delays.\r\n");
		mailBody.append("Choose Your Seat: Personalize your travel experience by selecting your preferred seats using our interactive seat map. You can even upgrade your class of travel if available!\r\n");
		mailBody.append("Safe and Secure Transactions: Your security is our top priority. We partner with trusted payment gateways to ensure that your online transactions are safe and seamless.\r\n");
		mailBody.append("Prompt Customer Support: Have questions or need assistance? Our friendly customer support team is here to help you. Feel free to reach out to us anytime.\r\n");
		mailBody.append("Environmentally Conscious: Join us in our commitment to the environment. By choosing digital ticketing, you are contributing to a greener and more sustainable way of traveling.\r\n");
		mailBody.append("We are confident that EXPO TRAIN TICKETING! will enhance your travel experience and make your journeys more enjoyable. Thank you for choosing us as your preferred platform for train ticketing and management.\r\n");
		mailBody.append("Should you have any feedback, suggestions, or inquiries, please don't hesitate to contact us. We value your input and are continuously striving to improve our services.\r\n");
		mailBody.append("Welcome aboard! Get ready to embark on a seamless and delightful train travel experience with EXPO TRAIN TICKETING!\r\n\r\n\r\n");

		mailBody.append("Safe travels!\r\n");
		mailBody.append("Best regards,\r\n");
		mailBody.append("EXPO TRAIN TICKETING!\r\n");
		mailBody.append("+94-00-000-00-00\r\n");
		
		this.body = mailBody.toString();
		this.attachment = inputStreamResource;
	}

}
