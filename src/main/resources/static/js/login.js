window.addEventListener("load", () => {

    document.getElementById("loginbutton")
        .addEventListener("click", loginbutton);

    const params = new URLSearchParams(window.location.search);
    const error = params.get("error");

    if (error === "false") {
        alert("아이디 또는 비밀번호가 올바르지 않습니다.");
    }
});
