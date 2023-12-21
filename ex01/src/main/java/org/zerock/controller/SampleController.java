package org.zerock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
	
	@RequestMapping(value="/basic", method= {RequestMethod.GET, RequestMethod.POST})
	public void basic() {
		log.info("basic.................");
	}
	
	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		log.info("basic get only get.................................");
	}
	
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info("" + dto);
		return "ex01";
	}
	
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name,
						@RequestParam("age") int age) {
		log.info("name : " + name);
		log.info("age : " + age);
		
		return "ex02";
	}
	
	// list 처리
	@GetMapping("/ex02/list")
	public String ex02List(@RequestParam("ids")ArrayList<String> ids) {
		log.info("ids : " + ids);
		return "ex02ListResult";
	}
	
	// 배열로 처리
	@GetMapping("/ex02/array")
	public String ex02Array(@RequestParam("ids") String[] ids) {
		log.info("array ids : " + Arrays.toString(ids));
		return "ex02ArrayResult";
	}
	
	// 객체리스트
	@GetMapping("ex02/bean")
	public String ex02Bean(SampleDTOList list) {
		log.info("list dtos : " + list);
		return "ex02Bean";
	}
	
	/* @DateTimeFormat을 사용하면 필요 없다.
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
	}
	 */
	
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		log.info("todo : " + todo);
		return "ex03";
	}
	
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
		log.info("dto : " + dto);
		log.info("page : " + page);
		return "/sample/ex04";
	}
	
	/*
	 * 6.4.2 RedirectAttrubutes
	 * 일회성으로 데이터를 전달하는 용도로 사
	 * Servlet에서 redirect방
	 * response.sendRedirect("/home?name=aaa&age=10");
	 * 스프링 MVC를 이용하는 redirect 처리
	 * rttr.addFlashAttribute("name", "AAA");
	 * rttr.addFlashAttribute("age", 10);
	 * 
	 * return "redirect:/";
	 * part3에서 예제 작성
	 * */
	
	// 6.5.1 void 타입
	// 해당 url의 경로를 그대로 jsp파일의 이름으로 사용하게 됩니다.
	@GetMapping("/ex05")
	public void ex05() {
		log.info("/ex05.......................");
	}
	
	/***************************************************
	 * 6.5.2 String 타입
	 * 상황에 따라 다른 화면을 보여줄 필요가 있을 경우에 유용
	 * if~else와같은 처리가 필요한 상황
	 * String 타입에 다음과 같은 별한 키워드를 붙여서 사용할 수 있다.
	 * 1. redirect : 리다이렉트 방식으로 처리하는 경우
	 * 2. forward : 포워드 방식으로 처리하는 경우
	 ***************************************************/
	
	/***************************************************
	 * 6.5.4 객체 타입
	 * Controller의 메서드 리턴 타입을 VO, DTO타입 등 복합적인 
	 * 데이터가 들어간 객체 타입으로 지정할 수 있는데, 
	 * 이 경우는 주로 JSON데이터를 만들어 내는 용도로 사용
	 ***************************************************/
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("/ex06.................");
		SampleDTO dto = new SampleDTO();
		dto.setAge(31);
		dto.setName("이택진");
		
		return dto;
	}
	
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07() {
		log.info("/ex07........................");
		
		// {"name" : "홍길동"}
		String msg = "{\"name\" : \"홍길동 \"}";
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
	
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload.....................");
	}
	
	@PostMapping("exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		
		files.forEach(file -> {
			log.info("------------------------------------------");
			log.info("name : " + file.getOriginalFilename());
			log.info("size : " + file.getSize());
		});
		
	}
	
	
}
