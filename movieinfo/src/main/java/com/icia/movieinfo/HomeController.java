package com.icia.movieinfo;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.icia.movieinfo.service.MovieService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	@Autowired
	private MovieService mServ;
	
	@GetMapping("/")
	public String home(Integer pageNum, 
					   Model model, 
					   HttpSession session) {
		log.info("home()");
		
		String view = mServ.getMovieList(pageNum, model, session);
		
		return view;
	}
	
	@GetMapping("writeFrm")
	public String writeFrm() {
		log.info("writeFrm()");
		return "writeFrm";
	}
	
}// class end
