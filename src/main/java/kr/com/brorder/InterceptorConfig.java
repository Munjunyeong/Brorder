package kr.com.brorder;

import kr.com.brorder.store.StoreInterceptor;
import kr.com.brorder.users.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class InterceptorConfig {
	
	@Bean
	HandlerInterceptor userInterceptor() {
		return new UserInterceptor();
	}

	@Bean
	HandlerInterceptor storeInterceptor() { return new StoreInterceptor(); }

}
