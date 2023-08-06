package com.org.project.TrainTicketingManagement.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.project.TrainTicketingManagement.domain.Client;
import com.org.project.TrainTicketingManagement.repository.ClientRepository;
import com.org.project.TrainTicketingManagement.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public Client createClient(Client client) {
		client.setPassword(passwordEncoder.encode(client.getPassword()));
		client.setRole("ROLE_USER");
		client.setRegisterDate(new Date());
		clientRepository.save(client);
		return clientRepository.save(client);
	}

	public boolean checkclientAlreadyExistByEmail(String email) {
		return clientRepository.existsByEmail(email);
	}

	@Override
	public Client getClientByEmail(String username) {
		return clientRepository.findByEmail(username);
	}

	@Override
	public Client updateClient(Client updatedClient) {
		Client existingClient = clientRepository.findById(updatedClient.getClientId()).orElse(null);
	        if (existingClient != null) {
	        	existingClient.setAddress(updatedClient.getAddress());
	        	existingClient.setContactNumber(updatedClient.getContactNumber());
	        	existingClient.setEmail(updatedClient.getEmail());

	            return clientRepository.save(existingClient);
	        }
	        return null; // or throw an exception if you want to handle it differently
	    }
}
