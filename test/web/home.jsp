<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="navigator" uri="http://mycompany.com" %>
<!DOCTYPE html>
<html lang='zh-CN'>

<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <link rel='stylesheet' href='css/reset.css'>
    <link rel='stylesheet' href='css/common.css'>
    <link rel='stylesheet' href='css/home.css'>
    <title>home</title>

    <!-- <script type='text/javascript'>
        //事件一
        function
        myfunction(obj, type, fn) {
            if (obj.attachEvent) {
                obj.attachEvent('on' + type, function () {
                    fn.call(obj);
                })
            } else {
                obj.addEventListener(type, fn, false);
            }
        }
        myfunction(window, 'scroll', function () {
            var
                tymnc =
                document.documentElement.scrollTop ||
                document.body.scrollTop;
            var
                mydiv =
                document.getElementById('test');
            if (tymnc >= 100) {
                mydiv.style.position = 'fixed';
                mydiv.style.top = '100px';
                mydiv.style.right = '100px';
            } else {
                mydiv.style.position = 'relative';
                mydiv.style.top = '0px';
                mydiv.style.right = '-60px';
            }
        });
    </script> -->
    <script src='js/home.js'></script>
    <script src='js/jquery.js'></script>
    <script src='js/common.js'></script>
</head>

<body onload='load()'>
<div class='box'>
    <header class='nav' id='nav'>
        <!-- 导航栏 -->
        <ul>
            <li>
                <a class='navigation' href='./home.jsp'>
                    <img src='img/common/default.png' width='30' height='30' alt="default">
                </a>
            </li>
            <li><a class='navigation' href='./home.jsp' id="chosen">首页</a></li>
            <li><a class='navigation' href='./search.jsp'>搜索</a></li>
        </ul>
        <navigator:getNavigator/>
        <%
            if (request.getAttribute("load") != "1") {
                request.getRequestDispatcher("homeServlet").forward(request, response);
            }
//            else {
//                Travelimage img = (Travelimage) request.getAttribute("toutu0");
//                System.out.println(img.getPath());
//            }
        %>
    </header>

    <div class="container">
        <div class="wrap" style="left: -1520px;">
            <c:forEach items="${requestScope.toutu}" var="ele" varStatus="s">
                <c:if test="${s.last}">
                    <a href='details.jsp?id=${ele.id}'>
                        <img id='toutu' src='img/travel-images/large/${ele.path}' alt='${s.index+1}' width="1520"
                             height="1140"></a>
                </c:if>
            </c:forEach>

            <c:forEach items="${requestScope.toutu}" var="ele" varStatus="s">
                <a href='details.jsp?id=${ele.id}'>
                    <img id='toutu' src='img/travel-images/large/${ele.path}' alt='${s.index+1}' width="1520"
                         height="1140"></a>
            </c:forEach>

            <c:forEach items="${requestScope.toutu}" var="ele" varStatus="s">
                <c:if test="${s.first}">
                    <a href='details.jsp?id=${ele.id}'>
                        <img id='toutu' src='img/travel-images/large/${ele.path}' alt='${s.index+1}' width="1520"
                             height="1140"></a>
                </c:if>
            </c:forEach>
        </div>
        <div class="buttons">
            <c:forEach items="${requestScope.toutu}" var="ele" varStatus="s">
                <c:if test="${s.first}">
                    <span class="on">${s.index+1}</span>
                </c:if>
            </c:forEach>
            <c:forEach begin="1" items="${requestScope.toutu}" var="ele" varStatus="s">
                <span>${s.index+1}</span>
            </c:forEach>
        </div>
    </div>

    <!-- 头图 -->

    <table id='table'>

        <tr>
            <c:forEach items="${requestScope.show0}" var="ele">
                <td class='show' name='td'>
                    <div name='div'>
                        <a href='details.jsp?id=${ele.id}'><img src='./img/travel-images/small/${ele.path}'
                                                                onload='Zoom(this,350,280)' name='img' alt="show"></a>
                    </div>
                    <br>
                    <a href='details.jsp?id=${ele.id}' class='title' name='a'>${ele.title}</a>
                    <p class='content' name='p'>${ele.description}</p>
                </td>
            </c:forEach>
        </tr>
        <tr>
            <c:forEach items="${requestScope.show1}" var="ele">
                <td class='show' name='td'>
                    <div name='div'>
                        <a href='details.jsp?id=${ele.id}'><img src='./img/travel-images/small/${ele.path}'
                                                                onload='Zoom(this,350,280)' name='img' alt="show"></a>
                    </div>
                    <br>
                    <a href='details.jsp?id=${ele.id}' class='title' name='a'>${ele.title}</a>
                    <p class='content' name='p'>${ele.description}</p>
                </td>
            </c:forEach>
        </tr>
        <!-- 图片展示 -->

    </table>

    <div id='back-to-top' class='top_e'>
        <img src='img/common/totop.png' width='40' height='40' id='img' alt="totop">
    </div>
    <!-- 返回顶部 -->
    <%--        <div id='refresh' class='refresh_e'>--%>
    <%--            <img src='img/common/refresh.png' width='40' height='40' id='img' onclick='javascript:refresh()'>--%>
    <%--            <script type="text/javascript">--%>
    <%--                function refresh() {--%>
    <%--                    document.location.href = "?type=refresh";--%>
    <%--                }--%>
    <%--            </script>--%>
    <%--        </div>--%>
    <%--        <!-- 刷新 -->--%>
    <footer>
        Copyright © 2019-2021 Web fundamental. All Rights Reserved. 备案号：19302010012
        <br>所有图片以及数据，均已进入幻想。
    </footer>
</div>


</body>

</html>