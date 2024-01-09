package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileVO;

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
	public ResponseEntity<List<AttachFileVO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		
		List<AttachFileVO> list = new ArrayList();
		String uploadFolder = "/Users/itaegjin/git/file/upload";
		
		String uploadFolderPath = getFolder();
		// make folder ---------------
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		// make yyyy/MM/dd folder
		
		for (MultipartFile multipartFile : uploadFile) {
			
			AttachFileVO attachDTO = new AttachFileVO();
			
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
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) {
		
		log.info("fileName : " + fileName);
		
		File file = new File("/Users/itaegjin/git/file/upload/" + fileName);
		
		log.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			// probeContentType은 브라우저에 보내주는 MIME타입이 종류에 따라 달라지는 것을 적절하게 Http의 헤더 메시지에 포함할 수 있도록 처리
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 24.1 첨부파일 다운로
	@GetMapping(value="/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {
		
		log.info("download file : " + fileName);
		
		Resource resource = new FileSystemResource("/Users/itaegjin/git/file/upload/" + fileName);
		
		if (resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		log.info("resource : " + resource);
		
		String resourceName = resource.getFilename();
		
		// remove UUID
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			
			String downloadName = null;
			
			if (userAgent.contains("Trident")) {
				log.info("IE browser");
				
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replace("\\+", " ");
			} else if(userAgent.contains("Edge")) {
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");
				
				log.info("Edge name : " + downloadName);
			} else {
				log.info("Chrome browser");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			}
			
			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) {
		
		log.info("deleteFile : " + fileName);
		log.info("type : " + type);
		
		File file;
		
		try {
			
			file = new File("/Users/itaegjin/git/file/upload/" + URLDecoder.decode(fileName, "UTF-8"));
			
			file.delete();
			
			if (type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				log.info("largeFileName : " + largeFileName);
				
				file = new File(largeFileName);
				file.delete();
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
}
