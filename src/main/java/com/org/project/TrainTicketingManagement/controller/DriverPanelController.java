package com.org.project.TrainTicketingManagement.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.org.project.TrainTicketingManagement.domain.TrainLocations;
import com.org.project.TrainTicketingManagement.domain.TrainSchedule;
import com.org.project.TrainTicketingManagement.domain.TrainTracking;
import com.org.project.TrainTicketingManagement.service.AdminService;

@Controller
@RequestMapping("/driver")
public class DriverPanelController {
	
	@Autowired
	public AdminService adminService;
	
	@GetMapping("/")
	public String driverHome() {
		return "driver/driverlocation";
	}
	
	@GetMapping("/driverLocation")
	public String driverLocations(Model model) {
		List<TrainSchedule> tsch = adminService.getAllSchedulesAfterDate(new Date());
		List<TrainLocations> trl = adminService.getAllTrainLocations();
		TrainTracking newtacking = new TrainTracking();
		model.addAttribute("tschList", tsch);
		model.addAttribute("trlhList", trl);
		model.addAttribute("newtracking", newtacking);
		return "/driver/driverlocation";
	}
	
	@PostMapping("/addtracking")
	public String addTrackings(@ModelAttribute("newtracking") TrainTracking trainTracking) {
		trainTracking.setTrackingDate(new Date());
		trainTracking = adminService.saveTrainTracking(trainTracking);
		return "redirect:/driver/driverlocation";
	}

}
