package org.zerock.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect		// 해당 클래스 객체가 Aspect를 구현한 것임으로 나태내기 위해서 사용
@Log4j
@Component	// AOP와는 관계는 없지만 스프링에서 빈으로 인식하기 위해서 사용
public class LogAdvice {
	
	@Before( "execution(* org.zerock.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("============================");
	}
}
