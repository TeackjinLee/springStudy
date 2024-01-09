package org.zerock.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
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
	
	@Before("execution(* org.zerock.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1 : " + str1);
		log.info("str2 : " + str2);
	}
	
	@AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService*.*(..))", throwing="exception")
	public void logException(Exception exception) {
		log.info("Exception......!!!!");
		log.info("exception : " + exception);
	}
	
	// @Around - 메서드의 실행 자체를 제어할 수 있는 가장 강력한 코드, 직접 대상 메서드를 호출하고 결과나 예외를 처리할 수 있습니다.
	@Around("execution(* org.zerock.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();
		log.info("Target : " + pjp.getTarget());
		log.info("param : " + Arrays.toString(pjp.getArgs()));
		
		// invoke method
		Object result = null;
		
		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		log.info("Time : " + (end - start));
		log.info("result : " + result);
		return result;
	}
}
