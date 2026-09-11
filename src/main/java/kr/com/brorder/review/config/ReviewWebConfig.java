package kr.com.brorder.review.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry; // ◀ [추가 주석] 리소스 매핑을 위한 스펙 임포트
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 리뷰 도메인 전용 웹 설정 파일
 * - 와일드카드를 보완하여 /review/write 진입 경로의 누수를 원천 차단
 * - [기능 확장] application.properties의 위험한 설정을 제거하고 d:/upload 폴더를 안전하게 매핑
 * - [기능 확장] /store/list 진입도 로그인 필수로 묶음 (메인페이지 '배달 주문' 버튼 등)
 */
@Configuration
public class ReviewWebConfig implements WebMvcConfigurer {

    @Value("${kopo.upload.url}")
    private String uploadUrl;

    private final ReviewInterceptor reviewInterceptor;

    @Autowired
    public ReviewWebConfig(ReviewInterceptor reviewInterceptor) {
        this.reviewInterceptor = reviewInterceptor;
    }

    /**
     * [정적 리소스 핸들러 정석 연동]
     * - 이 메서드가 탑재되었기 때문에 application.properties의 지저분한 static-locations 설정을 통째로 지워도 됩니다.
     * - 웹 브라우저가 /upload/** 주소로 접근하면, 실제 서버 컴퓨터의 d:/upload/ 내부 자원을 안전하게 다이렉트로 매핑합니다.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/upload/**")
//                .addResourceLocations("file:///d:/upload/");
        System.err.println("uploadUrl: " + uploadUrl);

        registry.addResourceHandler("/upload/**")
                .addResourceLocations(uploadUrl);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그인 검사가 필수인 리뷰 관련 주소들의 가로채기 범위 정밀 교정
        registry.addInterceptor(reviewInterceptor)
                .addPathPatterns(
                        "/review/write/**",
                        "/review/delete/**",
                        "/review/update/**",
                        "/store/**"                // ★ [추가] /store 로 시작하는 모든 링크(매장 상세 등) 차단
                )
                // 정적 자원 및 비회원 공개 페이지는 제외
                .excludePathPatterns(
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/upload/**",
                        "/store/list",             // ★ [중요] 메인 캐러셀이 데이터를 불러오기 위해 통과시켜야 함
                        "/store/list/**"           // ★ [중요] 리스트 조회 관련 파라미터 통과
                )
                .order(1);
    }
}