package com.icia.jsp01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.icia.jsp01.dto.DataDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	@GetMapping("/")
	public String home(Model model) {
		log.info("home()");
		// front-end로 데이터를 보낼 때 사용하는 객체 - Model
		// DispatcherServlet이 생성하여 보내는 객체 
		model.addAttribute("data", "서버에서 보내는 문자열"); //문자열
		model.addAttribute("num", 100); //숫자(정수)
		
		DataDto dataDto = new DataDto();
		dataDto.setCode(1);
		dataDto.setStrData("홍길동");
		dataDto.setNumData(23);
		dataDto.setDateData("2000-01-05");
		model.addAttribute("person1", dataDto); // 객체(dto)
		
		return "home";
	}
	
}
