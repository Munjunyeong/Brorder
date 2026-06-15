/* ── 리뷰 카드 순차 노출 처리 ── */
// const reviewCards = document.querySelectorAll('.myreview-card, .review-card');
// if (reviewCards.length > 0) {
//     const cardObs = new IntersectionObserver(entries => {
//         entries.forEach(entry => {
//             if (entry.isIntersecting) {
//                 const delay = entry.target.dataset.delay || 0;
//                 setTimeout(() => entry.target.classList.add('card-visible'), parseInt(delay));
//                 cardObs.unobserve(entry.target);
//             }
//         });
//     }, { threshold: 0.08 });
//
//     reviewCards.forEach(card => cardObs.observe(card));
// }

/* ── 스크롤 탑 및 헤더 상태 처리 ── *//* ── 스크롤 탑 및 헤더 상태 처리 ── */
const reviewScrollBtn = document.getElementById('scrollTopBtn');
const mainHeader = document.getElementById('mainHeader');

let lastHeaderScrolled = false;
let lastScrollButtonShown = false;
let ticking = false;

window.addEventListener('scroll', () => {
    if (ticking) return;

    ticking = true;

    requestAnimationFrame(() => {
        const scrollY = window.scrollY || document.documentElement.scrollTop;

        const shouldShowScrollButton = scrollY > 300;
        if (reviewScrollBtn && shouldShowScrollButton !== lastScrollButtonShown) {
            reviewScrollBtn.classList.toggle('show', shouldShowScrollButton);
            lastScrollButtonShown = shouldShowScrollButton;
        }

        const shouldHeaderScrolled = scrollY > 80;
        if (mainHeader && shouldHeaderScrolled !== lastHeaderScrolled) {
            mainHeader.classList.toggle('scrolled', shouldHeaderScrolled);
            lastHeaderScrolled = shouldHeaderScrolled;
        }

        ticking = false;
    });
}, { passive: true });

/* ── 필터 탭 활성 처리 ── */
document.querySelectorAll('.filter-tab').forEach(tab => {
    tab.addEventListener('click', function(e) {
        e.preventDefault();
        document.querySelectorAll('.filter-tab').forEach(t => t.classList.remove('active'));
        this.classList.add('active');
    });
});

/* ── 파일 업로드 라벨 처리 ── */
const fileInput = document.querySelector('input[type="file"]');
const fileLabel = document.querySelector('.file-upload-label');
if (fileInput && fileLabel) {
    fileInput.addEventListener('change', () => {
        if (fileInput.files.length > 0) {
            fileLabel.textContent = fileInput.files[0].name;
            fileLabel.style.color = 'var(--teal)';
            fileLabel.style.fontWeight = '700';
        }
    });
}

/* ── 커스텀 삭제 모달 처리 ── */
let _pendingReviewId = null;
let _pendingStoreId = null;

function openDeleteModal(btn) {
    _pendingReviewId = btn.dataset.reviewId;
    _pendingStoreId = btn.dataset.storeId;
    document.getElementById('deleteModal')?.classList.add('open');
}

function closeDeleteModal() {
    document.getElementById('deleteModal')?.classList.remove('open');
    _pendingReviewId = null;
    _pendingStoreId = null;
}

const deleteModal = document.getElementById('deleteModal');
if (deleteModal) {
    deleteModal.addEventListener('click', function(e) {
        if (e.target === this) closeDeleteModal();
    });
}

const modalConfirmBtn = document.getElementById('modalConfirmBtn');
if (modalConfirmBtn) {
    modalConfirmBtn.addEventListener('click', function() {
        if (!_pendingReviewId) return;

        const formData = new FormData();
        formData.append('storeId', _pendingStoreId);

        this.textContent = '삭제 중...';
        this.disabled = true;

        fetch('/review/delete/' + _pendingReviewId, {
            method: 'POST',
            body: formData
        })
            .then(res => {
                if (res.ok) {
                    closeDeleteModal();

                    const card = document.querySelector(`[data-review-dom-id="${_pendingReviewId}"]`);
                    if (card) {
                        card.style.transition = 'opacity .4s, transform .4s';
                        card.style.opacity = '0';
                        card.style.transform = 'scale(.95)';
                        setTimeout(() => {
                            card.remove();
                            location.reload();
                        }, 420);
                    } else {
                        location.reload();
                    }
                } else {
                    alert('삭제 중 오류가 발생했습니다. 다시 시도해 주세요.');
                    closeDeleteModal();
                    location.reload();
                }
            })
            .catch(() => {
                alert('네트워크 오류가 발생했습니다.');
                closeDeleteModal();
            });
    });
}

/* ── 도움돼요 버튼 토글 처리 ── */
document.querySelectorAll('.help-btn').forEach(button => {
    button.addEventListener('click', function() {
        const countElement = this.querySelector('.help-count');
        const currentCount = parseInt(this.dataset.count || countElement.textContent, 10);
        const isActive = this.classList.contains('active');

        if (isActive) {
            this.classList.remove('active');
            this.dataset.count = String(currentCount - 1);
            countElement.textContent = currentCount - 1;
            this.setAttribute('aria-pressed', 'false');
        } else {
            this.classList.add('active');
            this.dataset.count = String(currentCount + 1);
            countElement.textContent = currentCount + 1;
            this.setAttribute('aria-pressed', 'true');
        }
    });
});
/*// 06/15 커밋 테스트*/