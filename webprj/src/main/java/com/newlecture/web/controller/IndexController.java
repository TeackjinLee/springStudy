package com.newlecture.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class IndexController implements Controller {
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("index controller");
		ModelAndView mv = new ModelAndView("root.index");
		mv.addObject("data", "Hello Spring MVC~");
//		mv.setViewName("/WEB-INF/view/index.jsp");
		return mv;
	}

}
