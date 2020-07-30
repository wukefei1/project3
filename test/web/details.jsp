<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="navigator" uri="http://mycompany.com" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang='zh-CN'>

<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <link rel='stylesheet' href='css/reset.css'>
    <link rel='stylesheet' href='css/common.css'>
    <link rel='stylesheet' href='css/details.css'>
    <title>details</title>
    <script src='js/details.js'></script>
    <script src='js/jquery.js'></script>
    <script src='js/common.js'></script>
</head>

<body onload='resize()'>
<div class='box' id='others'>
    <header class='nav' id='nav'>
        <!-- 导航栏 -->
        <ul>
            <li>
                <a class='navigation' href='./home.jsp'>
                    <img src='img/common/default.png' width='30' height='30' alt="default">
                </a>
            </li>
            <li><a class='navigation' href='./home.jsp'>首页</a></li>
            <li><a class='navigation' href='./search.jsp'>搜索</a></li>
        </ul>
        <navigator:getNavigator/>
        <%
            if (request.getAttribute("load") != "1") {
                request.getRequestDispatcher("detailServlet").forward(request, response);
            }
        %>
    </header>
    <br><br><br><br>

    <filter id='filter'>
        <!-- 详细信息 -->
        <ul class='first'>详细信息</ul>
        <ul class='last photo' id='photo'>
            <br>
            <jsp:useBean id="details" scope="request" class="Entity.Travelimage"/>
            <h1>
                <jsp:getProperty name="details" property="title"/>
                <br>
                <small>by
                    <jsp:getProperty name="details" property="author"/>
                    on
                    <jsp:getProperty name="details" property="submitTime"/>
                </small>
            </h1>


            <div class="min">
                <a>
                    <img src='img/travel-images/large/<jsp:getProperty name="details" property="path"/>'
                         onload='Zoom(this,540,360)'
                         onclick='showImage(this.src)' id="detailimg" alt="detailimg">
                    <div class="fd"></div>
                </a>
            </div>
            <div class="max">
                <img src='img/travel-images/large/<jsp:getProperty name="details" property="path"/>' alt="maximg">
            </div>
            <p id='hint'>点击图片以查看原图</p>
            <ul class='first' name='ul'>收藏数</ul>
            <ul class='last' name='ul' style='height: 30px;text-align: center;font: 24px Verdana;color: #ff0000;'>
                <jsp:getProperty name="details" property="likeNum"/>
            </ul>
            <br>
            <ul class='first' name='ul'>图片详细信息</ul>
            <ul name='ul'>内容：
                <jsp:getProperty name="details" property="content"/>
            </ul>
            <ul name='ul'>国家：
                <jsp:getProperty name="details" property="countryName"/>
            </ul>
            <ul name='ul' class='last'>城市：
                <jsp:getProperty name="details" property="cityName"/>
            </ul>
            <br>
            <ul name='ul' class='like'>
                <a href='favourServlet?id=<jsp:getProperty name="details" property="id"/>'>
                    <img src='img/common/test.png' width='20' height='20' style='position: relative;top: -3px' alt="love">
                    <c:choose>
                        <c:when test="${requestScope.details.isFavourite}">取消收藏</c:when>
                        <c:otherwise>点击收藏</c:otherwise>
                    </c:choose>
                </a>
            </ul>
            <p class='content' id='p'>
                <jsp:getProperty name="details" property="description"/>
            </p>
        </ul>
        <script>
            var min = document.querySelector(".min");
            var max = document.querySelector(".max");
            var img = document.querySelector(".max img");
            var fd = document.querySelector(".fd");

            min.onmouseover = function () {
//                1.鼠标覆盖显示max和fd
                max.style.display = "block";
                fd.style.display = "block";
            }
            //            离开时隐藏
            min.onmouseout = function () {
                max.style.display = "none";
                fd.style.display = "none";
            }
            //            2. fd的移动范围
            min.onmousemove = function () {
//                鼠标触摸的点
                var x = event.clientX - min.offsetLeft - fd.offsetWidth / 2;
                var y = event.clientY - min.offsetTop - fd.offsetHeight / 2;
//                最大移动距离
                var maxX = min.clientWidth - fd.offsetWidth;
                var maxY = min.clientHeight - fd.offsetHeight;
//                边界判断
                if (x <= 0) {
                    x = 0;
                } else if (x >= maxX) {
                    x = maxX;
                }
                if (y <= 0) {
                    y = 0;
                } else if (y >= maxY) {
                    y = maxY;
                }
//                fd的位置
                fd.style.left = x + 'px';
                fd.style.top = y + 'px';
                //fd/min = max/img
                //移动比例
                var moveX = x / maxX;
                var moveY = y / maxY;
//                移动
//                3. max的对应显示
//                对于大图而言,放大镜在小图上移动的比例相当于img在可显示区域上移动的比例
//                放大镜右移等于图片左移
//                也就是本质上为img-max 然而而需要负值,则*-1简化后为max-img
                img.style.left = moveX * (max.clientWidth - img.offsetWidth) + 'px';
                img.style.top = moveY * (max.clientHeight - img.offsetHeight) + 'px';
            }
        </script>
    </filter>

    <div id='back-to-top' class='top_e'>
        <img src='./img/common/totop.png' width='40' height='40' id='img' alt="totop">
    </div>
    <!-- <div id='refresh' class='refresh_e'>
        <img src='../img/common/refresh.png' width='40' height='40' id='img' onclick='alert("图片已更新")'>
    </div> -->

    <footer>
        <br><br>Copyright © 2019-2021 Web fundamental. All Rights Reserved. 备案号：19302010012
        <br>所有图片以及数据，均已进入幻想。
    </footer>
</div>
<a><img src='' onclick='closeImage()' id='yuantu'></a>
<p id='hidden' hidden>点击图片以返回</p>
</body>

</html>