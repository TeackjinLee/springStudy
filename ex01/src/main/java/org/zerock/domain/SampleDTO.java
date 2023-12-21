package org.zerock.domain;

import lombok.Data;

@Data	// getter/setter, equals(), toString() 등의 메서드를 자동 생성하기 떄문에 편리하다.
public class SampleDTO {
	/*********************************************************************** 
	 * Controller를 작성할 때 가장 편리한 기능은 파라미터가 자동으로 수집되는 기능이다.
	 * 이 기능을 이용하면 매번 request.getParmeter()를 이용하는 불편함을 없앨 수 있습니다.
	 ***********************************************************************/
	
	private String name;
	private int age;
	
}
