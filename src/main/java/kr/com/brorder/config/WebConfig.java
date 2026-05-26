package kr.com.brorder.config;

import kr.com.brorder.review.interceptor.ReviewAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * [웹 시스템 웹 MVC 설정 클래스]
 * 구현된 인터셉터 뼈대를 스프링 부트에 공식 등록하고 작동할 주소 필터를 매핑하는 공간임
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ReviewAuthInterceptor reviewAuthInterceptor;

    /**
     * 생성자 주입을 통해 구현해 둔 리뷰 인증 인터셉터 빈(Bean)을 조립함
     */
    @Autowired
    public WebConfig(ReviewAuthInterceptor reviewAuthInterceptor) {
        this.reviewAuthInterceptor = reviewAuthInterceptor;
    }

    /**
     * [인터셉터 주소 필터 매핑 등록]
     * 리뷰 컨트롤러의 주소들을 감시하되, 비회원도 접근 가능한 예외 경로를 정밀 세팅함
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(reviewAuthInterceptor)
                .addPathPatterns("/review/**") // /review로 시작하는 모든 주소(등록, 수정, 삭제 등)를 일단 전부 잠금 처리함
                .excludePathPatterns("/review/store/{storeId}"); // [예외 대상] 가게별 리뷰 리스트 조회는 로그아웃 상태에서도 구경할 수 있도록 문을 열어둠
    }
}