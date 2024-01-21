package org.zerock.mapper;

import org.springframework.stereotype.Repository;
import org.zerock.domain.MemberVO;

@Repository
public interface MemberMapper {
	public MemberVO read(String userid);
}
