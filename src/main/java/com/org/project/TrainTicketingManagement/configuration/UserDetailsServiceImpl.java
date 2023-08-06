package com.org.project.TrainTicketingManagement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.org.project.TrainTicketingManagement.domain.Client;
import com.org.project.TrainTicketingManagement.repository.ClientRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private ClientRepository clientRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Client clientData = clientRepository.findByUsername(username);
		
		if(clientData!=null)
			return new UserInfoUserDetails(clientData);
		throw new UsernameNotFoundException("User not found" + username);
	}
}
