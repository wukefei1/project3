function resize() {
    //响应式布局
    var w = document.documentElement.clientWidth;
    var h = document.documentElement.clientHeight;

    var nav = document.getElementById("nav");
    var x = document.getElementById("filter");
    if (w < 720) {
        x.style.width = "560px";
    } else {
        x.style.width = w - 160 + "px";
    }
    if (w < 720) {
        nav.style.width = "720px";
    } else {
        nav.style.width = "100%";
    }

    var ul = document.getElementsByName("ul");
    var photo = document.getElementById("photo");
    var p = document.getElementById("p");
    for (var i = 0; i < ul.length; i++) {
        if (w < 880) {
            ul[i].style.top = "450px";
            ul[i].style.left = "30px";
            ul[i].style.width = "calc(100% - 100px)"
            photo.style.height = "1160px";
            p.style.top = "880px";
            p.style.lineHeight = "50px";
        } else if (w < 1200) {
            ul[i].style.top = "450px";
            ul[i].style.left = "30px";
            ul[i].style.width = "calc(100% - 100px)"
            photo.style.height = "1160px";
            p.style.top = "880px";
            p.style.lineHeight = "60px";
        } else {
            ul[i].style.top = "60px";
            ul[i].style.left = "640px";
            ul[i].style.width = "calc(100% - 760px)";
            photo.style.height = "720px";
            p.style.top = "540px";
            p.style.lineHeight = "60px";
        }
    }
}

window.addEventListener("resize", resize);