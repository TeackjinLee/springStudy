package org.zerock.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class UploadController {
	
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}
	
	// 21.2.1 MultipartFile 타입
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
				
		String uploadFolder = "/Users/itaegjin/git/file/upload";
		
		for (MultipartFile multipartFile : uploadFile) {
			log.info("---------------------------------------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
//			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
						
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	
	// 21.3 Ajax를 이용하는 파일 업로드
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	
	// 21.3.1 jQuery를 이용한 첨부파일 전송
	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile, Model model) {
				
		String uploadFolder = "/Users/itaegjin/git/file/upload";
		
		// make folder ---------------
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("upload path : " + uploadPath);
		
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		// make yyyy/MM/dd folder
		
		for (MultipartFile multipartFile : uploadFile) {
			log.info("---------------------------------------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
//			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			// 22.1.3 중복 방지를 위한 UUID 적용
			UUID uuid = UUID.randomUUID();
			
			// IE has file path
//			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			uploadFileName = uuid.toString() + "_" + uploadFileName;
//			log.info("only file name : " + uploadFileName);
			// File saveFile = new File(uploadFolder, uploadFileName);
			File saveFile = new File(uploadPath, uploadFileName);
			
			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	
	/** 21.3.2 파일 업로드에서 고려해야 하는 점들
		- 동일한 이름으로 파일이 업로드 되었을 때 기존 파일이 사라지는 문제
		- 이미지 파일의 경우에는 원본 파일의 용량이 큰 경우 섬네일 이미지를 생성해야 하는 문제
		- 이미지 파일과 일반 파일을 구분해서 다운로드 혹은 페이지에서 조회하도록 처리하는 문제
		- 첨부파일 공격에 대비하기 위한 업로드 파일의 확장자 제한
	*/
	
	
	/** 22. 파일 업로드 상세 처리
		22.1 파일의 확장자나 크기의 사전 처리
			22.1.1 중복된 이름의 첨부파일 처리
			- 현재 시간의 밀리세컨드까지ㅣ 구분 파일 이름 생성
			- UUID를 이용해서 중복이 발생할 가능성이 거의 없는 문자열을 생성
			- 년/월/일 단위의 폴더 생성
	*/
	
	//	22.1.1 중복된 이름의 첨부파일 처리
	private String getFolder() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
	}
}
