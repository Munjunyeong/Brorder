const accuse = e => {
    // 1. 기본 submit 동작 차단
    e.preventDefault();

    const form = document.getElementById("register_form");

    if (form.id.value.trim() === "") {
        alert("아이디를 입력하셔야 합니다"); form.id.focus(); return;
    }
    if (form.password.value.trim() === "") {
        alert("비밀번호를 입력하셔야 합니다"); form.password.focus(); return;
    }
    if (form.passwordConfirm.value.trim() === "") {
        alert("비밀번호 확인을 입력하셔야 합니다"); form.passwordConfirm.focus(); return;
    }
    if (form.name.value.trim() === "") {
        alert("이름을 입력하셔야 합니다"); form.name.focus(); return;
    }
    if (form.nickname.value.trim() === "") {
        alert("닉네임을 입력하셔야 합니다"); form.nickname.focus(); return;
    }

    // 전화번호 입력값 조합 처리
    const p1 = form.phone1.value.trim();
    const p2 = form.phone2.value.trim();
    const p3 = form.phone3.value.trim();

    if (p1 === "" || p2 === "" || p3 === "") {
        alert("전화번호를 입력하셔야 합니다");
        return;
    }

    if (form.password.value !== form.passwordConfirm.value) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다."); return;
    }

    // 기존 hidden phone 필드 제거 후 재생성
    const oldHiddenPhone = form.querySelector("input[name='phone'][type='hidden']");
    if (oldHiddenPhone) oldHiddenPhone.remove();

    const hiddenPhone = document.createElement("input");
    hiddenPhone.type = "hidden";
    hiddenPhone.name = "phone";
    hiddenPhone.value = `${p1}-${p2}-${p3}`;
    form.appendChild(hiddenPhone);

    // role (가입목적) hidden 처리 (HTML name이 purpose일 경우)
    if (form.purpose) {
        const purpose = form.purpose.value;
        const roleValue = (purpose === "store") ? "OWNER" : "USER";

        const oldRole = form.querySelector("input[name='role'][type='hidden']");
        if (oldRole) oldRole.remove();

        const hiddenRole = document.createElement("input");
        hiddenRole.type = "hidden";
        hiddenRole.name = "role";
        hiddenRole.value = roleValue;
        form.appendChild(hiddenRole);
    }

    // 버튼 중복 클릭 방지 (버튼 비활성화)
    const btn = document.getElementById("accuse");
    btn.disabled = true;

    // 폼 실제 전송!
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