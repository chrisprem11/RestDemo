package com.digital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String getHomePage(Model model){
		model.addAttribute("Message", "Restful Web Services Demo.");
		return "index";
	}
}
