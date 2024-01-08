package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

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
//	@PostMapping("/uploadAjaxAction")
//	public void uploadAjaxPost(MultipartFile[] uploadFile, Model model) {
//				
//		String uploadFolder = "/Users/itaegjin/git/file/upload";
//		
//		// make folder ---------------
//		File uploadPath = new File(uploadFolder, getFolder());
//		log.info("upload path : " + uploadPath);
//		
//		if (uploadPath.exists() == false) {
//			uploadPath.mkdirs();
//		}
//		// make yyyy/MM/dd folder
//		
//		for (MultipartFile multipartFile : uploadFile) {
//			log.info("---------------------------------------------------");
//			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
//			log.info("Upload File Size : " + multipartFile.getSize());
//			
////			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
//			
//			String uploadFileName = multipartFile.getOriginalFilename();
//			
//			// 22.1.3 중복 방지를 위한 UUID 적용
//			UUID uuid = UUID.randomUUID();
//			
//			// IE has file path
////			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
//			uploadFileName = uuid.toString() + "_" + uploadFileName;
////			log.info("only file name : " + uploadFileName);
//			// File saveFile = new File(uploadFolder, uploadFileName);
//			try {
//				File saveFile = new File(uploadPath, uploadFileName);
//				multipartFile.transferTo(saveFile);
//				
//				// check image type file
//				if (checkImageType(saveFile)) {
//					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
//					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
//					thumbnail.close();
//				}
//			} catch (Exception e) {
//				log.error(e.getMessage());
//			}
//		}
//	}
	
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
	
	/** 22.2 섬네일 이미지 생성
		22.2.1 이미지 파일의 판단
	*/
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/********************************************
	 * 22.3 업로드된 파일의 데이터 반환
	 ********************************************/
	// 21.3.1 jQuery를 이용한 첨부파일 전송
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		
		List<AttachFileDTO> list = new ArrayList();
		String uploadFolder = "/Users/itaegjin/git/file/upload";
		
		String uploadFolderPath = getFolder();
		// make folder ---------------
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		// make yyyy/MM/dd folder
		
		for (MultipartFile multipartFile : uploadFile) {
			
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();

			// IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("only file name : " + uploadFileName);
			attachDTO.setFileName(uploadFileName);
			
			// 22.1.3 중복 방지를 위한 UUID 적용
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;

			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				// check image type file
				if (checkImageType(saveFile)) {
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
				
				// add to List
				list.add(attachDTO);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}
