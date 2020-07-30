function resize() {
    //响应式布局
    var w = document.documentElement.clientWidth;
    var h = document.documentElement.clientHeight;

    var x = document.getElementById("filter");
    var nav = document.getElementById("nav");
    if (w < 720) {
        nav.style.width = "720px";
    } else {
        nav.style.width = "100%";
    }
    if (w < 720) {
        x.style.width = "560px";
    } else {
        x.style.width = w - 160 + "px";
    }
    var div = document.getElementsByName("div");
    var ul = document.getElementsByName("ul");
    var p = document.getElementsByName("p");
    for (var i = 0; i < div.length; i++) {
        if (w > 1300) {
            div[i].style.position = "absolute";
            div[i].style.left = "500px";
            div[i].style.top = "50px";
            ul[i].style.height = "300px";
            p[i].style.width = "600px";
        } else if (w > 1000) {
            div[i].style.position = "absolute";
            div[i].style.left = "500px";
            div[i].style.top = "50px";
            ul[i].style.height = "300px";
            p[i].style.width = w - 700 + "px";
        } else if (w > 720) {
            div[i].style.position = "relative";
            div[i].style.left = "20px";
            div[i].style.top = "10px";
            ul[i].style.height = "500px";
            p[i].style.width = w - 300 + "px";
        } else {
            div[i].style.position = "relative";
            div[i].style.left = "20px";
            div[i].style.top = "10px";
            ul[i].style.height = "500px";
            p[i].style.width = "420px";
        }
    }
}

window.addEventListener("resize", resize);