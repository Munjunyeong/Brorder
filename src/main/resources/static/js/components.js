/* ══════════════════════════════════════════════════════════
   BRORDER 공용 컴포넌트 스크립트
   ══════════════════════════════════════════════════════════ */

/**
 * 스켈레톤 → 실제 콘텐츠 전환
 * skeletonEl : .sk-grid 뼈대 엘리먼트
 * contentEl  : 서버가 이미 렌더링해 둔 실제 목록(th:each 결과) 엘리먼트
 * minDelay   : 최소 노출 시간(ms) - 너무 빨리 사라져서 깜빡이는 것 방지
 */
function swapSkeletonToContent(skeletonEl, contentEl, minDelay = 400) {
    if (!skeletonEl || !contentEl) return;
    const start = performance.now();
    const reveal = () => {
        const elapsed = performance.now() - start;
        const wait = Math.max(0, minDelay - elapsed);
        setTimeout(() => {
            skeletonEl.classList.add('sk-hidden');
            contentEl.classList.remove('sk-hidden');
        }, wait);
    };
    if (document.readyState === 'complete') {
        reveal();
    } else {
        window.addEventListener('load', reveal, { once: true });
    }
}

/**
 * 리뷰 이미지 업로드 박스 - 선택한 파일명을 라벨에 표시
 * inputEl : <input type="file">
 * labelEl : 파일명이 표시될 <p>
 */
function initReviewUpload(inputEl, labelEl) {
    if (!inputEl || !labelEl) return;
    inputEl.addEventListener('change', () => {
        const file = inputEl.files && inputEl.files[0];
        labelEl.textContent = file ? file.name : '선택된 파일 없음';
    });
}

/**
 * 주문 버튼 로딩 상태 토글 (버튼 텍스트를 로더로 교체)
 * buttonEl : .btn-order 버튼
 * onDone   : 로딩이 끝난 뒤 실행할 콜백 (예: 실제 주문 API 호출 결과 처리)
 */
function setOrderButtonLoading(buttonEl, isLoading) {
    if (!buttonEl) return;
    if (isLoading) {
        buttonEl.dataset.originalHtml = buttonEl.innerHTML;
        buttonEl.innerHTML = `
            <div class="loader-dots">
                <div class="dot"></div><div class="dot"></div><div class="dot"></div>
                <div class="dot-shadow"></div><div class="dot-shadow"></div><div class="dot-shadow"></div>
            </div>`;
        buttonEl.disabled = true;
    } else if (buttonEl.dataset.originalHtml) {
        buttonEl.innerHTML = buttonEl.dataset.originalHtml;
        buttonEl.disabled = false;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    // 인기 맛집 섹션
    swapSkeletonToContent(
        document.getElementById('popularSkeleton'),
        document.getElementById('popularContent')
    );
    // 내 주변 매장 섹션
    swapSkeletonToContent(
        document.getElementById('storeSkeleton'),
        document.getElementById('storeContent')
    );

    // 카테고리 드롭다운: 호버는 CSS로 처리되고,
    // 여긴 터치 기기용 클릭 토글 + 바깥클릭/ESC 닫기만 담당
    const catDropdown = document.getElementById('catDropdown');
    const catTrigger = document.getElementById('catTriggerBtn');
    if (catDropdown && catTrigger) {
        catTrigger.addEventListener('click', () => {
            const isOpen = catDropdown.classList.toggle('open');
            catTrigger.setAttribute('aria-expanded', String(isOpen));
        });
        document.addEventListener('click', (e) => {
            if (!catDropdown.contains(e.target)) {
                catDropdown.classList.remove('open');
                catTrigger.setAttribute('aria-expanded', 'false');
            }
        });
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape') {
                catDropdown.classList.remove('open');
                catTrigger.setAttribute('aria-expanded', 'false');
            }
        });
    }
});