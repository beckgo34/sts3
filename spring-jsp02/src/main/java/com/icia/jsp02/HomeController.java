package com.icia.jsp02;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		log.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@GetMapping("jstl_1")
	public String jstl_1(Model model) {
		log.info("jstl_1()");
		model.addAttribute("user", 1);
		
		List<String> sList = new ArrayList<String>();
		sList.add("아메리카노");
		sList.add("카페라떼");
		sList.add("카페모카");
		sList.add("녹차");
		sList.add("프라프치노");
		model.addAttribute("menu", sList);
		return "jstl01";
	}
	
	@GetMapping("today")
	public String today() {
		log.info("today()");
		
		return "today";
	}
	
}// class end
