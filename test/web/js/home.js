function resize() {
    //响应式布局
    var w = document.documentElement.clientWidth;
    var h = document.documentElement.clientHeight;

    var nav = document.getElementById("nav");
    if (w < 720) {
        nav.style.width = "720px";
    } else {
        nav.style.width = "100%";
    }

    var p = document.getElementsByName("p");
    var a = document.getElementsByName("a");
    var img = document.getElementsByName("img");
    var table = document.getElementById("table");
    var td = document.getElementsByName("td");
    var div = document.getElementsByName("div");
    const scale = w / 1520;

    table.style.borderSpacing = 40 * scale + "px";
    table.style.right = -80 * scale + "px";
    for (var i = 0; i < p.length; i++) {
        p[i].style.fontSize = scale * 20 + "px";
        p[i].style.left = 25 * scale + "px";
        p[i].style.bottom = 25 * scale + "px";
        a[i].style.fontSize = scale * 30 + "px";
        a[i].style.left = 25 * scale + "px";
        a[i].style.bottom = 75 * scale + "px";
        thisZoom(img[i], 350 * scale, 280 * scale);
        td[i].style.minWidth = 400 * scale + "px";
        td[i].style.height = 430 * scale + "px";
        td[i].style.borderRadius = 40 * scale + "px";
        div[i].style.bottom = 40 * scale + "px";
        div[i].style.left = 25 * scale + "px";
        div[i].style.borderRadius = 15 * scale + "px";
    }
}

window.addEventListener("resize", resize);

function thisZoom(obj, width, height) {
    //裁剪图片符合固定长宽
    var scale = Math.max(width / obj.width, height / obj.height);
    var newWidth = obj.width * scale;
    var newHeight = obj.height * scale;
    var div = obj.parentNode.parentNode;

    obj.width = newWidth;
    obj.height = newHeight;
    div.style.width = width + "px";
    div.style.height = height + "px";
    div.style.overflow = "hidden";
    obj.style.marginLeft = (width - newWidth) / 2 + "px";
    obj.style.marginRight = (height - newHeight) / 2 + "px";
}

function load() {
    resize();
    const container = document.getElementsByClassName("container")[0];
    const imgs = document.getElementsByClassName("wrap")[0];
    const buttons = document.getElementsByClassName("buttons")[0].children;

    const width = 1520; //图片宽度
    const interval = 5; //5ms的动画间隔
    const transitionPerPage = width * interval / 1000; //5ms内、变化一页时的变化量
    let currentPage = 1;
    let manualPlay = true;
    let autoPlay = true;

    function setPage(pageNumber) {
        let transition = 0;
        let pageToChange = pageNumber - currentPage;
        let animation = setInterval(function () {
            if (transition < pageToChange * width + 1 && transition > pageToChange * width - 1) {
                clearInterval(animation);
                manualPlay = true;

                buttons[currentPage - 1].className = "";
                currentPage = (pageNumber + buttons.length - 1) % buttons.length + 1;
                buttons[currentPage - 1].className = "on";
            } else {
                transition += pageToChange * transitionPerPage;
                imgs.style.left = (-width * currentPage - transition) + "px";
                manualPlay = false;
            }
        }, interval);
    }

    let animation = setInterval(function () {
        if (manualPlay && autoPlay) {
            setPage(currentPage + 1);
        }
    }, 4000);

    // container.onmouseover = function () {
    //     autoPlay = false;
    // };
    //
    // container.onmouseout = function () {
    //     autoPlay = true;
    // };
    for (let i = 0; i < buttons.length; i++) {
        buttons[i].onclick = function () {
            if (manualPlay) {
                setPage(i + 1);
            }
        }
    }
}



