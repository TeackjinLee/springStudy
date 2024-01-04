package org.zerock.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.controller.SampleControllerTest;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringRunner.class)
@ContextConfiguration({
	"file:/Users/itaegjin/git/springStudy/ex03/src/main/webapp/WEB-INF/spring/root-context.xml"
})
@Log4j
public class ReplyMapperTests {
	
	private Long[] bnoArr = { 3195151L, 3195150L, 3195149L, 3195148L, 3195147L, 3195146L, 3195145L, 3195144L, 3195143L, 3195142L};
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Test
	public void testCreate() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			ReplyVO vo = new ReplyVO();
			
			// 게시물 번호
			vo.setBno(bnoArr[i%5]);
			vo.setReply("댓글 테스트 " + i);
			vo.setReplyer("replyer" + i);
			
			mapper.insert(vo);
		});
	}
	
	@Test
	public void testMapper() {
		log.info(mapper);
	}
	
	@Test
	public void testRead() {
		Long targetRno = 36L;
		ReplyVO vo = mapper.read(targetRno);
		log.info(vo);
	}
	
	@Test
	public void testDelete() {
		Long targetRno = 1L;
		mapper.delete(targetRno);
	}

	@Test
	public void testUpdate() {
		Long targetRno = 10L;
		
		ReplyVO vo = mapper.read(targetRno);
		vo.setReply("update Reply");
		int count = mapper.update(vo);
		log.info("UPDATE COUNT : " + count);
	}
	
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		// 3195150L
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[1]);
		
		replies.forEach(reply -> log.info(reply));
	}
	
	@Test
	public void testList2() {
		Criteria cri = new Criteria(2, 10);
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 3195150L);
		replies.forEach(reply -> log.info(reply));
	}
}
