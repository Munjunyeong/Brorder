const accuse = e => {
    const form = document.getElementById("resultpw");
    if(form.pw1.value == "") {
        alert("비밀번호를 입력하셔야 합니다"); form.pw1.focus(); return;
    }
    if(form.pw2.value == "") {
        alert("비밀번호 확인을 입력하셔야 합니다."); form.pw2.focus(); return;
    }
    if(form.pw1.value != form.pw2.value) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다"); return;
    }
    form.submit();
}

window.addEventListener("load", () => {
    document.getElementById("accuse").addEventListener("click", accuse);
})