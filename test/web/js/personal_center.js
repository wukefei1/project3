function resize() {
    //响应式布局
    var w = document.documentElement.clientWidth;
    var h = document.documentElement.clientHeight;

    var x = document.getElementById("filter");
    var x1 = document.getElementById("filter1");
    var x2 = document.getElementById("filter2");
    var nav = document.getElementById("nav");
    if (w < 720) {
        nav.style.width = "720px";
        x.style.width = "560px";
        x1.style.width = "560px";
        x2.style.width = "560px";
    } else {
        nav.style.width = "100%";
        x.style.width = w - 160 + "px";
        x1.style.width = w - 160 + "px";
        x2.style.width = w - 160 + "px";
    }
}

window.addEventListener("resize", resize);

