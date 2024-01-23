package org.zerock.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller	// 스프링의 빈으로 인식할 수 있게 한다.
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor // 생성자를 만들고 자동으로 주입하도록 함.
public class BoardController {
//	@Setter(onMethod_ = {@Autowired})	// AllArgConstructor 이용 안할시 생성해줘야함
	private BoardService boardService;
	
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		log.info("list : " + cri);
		model.addAttribute("list", boardService.getList(cri));
//		model.addAttribute("pageMaker", new PageDTO(cri, 123));
		
		int total = boardService.getTotal(cri);
		
		log.info("total : " + total);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		log.info("==============================================");
		if (board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		
		log.info("==============================================");
		
		boardService.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
	}
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info("/get or modify");
		model.addAttribute("board", boardService.get(bno));
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("modify : " + board);
		System.out.println("###########################1");
		if (boardService.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		} 
		
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/board/list" + cri.getListLink();
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove..." + bno);
		
		List<BoardAttachVO> attachList = boardService.getAttachList(bno);
		
		if (boardService.remove(bno)) {
			
			// delete Attach Files
			deleteFiles(attachList);
			
			rttr.addFlashAttribute("result", "success");
		}
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/board/list" + cri.getListLink();
	}
	
	@GetMapping(value="/getAttachList",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {
		
		log.info("getAttachList " + bno);
		
		return new ResponseEntity<>(boardService.getAttachList(bno), HttpStatus.OK);
	}
	

	private void deleteFiles(List<BoardAttachVO> attachList) {
		
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		
		log.info("delete attach files...........................");
		log.info(attachList);
		
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get("/Users/itaegjin/git/file/upload" + attach.getUploadPath() + "/" + attach.getUuid() + "_" + attach.getFileName());
				
				Files.deleteIfExists(file);
				
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("/Users/itaegjin/git/file/upload" + attach.getUploadPath() + "/s_" + attach.getUuid() + "_" + attach.getFileName());
					Files.delete(thumbNail);
				}
			} catch(Exception e) {
				log.error("delete file error : " + e.getMessage());
			}
		});
	}
}
