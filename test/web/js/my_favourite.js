function resize() {
    //响应式布局
    var w = document.documentElement.clientWidth;
    var h = document.documentElement.clientHeight;

    var x = document.getElementById("filter");
    var x1 = document.getElementById("filter1");
    var nav = document.getElementById("nav");
    if (w < 720) {
        nav.style.width = "720px";
        x.style.width = "560px";
        x1.style.width = "560px";
    } else {
        nav.style.width = "100%";
        x.style.width = w - 160 + "px";
        x1.style.width = w - 160 + "px";
    }

    var div = document.getElementsByName("div");
    var ul = document.getElementsByName("ul");
    var p = document.getElementsByName("p");
    var button2 = document.getElementsByName("button2");
    for (var i = 0; i < div.length; i++) {
        if (w > 1300) {
            div[i].style.position = "absolute";
            div[i].style.left = "500px";
            div[i].style.top = "50px";
            ul[i].style.height = "300px";
            p[i].style.width = "600px";
            button2[i].style.right = w - 1240 + "px";
        } else if (w > 1140) {
            div[i].style.position = "absolute";
            div[i].style.left = "500px";
            div[i].style.top = "50px";
            ul[i].style.height = "300px";
            p[i].style.width = w - 700 + "px";
            button2[i].style.right = 60 + "px";
        } else if (w > 1000) {
            div[i].style.position = "absolute";
            div[i].style.left = "500px";
            div[i].style.top = "50px";
            ul[i].style.height = "340px";
            p[i].style.width = w - 700 + "px";
            button2[i].style.right = 60 + "px";
        } else if (w > 720) {
            div[i].style.position = "absolute";
            div[i].style.left = "40px";
            div[i].style.top = "285px";
            ul[i].style.height = "500px";
            p[i].style.width = w - 300 + "px";
            button2[i].style.right = 120 + "px";
        } else {
            div[i].style.position = "absolute";
            div[i].style.left = "40px";
            div[i].style.top = "285px";
            ul[i].style.height = "500px";
            p[i].style.width = "420px";
            button2[i].style.right = 120 + "px";
        }
    }
}

window.addEventListener("resize", resize);