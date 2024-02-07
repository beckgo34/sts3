package com.icia.movieinfo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.icia.movieinfo.dao.MovieDao;
import com.icia.movieinfo.dto.MovieDto;
import com.icia.movieinfo.util.PagingUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MovieService {
	// DAO
	@Autowired
	private MovieDao mDao;
	
	// 영화 목록을 가져와서 컨트롤러에 넘기는 메소드
	public String getMovieList(Integer pageNum, 
							   Model model, 
							   HttpSession session) {
		log.info("getMovieList()");
		if(pageNum == null) {
			pageNum = 1; // 처음에 사이트가 열릴 때 첫페이지가 되도록 설정.
		}
		
		int listCnt = 5; // 페이지당 보여질 콘텐츠 개수
		
		Map<String, Integer> pMap = new HashMap<String, Integer>();
		pMap.put("pageNum", (pageNum - 1) * listCnt);
		pMap.put("listCnt", listCnt);
		
		List<MovieDto> mList = mDao.getMovieList(pMap);

		model.addAttribute("mList", mList);

		// 페이정 처리
		String pageHtml = getpaging(pageNum, listCnt);
		
		model.addAttribute("paging", pageHtml);
		
		return "home";
	}

	private String getpaging(Integer pageNum, Integer listCnt) {
		String pageHtml = null;
		
		// 전체 영화정보 개수
		int maxNum = mDao.cntMovie();
		// 페이지 당 보여질 번호 개수
		int pageCnt = 2;
		
		PagingUtil paging = new PagingUtil(maxNum, pageNum, 
										   listCnt, pageCnt); 

		pageHtml  = paging.makePaging();

		return pageHtml;
	}
		
	
	
} //class end
