package com.org.project.TrainTicketingManagement.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.org.project.TrainTicketingManagement.domain.BookingDetails;
import com.org.project.TrainTicketingManagement.domain.Carriages;
import com.org.project.TrainTicketingManagement.domain.Client;
import com.org.project.TrainTicketingManagement.domain.Payments;
import com.org.project.TrainTicketingManagement.domain.SearchContents;
import com.org.project.TrainTicketingManagement.domain.SeatArrangement;
import com.org.project.TrainTicketingManagement.domain.Station;
import com.org.project.TrainTicketingManagement.domain.Tickets;
import com.org.project.TrainTicketingManagement.domain.Train;
import com.org.project.TrainTicketingManagement.domain.TrainLocations;
import com.org.project.TrainTicketingManagement.domain.TrainSchedule;
import com.org.project.TrainTicketingManagement.domain.TrainTracking;
import com.org.project.TrainTicketingManagement.dto.BookingDetailsDto;
import com.org.project.TrainTicketingManagement.dto.BookingHistoryDto;
import com.org.project.TrainTicketingManagement.dto.ClientBookingDetailsDto;
import com.org.project.TrainTicketingManagement.dto.EmailDetailsDto;
import com.org.project.TrainTicketingManagement.dto.ReceiptDetailsDto;
import com.org.project.TrainTicketingManagement.service.AdminService;
import com.org.project.TrainTicketingManagement.service.ClientService;
import com.org.project.TrainTicketingManagement.service.CustomerPanelService;
import com.org.project.TrainTicketingManagement.serviceImpl.PaypalService;
import com.org.project.TrainTicketingManagement.util.EmailService;
import com.org.project.TrainTicketingManagement.util.PDFGenerator;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
@RequestMapping("/client")
public class CustomerPanelController {
	
	public static final String SUCCESS_URL = "client/receipt";
	public static final String CANCEL_URL = "client/error";
	
	@Autowired
	public CustomerPanelService customerPanelService;
	
	@Autowired
	public ClientService clientService;
	
	@Autowired
	public PaypalService service;
	
	@Autowired
	public PDFGenerator pdfGenerator;
	
	@Autowired
	public SpringTemplateEngine springTemplateEngine;
	
	@Autowired
	public AdminService adminService;
	
	@Autowired
	public EmailService emailService;	
	
	@ModelAttribute
	private void loginUserDetails(Model model, Principal principal, HttpSession session) {
		String username = principal.getName();
		Client client = clientService.getClientByEmail(username);
		model.addAttribute("client", client);
		session.setAttribute("userconfig", client);
	}
	
	@GetMapping("/")
	public String home(Model model, HttpSession session, Principal principle) {
		Client client = (Client) session.getAttribute("userconfig");
		client = (Client) model.getAttribute("client");
		List<Station> allStations = customerPanelService.getAvailableStaions();
		model.addAttribute("stations", allStations);
		model.addAttribute("bookingData", new SearchContents());
		session.setAttribute("client", client);
		return "client/home";
	}	
	
	@PostMapping("/searchData")
	public String getAvailableTrainForTheDate(@ModelAttribute("bookingData") SearchContents searchContents, HttpSession session, Model model) {
		System.out.println("AAAAAAAA"+searchContents);
		if(searchContents.getDepartureDate()!=null && searchContents.getDepartureStation()!=null && searchContents.getDestinationStation()!=null) {
			List<TrainSchedule> availableTrains = customerPanelService.getAvailableTrain(searchContents);
			model.addAttribute("trainSchedules", availableTrains);
			
			if(!availableTrains.isEmpty()) {
				return "client/searchtrain";
			}			
		}
		session.setAttribute("error", "There are no any trains between the selected station for the selected date");
		return "redirect:/client/";		
	}
	
	@GetMapping("/booking/{id}")
	public String bookingPage(@PathVariable Long id, Model model, HttpSession session) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Train train = new Train();
		Client client = (Client) session.getAttribute("client");
		TrainSchedule trainSchedule = customerPanelService.getTrainScheduleById(id);
		train = trainSchedule.getTrain();
		List<Carriages> trainClasses = trainSchedule.getTrain().getCarriages();
		BookingDetailsDto bookingDetailsDto = new BookingDetailsDto();
		bookingDetailsDto.setClientName(client.getFirstName()+" "+client.getLastName());
		bookingDetailsDto.setNic(client.getNic());
		bookingDetailsDto.setTrainName(train.getTrainName());
		bookingDetailsDto.setTrainNumber(train.getTrainNo());
		bookingDetailsDto.setDeparturStation(trainSchedule.getDeparture().getStationName());
		bookingDetailsDto.setDestinationStation(trainSchedule.getArrival().getStationName());
		bookingDetailsDto.setBookingDate(dateFormat.format(trainSchedule.getScheduleDate()));
		bookingDetailsDto.setDeparturStationId(trainSchedule.getDeparture().getStationId());
		bookingDetailsDto.setDestinationStationId(trainSchedule.getArrival().getStationId());
		bookingDetailsDto.setScheduleId(trainSchedule.getScheduleId());
		bookingDetailsDto.setClientId(client.getClientId());
		
		List<Integer> booked = new ArrayList<>();
		model.addAttribute("booked", booked);
		model.addAttribute("trainClasses", trainClasses);
		model.addAttribute("bookDetails", bookingDetailsDto);
		session.setAttribute("bookingDetails", bookingDetailsDto);
		return "client/booking";
	}
	
	@PostMapping("/selectSeatingOption")
	public String selectSeatingOption(@RequestParam(name ="scheduleid") Long scheduleid, Model model, 
			@RequestParam(name ="newarriages") Long cabinid, HttpSession session) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Train train = new Train();
		Client client = (Client) session.getAttribute("client");
		TrainSchedule trainSchedule = customerPanelService.getTrainScheduleById(scheduleid);
		train = trainSchedule.getTrain();
		List<Carriages> trainClasses = new ArrayList<>();
		Carriages crg = new Carriages();
		for(Carriages carr : trainSchedule.getTrain().getCarriages()) {
			if(cabinid == carr.getCarriageId()) {
				crg = carr;
				trainClasses.add(carr);
			}
		}
		
		BookingDetailsDto bookingDetailsDto = new BookingDetailsDto();
		bookingDetailsDto.setClientName(client.getFirstName()+" "+client.getLastName());
		bookingDetailsDto.setNic(client.getNic());
		bookingDetailsDto.setTrainName(train.getTrainName());
		bookingDetailsDto.setTrainNumber(train.getTrainNo());
		bookingDetailsDto.setDeparturStation(trainSchedule.getDeparture().getStationName());
		bookingDetailsDto.setDestinationStation(trainSchedule.getArrival().getStationName());
		bookingDetailsDto.setBookingDate(dateFormat.format(trainSchedule.getScheduleDate()));
		bookingDetailsDto.setDeparturStationId(trainSchedule.getDeparture().getStationId());
		bookingDetailsDto.setDestinationStationId(trainSchedule.getArrival().getStationId());
		bookingDetailsDto.setScheduleId(trainSchedule.getScheduleId());
		bookingDetailsDto.setClientId(client.getClientId());
		
		List<String> seatrows = new ArrayList<String>();
		List<SeatArrangement> seat = client.getSeat();		
		List<SeatArrangement> all = customerPanelService.getAllBookedSeatsBycabinAndSchedule(crg, trainSchedule);
		
		for (SeatArrangement s : all) {
			for (String s1 : s.getSeatNo()) {
				seatrows.add(s1);
			}
		}
		
		List<Integer> booked = new ArrayList<>();
		model.addAttribute("booked", booked);
		model.addAttribute("trainClasses", trainClasses);
		model.addAttribute("bookDetails", bookingDetailsDto);
		session.setAttribute("bookingDetails", bookingDetailsDto);
		model.addAttribute("seats", seatrows);
		model.addAttribute("seat", seat);

		return "client/reserveseats";
		
	}
	
	@PostMapping("/recervedBooking")
	public String recervedBooking(@RequestParam(name ="newarriages") Long cabinid, @ModelAttribute("seat") SeatArrangement seatArrangement, 
									@ModelAttribute("bookDetails") BookingDetailsDto bookingDetailsDto, HttpSession session, Model model ) throws ParseException {
		Client client = (Client) session.getAttribute("client");
		bookingDetailsDto = (BookingDetailsDto) session.getAttribute("bookingDetails");
		System.out.println("model"+seatArrangement.getSeatNo());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date bookingDate = new SimpleDateFormat("yyyy-MM-dd").parse(bookingDetailsDto.getBookingDate());
		
		if(seatArrangement.getSeatNo()==null) {
			return "redirect:/client/booking/"+bookingDetailsDto.getScheduleId();
		}
		
		Carriages bookingCarriage = customerPanelService.getBookingCabineById(cabinid);
		TrainSchedule bookingSchedule = customerPanelService.getTrainScheduleById(bookingDetailsDto.getScheduleId());
		
		seatArrangement.setClient(client);
		seatArrangement.setClassType(bookingCarriage);
		seatArrangement.setTrainSchedule(bookingSchedule);
		
		
		
		Tickets bookingtickets = new Tickets();
		bookingtickets.setTicketNumber("EXPOTCKT"+bookingDetailsDto.getBookingDate()+client.getClientId());
		bookingtickets.setNumOfSeats(seatArrangement.getSeatNo().size());
		bookingtickets.setPrice(bookingCarriage.getJourneyprice().getPrice());
		bookingtickets.setClient(client);
		bookingtickets.setDepartureDate(bookingDate);
		bookingtickets.setDepartureStation(customerPanelService.getStationById(bookingDetailsDto.getDeparturStationId()));
		bookingtickets.setDestinationStation(customerPanelService.getStationById(bookingDetailsDto.getDestinationStationId()));
		bookingtickets.setCancelled(false);
		bookingtickets.setCancelationStatus("A");
		bookingtickets.setCancelDate(null);
		bookingtickets.setTicketDate(new Date());
		bookingtickets.setSeat(seatArrangement);
		bookingtickets = customerPanelService.createTicketBooking(bookingtickets);
		
		seatArrangement.setTicket(bookingtickets);
		seatArrangement = customerPanelService.createSeateArrangement(seatArrangement);
		
		BookingDetails bookingDetails = new BookingDetails();
		bookingDetails.setClient(client);
		bookingDetails.setShedule(bookingSchedule);
		bookingDetails.setTicket(bookingtickets);
		bookingDetails.setBookingDate(bookingDate);		
		bookingDetails = customerPanelService.createBookingDetails(bookingDetails);
		
		double totalAmount = bookingCarriage.getJourneyprice().getPrice()*seatArrangement.getSeatNo().size();
		
		Payments ticketPayments = new Payments();
		ticketPayments.setCurrency("USD");
		ticketPayments.setTickets(bookingtickets);
		ticketPayments.setMethod("PayPal");
		ticketPayments.setPayDate(new Date());
		ticketPayments.setPaymentAmount(totalAmount);
		ticketPayments.setIntent("SALE");
		ticketPayments.setDescription("Ticket No: "+bookingtickets.getTicketNumber()+" payment "+dateFormat.format(new Date()));
		ticketPayments = customerPanelService.createPayemnts(ticketPayments);
		
		bookingDetailsDto.setOthers("Ticket No: "+bookingtickets.getTicketNumber()+" || Cabin Class: "+seatArrangement.getClassType().getTrainclass().getDescription()+" || Recerved Seats: "+seatArrangement.allSeatsRecerved(seatArrangement)+" || Total Ticket Price: "+totalAmount);
		model.addAttribute("bookDetails", bookingDetailsDto);
		model.addAttribute("paymentDetails", ticketPayments);
		
		return "/client/viewbooking";
	}
	
	@GetMapping("/viewbookinghistory")
	private String viewBooking(HttpSession session, Model model) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Client client = (Client) session.getAttribute("client");		
		List<Tickets> bookingtickets = customerPanelService.getAllTicketsByClient(client);
		Payments ticketPayments = null;
		List<BookingHistoryDto> bookingHistoryList = new ArrayList<>();
		for(Tickets tickets : bookingtickets) {
			BookingHistoryDto bookingHistory = new BookingHistoryDto();
			bookingHistory.setTicketNo(tickets.getTicketNumber());
			bookingHistory.setBookingDate(dateFormat.format(tickets.getDepartureDate()));
			bookingHistory.setDepStation(tickets.getDepartureStation().getStationName());
			bookingHistory.setDesStation(tickets.getDestinationStation().getStationName());
			bookingHistory.setTotalPrice(String.valueOf(tickets.getNumOfSeats()*tickets.getPrice()));
			if(tickets.getCancelationStatus().equals("A")) {
				bookingHistory.setTicketStatus("Active");
			}else {
				bookingHistory.setTicketStatus("Canceled");
			}
			
			String paysts = customerPanelService.getPaymentStatusByTicket(tickets);
			if("P".equals(paysts)) {
				bookingHistory.setPayStatus("Payed");
			}else {
				bookingHistory.setPayStatus("Not Payed");
			}
			bookingHistory.setTicketId(tickets.getTicketId());
			bookingHistoryList.add(bookingHistory);
		}
		
		model.addAttribute("paymentDetails", ticketPayments);
		model.addAttribute("bookingHistoryList", bookingHistoryList);
		
		return "/client/viewbookinghistory";
	}
	
	@GetMapping("/ticketcancel/{ticketid}")
	public String cancelPayment(@PathVariable Long ticketid) {
		Tickets tk = customerPanelService.getTicketById(ticketid);
		
		SeatArrangement seat = tk.getSeat();
		seat.setStatus(0);
		customerPanelService.removeSeats(seat);
		
		tk.setCancelDate(new Date());
		tk.setCancelationStatus("C");
		tk = customerPanelService.createTicketBooking(tk);
		return "redirect:/client/viewbookinghistory";		
	}
	
	@PostMapping("/payment")
	public String payment(@RequestParam(name="paymentDetailsId") Long paymentId) {
		Payments payments = customerPanelService.getPaymentsById(paymentId);
		try {
			Payment payment = service.createPayment(payments.getPaymentAmount(), payments.getCurrency(), payments.getMethod(),
					payments.getIntent(), payments.getDescription(), "http://localhost:8080/" + CANCEL_URL,
					"http://localhost:8080/" + SUCCESS_URL);
			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {
					return "redirect:"+link.getHref();
				}
			}
			if(payment!=null) {
				payments.setPaymentStatus("P"); // P - Payed, N - Not Payed
				customerPanelService.createPayemnts(payments);
			}
		} catch (PayPalRESTException e) {
		
			e.printStackTrace();
		}
		return "redirect:/client/";
	}
	
	@GetMapping("/payment/{ticketid}")
	public String paymentticket(@PathVariable Long ticketid) {
		Tickets tk = customerPanelService.getTicketById(ticketid);
		Payments payments = customerPanelService.getPaymentsByTicket(tk);
		try {
			Payment payment = service.createPayment(payments.getPaymentAmount(), payments.getCurrency(), payments.getMethod(),
					payments.getIntent(), payments.getDescription(), "http://localhost:8080/" + CANCEL_URL,
					"http://localhost:8080/" + SUCCESS_URL+"/"+ticketid);
			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {
					return "redirect:"+link.getHref();
				}
			}
		} catch (PayPalRESTException e) {
		
			e.printStackTrace();
		}
		return "redirect:/client/";
	}
	
	@GetMapping(value = CANCEL_URL)
	public String cancelPay() {
		return "redirect:/client/viewbookinghistory";
	}
	
	@GetMapping("/receipt/{ticketid}")
	private String paymentReceipt(@PathVariable Long ticketid, Model model) {
		Tickets tk = customerPanelService.getTicketById(ticketid);
		Payments payments = customerPanelService.getPaymentsByTicket(tk);
		
		if(payments!=null) {
			payments.setPaymentStatus("P"); // P - Payed, N - Not Payed
			customerPanelService.createPayemnts(payments);
		}
		ReceiptDetailsDto ticketDetails = customerPanelService.getReceiptDetails(ticketid);

		model.addAttribute("tkt", ticketDetails);
		
		return "/client/receipt";
	}
	
	@GetMapping(value = "/generate/document/{ticketid}")
	public ResponseEntity<InputStreamResource> generatePDFDocument(@PathVariable Long ticketid) {
		
		ReceiptDetailsDto ticketDetails = customerPanelService.getReceiptDetails(ticketid);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename="+ticketDetails.getTicketnumber()+".pdf");
		
		String finalHtml = null;
		
		Context dataContext = pdfGenerator.setData(ticketDetails);
		
		finalHtml = springTemplateEngine.process("client/printreceipt", dataContext);
		
		ByteArrayInputStream in = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			
			PdfWriter pdfwriter = new PdfWriter(byteArrayOutputStream);
			
			DefaultFontProvider defaultFont = new DefaultFontProvider(false, true, false);
			
			ConverterProperties converterProperties = new ConverterProperties();
			
			converterProperties.setFontProvider(defaultFont);
			
			HtmlConverter.convertToPdf(finalHtml, pdfwriter, converterProperties);
			in = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			
			byteArrayOutputStream.close();
			
			byteArrayOutputStream.flush();
			
		} catch(Exception ex) {
			
			//exception occured
		}		
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}
	
	@GetMapping("/liveLocation")
	public String trainLocationHistory(Model model) {
		List<TrainSchedule> tschList = adminService.getAllSchedulesAfterDate(new Date());
		model.addAttribute("allschedule", tschList);
		return "/client/clientlocation";
	}
	
	@PostMapping("/getCurrentLocation")
	public ResponseEntity<TrainLocations> getCurrentLocation(Long scheduleid) {
		TrainSchedule tsch = customerPanelService.getTrainScheduleById(scheduleid);		
		List<TrainTracking> x = customerPanelService.geCuttentLocation(tsch);		
		TrainTracking maxTrack = x.get(0);
		
		TrainLocations locate = maxTrack.getLocation();
		
		return ResponseEntity.ok().body(locate);
	}
	
	@GetMapping("/information")
	public String informationPage(Model model, HttpSession session) {
		try {
			Client client = (Client) session.getAttribute("userconfig");
			model.addAttribute("client", client);		
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			List<BookingDetails> bookingDetails = adminService.getAllBookingDetailsClientId(client.getClientId());
			
			List<ClientBookingDetailsDto> clList = new ArrayList<>();
			
			for(BookingDetails bkData : bookingDetails) {		
				ClientBookingDetailsDto clData = new ClientBookingDetailsDto();
				
				clData.setFirstName(bkData.getClient().getFirstName());
				clData.setLastName(bkData.getClient().getLastName());
				clData.setFullName(bkData.getClient().getFirstName().concat(" ").concat(bkData.getClient().getLastName()));
				clData.setNic(bkData.getClient().getNic());
				clData.setDepatureDate(dateFormat.format(bkData.getTicket().getDepartureDate()));
				clData.setNumOfSeats(bkData.getTicket().getNumOfSeats());
				clData.setPrice(bkData.getTicket().getPrice());
				clData.setDeparturStation(bkData.getTicket().getDepartureStation().getStationName());
				clData.setArrivalStation(bkData.getTicket().getDestinationStation().getStationName());
				clData.setBookingDate(dateFormat.format(bkData.getTicket().getTicketDate()));
				clList.add(clData);
			}		
			model.addAttribute("bookingDetailsList", clList);
			return "client/information";
			
		}catch (Exception e) {
			return "client/information";
		}
		
	}
	
	@GetMapping("/contactUs")
	public String contactUsPage(@ModelAttribute("contact") EmailDetailsDto contact) {
		return "client/contactUs";
	}
	
	@PostMapping("/send")
    public String send(@ModelAttribute("contact") EmailDetailsDto contact, Model model) throws MessagingException{
		try {
			String content = "Name : " + contact.getName();
	         content += "<br>Phone : " + contact.getPhone();
	         content += "<br>Address : " + contact.getAddress();
	         content += "<br>Email : " + contact.getEmail();
	         content += "<br>Subject : " + contact.getSubject();
	         content += "<br>Content : " + contact.getContent();
	         customerPanelService.send(contact.getEmail(), "kasunprabath5652@gmail.com", contact.getSubject(), content);
	         model.addAttribute("contact", contact);
	         model.addAttribute("msg", "Done!");
	         
	         String contentFeedback = "Hi : " + contact.getName();
	         contentFeedback += "<br>We will Contact You quickly : ";
	         contentFeedback += "<br>Thank You. ";
	         String subjectFeedBack = "Train Ticket Booking";
	         customerPanelService.sendFeedback("kasunprabath5652@gmail.com",contact.getEmail(), subjectFeedBack, contentFeedback);
	         System.out.println(contact.getEmail());	
	         System.out.println(contact.getName());	
	         System.out.println();	
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
		}
         
        return "client/contactUs";
    }
	
	@PostMapping("/updateClient")
    public String updateClient(@ModelAttribute("client") Client updatedClient) { 
		Client updated = clientService.updateClient(updatedClient);
        if (updated != null) {
            return "redirect:/client/information";
        } 
        return "redirect:/client/information";
    }
	
}
