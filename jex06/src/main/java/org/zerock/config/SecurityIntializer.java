package org.zerock.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityIntializer extends AbstractSecurityWebApplicationInitializer {
	// AbstractSecurityWebApplicationInitializer 클래스는 내부적으로 Delegationg FilterProxy를 
	// 스프링에 등록하는데 이 작업은 별도의 구현 없이 클래스를 추가하는 것 만으로도 설정이 완료.
	
}
