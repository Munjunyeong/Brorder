const accuse = e => {
    const form = document.getElementById("register_form");
    if(form.id.value == "") {
        alert("아이디를 입력하셔야 합니다"); form.id.focus(); return;
    }
    if(form.password.value == "") {
        alert("비밀번호를 입력하셔야 합니다"); form.password.focus(); return;
    }
    if(form.check_password.value == "") {
        alert("비밀번호 확인을 입력하셔야 합니다"); form.check_password.focus(); return;
    }
    if(form.nickname.value == "") {
        alert("닉네임를 입력하셔야 합니다"); form.nickname.focus(); return;
    }
    if(form.name.value == "") {
        alert("이름를 입력하셔야 합니다"); form.name.focus(); return;
    }
    if(form.phone.value == "") {
        alert("전화번호를 입력하셔야 합니다"); form.phone.focus(); return;
    }
    if(form.password.value != form.check_password.value) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다."); return;
    }
    if(form.role.value == "user" || form.role.value == "owner") {
        alert("잘못된 역할입니다."); return;
    }

    form.submit();
}

const check_id = e => {
    const id = document.querySelector("input[name='id']").value;
    fetch(`/check_id/${id}`, {
        method:"GET",
    })
    .then(resp => {
        if(resp.ok)
            return resp.text();
        else
            throw new Error("중복 확인을 할 수 없습니다.");
    })
    .then(result => {
        if(result === "true") {
            alert("사용 가능한 아이디입니다.")
            document.getElementById("accuse").removeAttribute("disabled");
        } else {
            alert("중복된 아이디입니다.")
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
    })
    const hide = e => {
        const input = e.target.closest("div").querySelector("input");
        input.type = "password";
    }
    item.addEventListener("mouseup", hide);
    item.addEventListener("mouseleave", hide);
  });
})