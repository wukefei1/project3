<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="navigator" uri="http://mycompany.com" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/my_friends.css">
    <title>my friends</title>
    <script src="js/my_friends.js"></script>
    <script src="js/jquery.js"></script>
    <script src="js/common.js"></script>
</head>

<body onload="resize()">
<div class='box'>
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
                request.getRequestDispatcher("myFriendsServlet").forward(request, response);
            }
        %>
    </header>
    <br><br><br><br>

    <filter id="filter">
        <!-- 图片展示 -->
        <ul class="first">我的好友</ul>

        <c:choose>
            <c:when test="${requestScope.rows>0}">
                <c:forEach items="${requestScope.users}" var="ele">
                    <ul class='friend' name='ul'>
                        <h2><a href="friends_favourite.jsp?id=${ele.id}">${ele.name}</a></h2>
                        <br>
                        <p>邮箱：${ele.email}</p>
                        <br>
                        <p>注册时间：${ele.date}</p>
                        <a href='javascript:confirmDelete(${ele.id})' class='button'
                           style='right: 80px; color: red; bottom: 90px;'
                           name='button1'>删除</a>
                        <a href='chat.jsp?id=${ele.id}' class='button' style='right: 80px; color: purple; bottom: 30px'
                           name='button2'>聊天</a>
                    </ul>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <ul>你还没有好友！</ul>
            </c:otherwise>
        </c:choose>


        <ul class='last' style='border-top: none; height: 50px;'>
            <!-- 切换页面 -->
            <div style='text-align: center;'>
                <span id='span'>

                </span>
            </div>
        </ul>
    </filter>
    <div id="back-to-top" class="top_e">
        <img src="./img/common/totop.png" width="40" height="40" id="img" alt="totop">
    </div>
    <!-- <div id="refresh" class="refresh_e">
        <img src="./img/common/refresh.png" width="40" height="40" id="img" onclick="alert('图片已更新')">
    </div> -->
    <footer>
        <br><br>Copyright © 2019-2021 Web fundamental. All Rights Reserved. 备案号：19302010012
        <br>所有图片以及数据，均已进入幻想。
    </footer>
</div>
</body>

</html>