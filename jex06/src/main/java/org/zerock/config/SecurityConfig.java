package org.zerock.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.zerock.security.CustomLoginSuccessHandler;
import org.zerock.security.CustomUserDetailsService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Configuration
@EnableWebSecurity
@Log4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// 36.5 JDBC를 이용하는 Java 설정
	@Setter(onMethod_ = {@Autowired})
	private DataSource dataSource;
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		log.info("configure......................");
//		auth.inMemoryAuthentication()
//		.withUser("admin").password("{noop}admin").roles("ADMIN");
//		
//		// $2a$10$0hpNtvkBCnSwek5I747ZHOwPTZ81n2Op4pthJBIXRI4Wel7eLKZym
//		auth.inMemoryAuthentication()
//		.withUser("member").password("$2a$10$0hpNtvkBCnSwek5I747ZHOwPTZ81n2Op4pthJBIXRI4Wel7eLKZym").roles("MEMBER");
//	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		log.info("configure JDBC .........................");
//		
//		String queryUser = "select userid, userpw, enabled from tbl_member where userid = ? ";
//		String queryDetails = "select userid, auth from tbl_member_auth where userid = ? ";
//		
//		auth.jdbcAuthentication()
//			.dataSource(dataSource)
//			.passwordEncoder(passwordEncoder())
//			.usersByUsernameQuery(queryUser)
//			.authoritiesByUsernameQuery(queryDetails);
//	}
	
	@Bean	// 36.2.1 로그인 성공 처리
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}
	
	// 36.4 PasswordEncoder 지정
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// 36.6 커스텀 UserDetailsService 설정
	@Bean
	public UserDetailsService customUserService() {
		return new CustomUserDetailsService();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserService())
		.passwordEncoder(passwordEncoder());
	}
	
	
	@Override	// <security:http> 관련 설정들을 대신
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/sample/all").permitAll()
			.antMatchers("/sample/admin").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/sample/member").access("hasRole('ROLE_MEMBER')");
		// 36.2 로그인 페이지 관련 설정
		http.formLogin()
			.loginPage("/customLogin")
			.loginProcessingUrl("/login")
			.successHandler(loginSuccessHandler());
		// 36.3 로그아웃 처리
		http.logout()
			.logoutUrl("/customLogout")
			.invalidateHttpSession(true)
			.deleteCookies("remember-me", "JESSION_ID");
		
		http.rememberMe()
			.key("zerock")
			.tokenRepository(persistentTokenRepository())
			.tokenValiditySeconds(604800);
	};
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}
	
}
