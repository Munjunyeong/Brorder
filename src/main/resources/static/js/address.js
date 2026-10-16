const change_address = e => {
    // 1. 이벤트가 발생한 버튼 요소
    const button = e.currentTarget;

    // 2. 버튼이 속한 상위 .address-card 탐색
    const card = button.closest('.address-card');

    // 3. 카드 안의 숨겨진 .hidden-address-id 태그에서 값 추출
    const addressId = card.querySelector('.hidden-address-id').innerText;

    console.log("선택된 주소 ID:", addressId);

    // TODO: 서버로 데이터 전송 (Fetch/AJAX 등)
}

window.addEventListener("load", () => {
    // 반복문으로 생성된 모든 '기본 주소로 설정' 버튼에 이벤트 등록
    const buttons = document.querySelectorAll(".change_address");
    
    buttons.forEach(btn => {
        btn.addEventListener("click", change_address);
    });
});