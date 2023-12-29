package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	
	// BoardMapper.xml작성 으로 주석
//	@Select("select * from tbl_board where bno > 0")
//	public List<BoardVO> getList();
	
//	@Select("select * from tbl_board where bno > 0")
	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	// insert만 처리되고 생성된 PK값을 알 필요가 없는 경우
	public void insert(BoardVO board);
	
	// insert문이 실행되고 생성된 PK값을 알아야 하는 경우
	public void insertSelectKey(BoardVO board);
	
	// read 처리
	public BoardVO read(Long bno);
	
	// delete 처리
	public int delete(Long bno);
	
	// update 처리
	public int update(BoardVO board);

}
