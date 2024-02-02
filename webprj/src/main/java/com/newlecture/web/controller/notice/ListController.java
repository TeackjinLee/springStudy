package com.newlecture.web.controller.notice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;
import com.newlecture.web.service.jdbc.JDBCNoticeService;

public class ListController implements Controller {
	
	private NoticeService noticeSerivce; 
	
	public void setNoticeService(NoticeService noticeSerivce) {
		this.noticeSerivce = noticeSerivce;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		ModelAndView mv = new ModelAndView("notice.list");
		
		List<Notice> list = noticeSerivce.getList(1, "TITLE", "");
		mv.addObject("list", list);
		
		return mv;
	}
	
}
