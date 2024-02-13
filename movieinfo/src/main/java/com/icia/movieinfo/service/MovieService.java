package com.icia.movieinfo.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String getMovieList(Integer pageNum, Model model, HttpSession session) {
		log.info("getMovieList()");
		if (pageNum == null) {
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

		PagingUtil paging = new PagingUtil(maxNum, pageNum, listCnt, pageCnt);

		pageHtml = paging.makePaging();

		return pageHtml;
	}

	public String insertMovie(List<MultipartFile> files, 
								MovieDto movie, 
								HttpSession session, 
								RedirectAttributes rttr) {
		log.info("insertMovie()");
		String msg = null;
		String view = null;
		String upFile = files.get(0).getOriginalFilename();

		try {
			if (!upFile.equals("")) {
				fileUpload(files, session, movie);
			}
			mDao.insertMovie(movie);
			view = "redirect:/?pageNum=1";
			msg = "작성 성공";
		} catch (Exception e) {
			e.printStackTrace();
			view = "redirect:writeFrm";
			msg = "작성 실패";
		}

		rttr.addFlashAttribute("msg", msg);
		return view;
	}

	private void fileUpload(List<MultipartFile> files, 
							HttpSession session, 
							MovieDto movie) throws Exception {
		log.info("fileUpload()");

		String sysname = null;
		String oriname = null;

		String realPath = session.getServletContext().getRealPath("/");
		log.info(realPath);
		realPath += "resources/upload/";
		File folder = new File(realPath);
		//isDirectory() : 해당 이름이 폴더가 아니거나 존재하지 않으면 false
		if(folder.isDirectory() == false) {
			folder.mkdir();// 폴더 생성 메소드
		}
		
		MultipartFile mf = files.get(0);
		oriname = mf.getOriginalFilename();
		
		sysname = System.currentTimeMillis()
				+ oriname.substring(oriname.lastIndexOf("."));
		
		File file = new File(realPath + sysname);
		
		mf.transferTo(file);// 하드디스크(경로상의 폴더)에 저장
		movie.setP_sysname(sysname);
	}
	
	// 상세보기 처리 메소드(수정처리에서도 활용)
	public void getMovie(Integer m_code, Model model) {
		log.info("getMovie()");
		// DB에서 데이터 가져오기
		MovieDto movie = mDao.selectMovie(m_code);
		//model에 담기
		model.addAttribute("movie", movie);
	}

	public String movieUpdate(List<MultipartFile> files, 
							  MovieDto movie, 
							  HttpSession session, 
							  RedirectAttributes rttr) {
		log.info("movieUpdate()");
		String msg = null;
		String view = null;
		String poster = movie.getP_sysname();// 기존 파일(포스터)
		
		try {
			if(!files.get(0).isEmpty()) {
				fileUpload(files, session, movie);
				
				// 기존 파일 삭제(포스터 삭제)
				if(poster != null) {
					fileDelete(poster, session);
				}
			}
			mDao.updateMovie(movie);
			
			view = "redirect:detail?m_code=" + movie.getM_code();
			msg = "수정 성공";
			
		} catch (Exception e) {
			e.printStackTrace();
			view = "redirect:updateFrm?m_code=" + movie.getM_code();
			msg = "수정 실패";
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}

	private void fileDelete(String poster, 
							HttpSession session) 
									throws Exception{
		log.info("fileDelete()");
		
		String realPath = session.getServletContext()
					.getRealPath("/");
		realPath += "resources/upload/" + poster;
		File file = new File(realPath);
		if(file.exists()) {
			file.delete();
		}
		
	}

	public String delete(Integer m_code, 
						 HttpSession session, 
						 RedirectAttributes rttr) {
		String view = null;
		String msg = null;
		
		MovieDto movie = mDao.selectMovie(m_code);
		String poster= movie.getP_sysname();
		
		try {
			if(poster != null) {
				fileDelete(poster, session);
			}
			mDao.delete(m_code);
			msg = "삭제 성공";
			view = "redirect:/?pageNum=1";
		} catch (Exception e){
			e.printStackTrace();
			msg = "삭제 실패";
			view = "redirect:detil?m_code" + m_code;
		}
		rttr.addAttribute("msg", msg);
		return view;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

} // class end
