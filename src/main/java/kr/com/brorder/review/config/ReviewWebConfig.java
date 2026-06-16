package kr.com.brorder.review.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 리뷰 도메인 전용 웹 설정 파일
 * - 와일드카드를 보완하여 /review/write 진입 경로의 누수를 원천 차단
 */
@Configuration
public class ReviewWebConfig implements WebMvcConfigurer {

    private final ReviewInterceptor reviewInterceptor;

    @Autowired
    public ReviewWebConfig(ReviewInterceptor reviewInterceptor) {
        this.reviewInterceptor = reviewInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그인 검사가 필수인 리뷰 관련 주소들의 가로채기 범위 정밀 교정
        registry.addInterceptor(reviewInterceptor)
                .addPathPatterns(
                        "/review/write/**",        // ★ 뒤에 /** 를 붙여서 파라미터가 붙은 GET/POST 진입로 전체를 묶어 감시합니다.
                        "/review/delete/**",       // 리뷰 삭제 전체 감시
                        "/review/update/**"      // 리뷰 수정 전체 감시
                )

                // 정적 자원 및 비회원 공개 페이지는 제외
                .excludePathPatterns(
                        "/css/**",
                        "/js/**",
                        "/images/**"
                )
                .order(1); // 타 팀원 인터셉터보다 무조건 1등으로 먼저 실행되도록 우선순위 고정
    }
}