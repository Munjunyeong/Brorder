const accuse = e => {
    // button 클릭 시 기본 submit/이벤트 동작 방지
    e.preventDefault();

    const form = document.getElementById("register_form");

    if (form.id.value.trim() === "") {
        alert("아이디를 입력하셔야 합니다"); form.id.focus(); return;
    }
    if (form.password.value.trim() === "") {
        alert("비밀번호를 입력하셔야 합니다"); form.password.focus(); return;
    }
    // HTML의 name="passwordConfirm"에 맞게 수정
    if (form.passwordConfirm.value.trim() === "") {
        alert("비밀번호 확인을 입력하셔야 합니다"); form.passwordConfirm.focus(); return;
    }
    if (form.name.value.trim() === "") {
        alert("이름을 입력하셔야 합니다"); form.name.focus(); return;
    }
    if (form.nickname.value.trim() === "") {
        alert("닉네임을 입력하셔야 합니다"); form.nickname.focus(); return;
    }

    // 전화번호 입력값 조합 처리 (phone1, phone2, phone3)
    const p1 = form.phone1.value.trim();
    const p2 = form.phone2.value.trim();
    const p3 = form.phone3.value.trim();

    if (p1 === "" || p2 === "" || p3 === "") {
        alert("전화번호를 입력하셔야 합니다");
        if (p1 === "") form.phone1.focus();
        else if (p2 === "") form.phone2.focus();
        else form.phone3.focus();
        return;
    }

    if (form.password.value !== form.passwordConfirm.value) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다."); return;
    }

    // 기존 hidden phone 필드가 있다면 제거
    const oldHidden = form.querySelector("input[name='phone'][type='hidden']");
    if (oldHidden) oldHidden.remove();

    // phone1, phone2, phone3 값을 합쳐서 hidden input 생성 (예: 010-1234-5678)
    const hiddenPhone = document.createElement("input");
    hiddenPhone.type = "hidden";
    hiddenPhone.name = "phone";
    hiddenPhone.value = `${p1}-${p2}-${p3}`; // 하이픈 없이 저장하려면 `${p1}${p2}${p3}`
    form.appendChild(hiddenPhone);

    // 폼 최종 제출
    form.submit();
}

const check_id = e => {
    const id = document.querySelector("input[name='id']").value;
    if (!id.trim()) {
        alert("아이디를 입력해주세요.");
        return;
    }

    fetch(`/check_id/${id}`, {
        method: "GET",
    })
        .then(resp => {
            if (resp.ok) return resp.text();
            else throw new Error("중복 확인을 할 수 없습니다.");
        })
        .then(result => {
            if (result === "true") {
                alert("사용 가능한 아이디입니다.");
                document.getElementById("accuse").removeAttribute("disabled");
            } else {
                alert("중복된 아이디입니다.");
            }
        }).catch(err => alert(err));
}

const changedId = e => {
    document.getElementById("accuse").setAttribute("disabled", true);
}

window.addEventListener("load", () => {
    document.getElementById("accuse").addEventListener("click", accuse);
    document.getElementById("check_id").addEventListener("click", check_id);
    document.querySelector("input[name='id']").addEventListener("keydown", changedId);

    document.querySelectorAll(".view").forEach(item => {
        item.addEventListener("mousedown", e => {
            const input = e.target.closest("div").querySelector("input");
            input.type = "text";
        });
        const hide = e => {
            const input = e.target.closest("div").querySelector("input");
            input.type = "password";
        };
        item.addEventListener("mouseup", hide);
        item.addEventListener("mouseleave", hide);
    });
});