package com.org.project.TrainTicketingManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetailsDto {

	private String recipient;
    private String msgBody;
    
    private String address;
    private String subject;
    private String content;
    private String name;
    private String phone;
    private String email;
   
}
