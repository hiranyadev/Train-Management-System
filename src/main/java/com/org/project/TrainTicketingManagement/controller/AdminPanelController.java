package com.org.project.TrainTicketingManagement.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

import com.org.project.TrainTicketingManagement.domain.BookingDetails;
import com.org.project.TrainTicketingManagement.domain.CabinDetails;
import com.org.project.TrainTicketingManagement.domain.Carriages;
import com.org.project.TrainTicketingManagement.domain.Client;
import com.org.project.TrainTicketingManagement.domain.Station;
import com.org.project.TrainTicketingManagement.domain.Train;
import com.org.project.TrainTicketingManagement.domain.TrainClasses;
import com.org.project.TrainTicketingManagement.domain.TrainLocations;
import com.org.project.TrainTicketingManagement.domain.TrainLongPriceses;
import com.org.project.TrainTicketingManagement.domain.TrainSchedule;
import com.org.project.TrainTicketingManagement.domain.TrainTracking;
import com.org.project.TrainTicketingManagement.dto.ClientBookingDetailsDto;
import com.org.project.TrainTicketingManagement.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {
	
	@Autowired
	public AdminService adminService;
	
	@GetMapping("/")
	public String adminHome() {
		return "admin/adminHome";
	}
	
	@GetMapping("/adminManageTrains")
	public String adminManageTrains(Model model) {
		List<Train> trains = adminService.getAllTrains();
		Train train = new Train();
		model.addAttribute("newtrain", train);
		model.addAttribute("trains", trains);
		return "admin/adminManageTrains";
	}
	
	@GetMapping("/adminManageSchedules")
	public String adminManageSchedules(Model model) {
		List<TrainSchedule> scheduleList = adminService.getAllSchedulesAfterDate(new Date());
		List<Station> stations = adminService.getAllStations();
		List<Train> trains = adminService.getAllTrains();
		TrainSchedule newschedule = new TrainSchedule();
		model.addAttribute("newschedule", newschedule);
		model.addAttribute("scheduleList", scheduleList);
		model.addAttribute("stations", stations);
		model.addAttribute("trains", trains);
		return "admin/adminManageSchedules";
	}
	
	@GetMapping("/adminManageStations")
	public String adminManageStations(Model model) {
		List<Station> stations = adminService.getAllStations();
		Station station = new Station();
		model.addAttribute("newstation", station);
		model.addAttribute("stations", stations);
		return "admin/adminManageStations";
	}
	
	@GetMapping("/adminManageBooking")
	public String adminManageBooking() {
		return "admin/adminManageBooking";
	}
	
	@GetMapping("/adminManageClient")
	public String adminManageClient(Model model) {
		List<Client> clients = adminService.getAllClient();
		model.addAttribute("clients", clients);
		return "admin/adminManageClient";
	}
	
	@GetMapping("/adminReports")
	public String adminReports() {
		return "admin/adminReports";
	}
	
	@GetMapping("/adminManageClasses")
	public String adminManageClasses(Model model) {
			List<TrainClasses> trainClasses = adminService.getAllTrainClasses();
			TrainClasses trainClass = new TrainClasses();
			model.addAttribute("newclass", trainClass);
			model.addAttribute("classess", trainClasses);
		return "admin/adminManageClasses";
	}
	
	@GetMapping("/adminManageCabins")
	public String adminManageCabins(Model model) {
			List<Carriages> trainCabins = adminService.getAllTrainCabins();
			List<TrainClasses> trainClasses = adminService.getAllTrainClasses();
			List<Train> trains = adminService.getAllTrains();
			List<CabinDetails> trainCabinsDetails = new ArrayList<>();
			for(Carriages cabins : trainCabins) {
				CabinDetails trainCabin = new CabinDetails();
				trainCabin.setTrainname(cabins.getTrain().getTrainName());
				trainCabin.setCabinclass(cabins.getTrainclass().getDescription());
				trainCabin.setCarriageno(cabins.getCarriageno());
				trainCabin.setCarriageId(cabins.getCarriageId());
				trainCabinsDetails.add(trainCabin);
			}
			Carriages Carriages = new Carriages();
			model.addAttribute("trains", trains);
			model.addAttribute("cabins", trainCabinsDetails);
			model.addAttribute("newcabin", Carriages);
			model.addAttribute("classess", trainClasses);
		return "admin/adminManageCabins";
	}
	
	@GetMapping("/adminManageJourneyPrices")
	public String adminManageJourneyPrices(Model model) {
		List<Station> stations = adminService.getAllStations();
		List<TrainLongPriceses> pricesList = adminService.getAllPricess();
		List<TrainClasses> trainClasses = adminService.getAllTrainClasses();
		TrainLongPriceses newJourney = new TrainLongPriceses();
		model.addAttribute("newJourney", newJourney);
		model.addAttribute("pricesList", pricesList);
		model.addAttribute("stations", stations);
		model.addAttribute("trainsclass", trainClasses);
		return "admin/adminManageJourneyPrices";
	}
	
	@GetMapping("/adminManageLocations")
	public String adminManageLocations(Model model) {
		TrainLocations newlocation = new TrainLocations();
		List<TrainLocations> trainLocations = adminService.getAllTrainLocations();
		model.addAttribute("locationsList", trainLocations);
		model.addAttribute("newlocation", newlocation);
		return "admin/adminManageLocations";
	}
	
	@PostMapping("/addstation")
	public String addNewStation(@ModelAttribute("newstation") Station station) {
		adminService.saveStation(station);
		System.out.println(station.getStationName());
		return "redirect:/admin/adminManageStations";
	}
	
	@GetMapping("/removestation/{id}")
	public String removeStation(@PathVariable Long id) {
		adminService.deleteStationById(id);
		return "redirect:/admin/adminManageStations";
	}
	
	@PostMapping("/addtrain")
	public String addNewStation(@ModelAttribute("newtrain") Train train) {
		adminService.saveTrain(train);
		System.out.println(train.getTrainName());
		return "redirect:/admin/adminManageTrains";
	}
	
	@GetMapping("/removetrain/{id}")
	public String deleteTrain(@PathVariable Long id) {
		adminService.deleteTrainById(id);
		return "redirect:/admin/adminManageTrains";
	}
	
	@PostMapping("/addclass")
	public String addNewStation(@ModelAttribute("newclass") TrainClasses trainClasses) {
		adminService.saveTrainClass(trainClasses);
		System.out.println(trainClasses.getDescription());
		return "redirect:/admin/adminManageClasses";
	}
	
	@GetMapping("/removeclass/{id}")
	public String deleteTrainClass(@PathVariable Long id) {
		adminService.deleteTrainClassById(id);
		return "redirect:/admin/adminManageClasses";
	}
	
	@PostMapping("/addcabin")
	public String addNewCabin(@ModelAttribute("newcabin") Carriages trainCabins) {
		adminService.saveTrainCabin(trainCabins);
		System.out.println(trainCabins.getCarriageId());
		return "redirect:/admin/adminManageCabins";
	}
	
	@GetMapping("/removecabin/{id}")
	public String deleteTrainCabin(@PathVariable Long id) {
		adminService.deleteTrainCabinById(id);
		return "redirect:/admin/adminManageCabins";
	}
	
	@PostMapping("/addnewschedule")
	public String addNewSchedule(@ModelAttribute("newschedule") TrainSchedule trainSchedule) {
		trainSchedule.setStatus(1);
		adminService.saveTrainSchedule(trainSchedule);
		System.out.println(trainSchedule.getTrain().getTrainName());
		return "redirect:/admin/adminManageSchedules";
	}
	
	@GetMapping("/removeschedule/{id}")
	public String deleteschedule(@PathVariable Long id) {
		adminService.deleteScheduleById(id);
		return "redirect:/admin/adminManageSchedules";
	}
	
	@PostMapping("/addnewJourney")
	public String addNewJourney(@ModelAttribute("newJourney") TrainLongPriceses trainLongPriceses) {
		adminService.saveJourneyPrices(trainLongPriceses);
		System.out.println(trainLongPriceses.getPrice());
		return "redirect:/admin/adminManageJourneyPrices";
	}
	
	@GetMapping("/removeJourney/{id}")
	public String deleteJourney(@PathVariable Long id) {
		adminService.deleteScheduleById(id);
		return "redirect:/admin/adminManageJourneyPrices";
	}
	
	@PostMapping("/addlocation")
	public String addNewLocation(@ModelAttribute("newlocation") TrainLocations trainLocations) {
		trainLocations = adminService.saveTrainLocations(trainLocations);
		System.out.println(trainLocations.getLocationName());
		return "redirect:/admin/adminManageLocations";
	}
	
	@GetMapping("/adminLocation")
	public String adminLocations(Model model) {
		List<TrainSchedule> tsch = adminService.getAllSchedulesAfterDate(new Date());
		List<TrainLocations> trl = adminService.getAllTrainLocations();
		TrainTracking newtacking = new TrainTracking();
		model.addAttribute("tschList", tsch);
		model.addAttribute("trlhList", trl);
		model.addAttribute("newtracking", newtacking);
		return "admin/adminlocation";
	}
	
	@PostMapping("/addtracking")
	public String addTrackings(@ModelAttribute("newtracking") TrainTracking trainTracking) {
		trainTracking.setTrackingDate(new Date());
		trainTracking = adminService.saveTrainTracking(trainTracking);
		return "redirect:/admin/adminLocation";
	}
	
	@GetMapping("/adminManagePassenger")
	public String adminManagePassenger(Model model) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		List<Client> clients = adminService.getAllClient();
		
		for(Client cl : clients) {
			ClientBookingDetailsDto clientRate = adminService.getCalClientRate(cl.getClientId());
			double rate = 0.0;
			if(clientRate.getNumberoftickets() >= 1 ) {
				rate = (clientRate.getNumberoftickets() / clientRate.getSumOfPrice())*100;
			}
			cl.setRate(decimalFormat.format(rate)+" "+"%");
		}
		List<BookingDetails> bookingDetailsss = new ArrayList<BookingDetails>();
		model.addAttribute("bookingDetailsList", bookingDetailsss);
		model.addAttribute("clients", clients);
		return "admin/adminManagePassenger";
	}
	
	@GetMapping("/searchClient")
	public String searchClient(Model model, String keyword) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		List<Client> clients = adminService.getClientbyNic(keyword);
		for(Client cl : clients) {
			ClientBookingDetailsDto clientRate = adminService.getCalClientRate(cl.getClientId());
			double rate = 0.0;
			if(clientRate.getNumberoftickets() >= 1 ) {
				rate = (clientRate.getNumberoftickets() / clientRate.getSumOfPrice())*100;
			}
			cl.setRate(decimalFormat.format(rate)+" "+"%");
		}
		System.out.println(keyword);
		model.addAttribute("clients", clients);
		return "admin/adminManagePassenger";
	}
	
	@PostMapping("/bookingDetails")
	public ResponseEntity<List<ClientBookingDetailsDto>> getBookingDetails(long clientId, Model model, HttpSession session) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<BookingDetails> bookingDetails = adminService.getAllBookingDetailsClientId(clientId);
		
		List<ClientBookingDetailsDto> clList = new ArrayList<>();
		
		for(BookingDetails bkData : bookingDetails) {		
			ClientBookingDetailsDto clData = new ClientBookingDetailsDto();
			clData.setFirstName(bkData.getClient().getFirstName());
			clData.setLastName(bkData.getClient().getLastName());
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
		session.setAttribute("bookingDetailsList", bookingDetails);

		return ResponseEntity.ok().body(clList) ; 	
	}
	
	@GetMapping("/getAllTrainReport")
	public ResponseEntity<InputStreamResource> TrainReport(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=TrainReport.xlsx");
		List<Train> trains = adminService.getAllTrains();
		ByteArrayInputStream in = null;
		String[] COLUMNs = { "Train Name", "Train Type", "Train No", "Basis" };
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			Sheet sheet = workbook.createSheet("Trains");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			int rowIdx = 1;
			for (Train train : trains) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(train.getTrainName());
				row.createCell(1).setCellValue(train.getTrainType());
				row.createCell(2).setCellValue(train.getTrainNo());
				row.createCell(3).setCellValue(train.getBasis());
			}

			workbook.write(out);
			in = new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}
	
	@GetMapping("/getPassengerReport")
	public ResponseEntity<InputStreamResource> PassengerReport(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=PassengerReport.xlsx");
		List<Client> PassengerDetails = adminService.getAllPassenger();
		ByteArrayInputStream in = null;
		String[] COLUMNs = { "Passenger Name", "Address", "NIC No", "Contact No" };
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			Sheet sheet = workbook.createSheet("Trains");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			int rowIdx = 1;
			for (Client passenger : PassengerDetails) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(passenger.getFirstName() + passenger.getLastName());
				row.createCell(1).setCellValue(passenger.getAddress());
				row.createCell(2).setCellValue(passenger.getNic());
				row.createCell(3).setCellValue(passenger.getContactNumber());
			}

			workbook.write(out);
			in = new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}
	
	@GetMapping("/getScheduleReport/{fromDate}/{toDate}")
	public ResponseEntity<InputStreamResource> ScheduleReport(@PathVariable(name ="fromDate") String fromDate, @PathVariable(name ="toDate") String toDate) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
		Date tDate = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
		
		//System.out.println(fromDate+"++++++++++++++++++++++++"+toDate);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=ScheduleReport.xlsx");
		List<TrainSchedule> scheduleList = adminService.getAllSchedulesDateBetween(fDate, tDate);
		ByteArrayInputStream in = null;
		String[] COLUMNs = { "Train Name",	"Departure Station",	"Destination Station",	"Departure Time",	"Destination Time",	"Schedule Date" };
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			Sheet sheet = workbook.createSheet("Schedule");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			int rowIdx = 1;
			for (TrainSchedule schedule : scheduleList) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(schedule.getTrain().getTrainName());
				row.createCell(1).setCellValue(schedule.getDeparture().getStationName());
				row.createCell(2).setCellValue(schedule.getArrival().getStationName());
				row.createCell(3).setCellValue(schedule.getArrivalTime());
				row.createCell(4).setCellValue(schedule.getDepartTime());
				row.createCell(5).setCellValue(dateFormat.format(schedule.getScheduleDate()));
			}

			workbook.write(out);
			in = new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}

	@GetMapping("/getSaleTicketReport/{fromDate}/{toDate}")
	public ResponseEntity<InputStreamResource> SaleTicketReport(@PathVariable(name ="fromDate") String fromDate, @PathVariable(name ="toDate") String toDate) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
		Date tDate = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=SaleTicketReport.xlsx");
		List<BookingDetails> bookingDetailsList = adminService.getBookingDetails(fDate, tDate);
		ByteArrayInputStream in = null;
		String[] COLUMNs = { "Client Name",	"No of Seats",	"Destination Station",	"Departure Station", "Price", "Booking Date"};
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			Sheet sheet = workbook.createSheet("SaleTicket");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			int rowIdx = 1;
			for (BookingDetails bookingDetails : bookingDetailsList) {
				//Double totalPrice = bookingDetails.get
				Row row = sheet.createRow(rowIdx++);
				double amount = (bookingDetails.getTicket().getPrice()) * (bookingDetails.getTicket().getNumOfSeats());
				
				row.createCell(0).setCellValue(bookingDetails.getClient().getFirstName() + " " + bookingDetails.getClient().getLastName());
				row.createCell(1).setCellValue(bookingDetails.getTicket().getNumOfSeats());
				row.createCell(2).setCellValue(bookingDetails.getTicket().getDestinationStation().getStationName());
				row.createCell(3).setCellValue(bookingDetails.getTicket().getDepartureStation().getStationName());
				row.createCell(4).setCellValue(amount);
				row.createCell(5).setCellValue(dateFormat.format(bookingDetails.getBookingDate()));
			}

			workbook.write(out);
			in = new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}

	@GetMapping("/getIncomeReport/{fromDate}/{toDate}")
	public ResponseEntity<InputStreamResource> IncomeReport(@PathVariable(name ="fromDate") String fromDate, @PathVariable(name ="toDate") String toDate) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
		Date tDate = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=SaleTicketReport.xlsx");
		List<Object[]> ticketDetailsList = adminService.getTicketDetails(fDate, tDate);
		ByteArrayInputStream in = null;
		String[] COLUMNs = { "Date",	"No of Tickets",	"Amount"};
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			Sheet sheet = workbook.createSheet("Income");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			int rowIdx = 1;
			for (Object[] ticketDetails : ticketDetailsList) {
				//Double totalPrice = bookingDetails.get
				Row row = sheet.createRow(rowIdx++);
				//int tcktCountDetails = adminService.getTicketCount(ticketDetails.getTicketDate());
				double amount = Double.parseDouble(ticketDetails[2].toString());
				
				row.createCell(0).setCellValue(dateFormat.format(ticketDetails[0]));
				row.createCell(1).setCellValue(ticketDetails[1].toString());
				row.createCell(2).setCellValue(amount);
			}

			workbook.write(out);
			in = new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}
	
}
