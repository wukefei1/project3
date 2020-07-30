function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

$(document).ready(function () {
    //回到顶部的动画效果
    //首先将#back-to-top隐藏
    $("#back-to-top").hide();
    //当滚动条的位置处于距顶部600像素以下时，跳转链接出现，否则消失
    $(function () {
        $(window).scroll(function () {
            if ($(window).scrollTop() > 300) {
                $("#back-to-top").fadeIn(500);
            } else {
                $("#back-to-top").fadeOut(500);
            }
        });
        //当点击跳转链接后，回到页面顶部位置
        $("#back-to-top").click(function () {
            $('body,html').animate({
                scrollTop: 0
            }, 500);
            return false;
        });
    });
});

function changePage(str) {
    //切换页面
    var now = document.getElementById("now");
    if (now.innerHTML.toString !== str) {
        now.id = "page" + now.innerHTML;
        now.style.border.style = "1px solid #eaeaea;";
        var s = "page" + str;
        var after = document.getElementById(s);
        after.id = "now";
        after.style.border.style = "none";
    }
    for (var i = 1; ; i++) {
        var result = document.getElementById("result" + i);
        if (result != null) {
            result.style.display = "none";
            if (i === str) {
                console.log(i);
                result.style.display = "inline";
            }
        } else break;
    }
}

function Zoom(obj, width, height) {
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

function showImage(src) {
    //查看原图
    var div = document.getElementById("others");
    div.hidden = "hidden";
    var img = document.getElementById("yuantu");
    img.src = src;
    var h = document.getElementById("hidden");
    h.removeAttribute("hidden");
}

function closeImage() {
    //关闭原图
    var div = document.getElementById("others");
    div.removeAttribute("hidden");
    var img = document.getElementById("yuantu");
    img.src = "";
    var h = document.getElementById("hidden");
    h.hidden = "hidden";
}