package kr.com.brorder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.com.brorder.users.UserInterceptor;

@Configuration
public class InterceptorConfig {
	
	@Bean
	HandlerInterceptor userInterceptor() {
		return new UserInterceptor();
	}

}
