package com.org.project.TrainTicketingManagement.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.org.project.TrainTicketingManagement.dto.ReceiptDetailsDto;

@Service
public class PDFGenerator {
	
	public Context setData(ReceiptDetailsDto receiptdetails) {
		
		Context context = new Context();
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("tkt", receiptdetails);
		
		context.setVariables(data);
		
		return context;
	}

}
