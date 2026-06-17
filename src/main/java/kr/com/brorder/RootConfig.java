package kr.com.brorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RootConfig implements WebMvcConfigurer {
    @Value("${kopo.upload.url}")
    private String uploadUrl;

    @Autowired
    HandlerInterceptor userInterceptor;
    @Autowired
    HandlerInterceptor storeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor).addPathPatterns("/users/**");

        registry.addInterceptor(storeInterceptor)
                .addPathPatterns(
                        "/store/owner",    // 사장님 목록 대시보드 주소 명시
                        "/store/add",      // 등록 페이지 및 처리
                        "/store/*/update", // 수정 페이지 및 처리
                        "/store/*/delete");  // 삭제 처리
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 브라우저에서 /upload/**로 접근하면 프로퍼티로 지정한 D드라이브 경로와 매핑
        registry.addResourceHandler("/upload/**")
                .addResourceLocations(uploadUrl);
    }
}

