/* ── 헤더 스크롤 처리 ── */
const headerEl = document.getElementById('mainHeader');
const scrollTopBtn = document.getElementById('scrollTopBtn');
window.addEventListener('scroll', () => {
    const y = window.scrollY;
    if (headerEl) headerEl.classList.toggle('scrolled', y > 60);
    if (scrollTopBtn) scrollTopBtn.classList.toggle('show', y > 320);
}, { passive: true });

/* ── 카운트다운 처리 ── */
function startTimer(duration, els) {
    let t = duration;
    const tick = () => {
        const h = String(Math.floor(t / 3600)).padStart(2,'0');
        const m = String(Math.floor((t % 3600) / 60)).padStart(2,'0');
        const s = String(t % 60).padStart(2,'0');
        const str = `${h}:${m}:${s}`;
        els.forEach(el => { if (el) el.textContent = str; });
        if (--t < 0) t = duration;
    };
    tick();
    setInterval(tick, 1000);
}
startTimer(10800, [
    document.getElementById('countdown')
]);

/* ── 스크롤 리빌 처리 ── */
const revealEls = document.querySelectorAll('.reveal, .reveal-scale');
const revealObs = new IntersectionObserver(entries => {
    entries.forEach(e => {
        if (e.isIntersecting) {
            e.target.classList.add('visible');
            revealObs.unobserve(e.target);
        }
    });
}, { threshold: 0.08 });
revealEls.forEach(el => revealObs.observe(el));

/* ── 섹션 슬라이드 처리 ── */
const snapSections = document.querySelectorAll('.snap-section');
const snapObs = new IntersectionObserver(entries => {
    entries.forEach(e => {
        if (e.isIntersecting) {
            e.target.classList.add('in-view');
            snapObs.unobserve(e.target);
        }
    });
}, { threshold: 0.05 });
snapSections.forEach(el => snapObs.observe(el));

/* ── 카운트업 처리 ── */
function countUp(el, target, isFloat, ms) {
    if (!el) return;
    const start = performance.now();
    const step = now => {
        const p = Math.min((now - start) / ms, 1);
        const eased = 1 - Math.pow(1 - p, 3);
        const v = eased * target;
        el.textContent = isFloat ? v.toFixed(1) : Math.floor(v).toLocaleString();
        if (p < 1) requestAnimationFrame(step);
        else el.textContent = isFloat ? target.toFixed(1) : target.toLocaleString();
    };
    requestAnimationFrame(step);
}
const ctaObs = new IntersectionObserver(entries => {
    entries.forEach(e => {
        if (e.isIntersecting) {
            countUp(document.getElementById('stat-reviews'), 24680, false, 1800);
            countUp(document.getElementById('stat-rating'),  4.8,   true,  1200);
            countUp(document.getElementById('stat-orders'),  8500,  false, 1600);
            ctaObs.disconnect();
        }
    });
}, { threshold: 0.3 });
const ctaSection = document.querySelector('.review-cta');
if (ctaSection) ctaObs.observe(ctaSection);

/* ── 리플 이펙트 처리 ── */
document.querySelectorAll('.rest-card, .store-card, .ts-card').forEach(card => {
    card.addEventListener('click', function(e) {
        const r = document.createElement('span');
        const rect = this.getBoundingClientRect();
        const size = Math.max(rect.width, rect.height);
        r.style.cssText = [
            `position:absolute`,
            `border-radius:50%`,
            `width:${size}px`,
            `height:${size}px`,
            `left:${e.clientX - rect.left - size / 2}px`,
            `top:${e.clientY - rect.top - size / 2}px`,
            `background:rgba(42,193,188,.1)`,
            `transform:scale(0)`,
            `animation:ripple .55s ease-out forwards`,
            `pointer-events:none`,
            `z-index:5`
        ].join(';');
        this.style.position = 'relative';
        this.style.overflow = 'hidden';
        this.appendChild(r);
        setTimeout(() => r.remove(), 600);
    });
});

/* ── 카테고리 탭 활성 처리 ── */
document.querySelectorAll('.cat-tab').forEach(tab => {
    tab.addEventListener('click', function() {
        document.querySelectorAll('.cat-tab').forEach(t => t.classList.remove('active'));
        this.classList.add('active');
    });
});