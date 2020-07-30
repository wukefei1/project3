<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="navigator" uri="http://mycompany.com" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <link rel='stylesheet' href='css/reset.css'>
    <link rel='stylesheet' href='css/common.css'>
    <link rel='stylesheet' href='css/chat.css'>
    <title>chat</title>
    <script src='js/chat.js'></script>
    <script src='js/jquery.js'></script>
    <script src='js/common.js'></script>
</head>

<body onload='resize()'>
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
            <li><a class='navigation' href='./search.jsp' id="chosen">搜索</a></li>
        </ul>
        <navigator:getNavigator/>
        <%
            if (request.getAttribute("load") != "1") {
                request.getRequestDispatcher("chatServlet").forward(request, response);
            }
        %>
    </header>
    <br><br><br><br>

    <filter id='filter'>
        <!-- 搜索框 -->
        <ul class='first'>和${requestScope.chatUser}聊天</ul>
        <ul>
            <div id="console">
                <c:if test="${requestScope.rows>0}">
                    <c:forEach var="ele" items="${requestScope.message}">
                        <li>
                                ${requestScope.chatUser}对你说：<p>&#12288&#12288 ${ele}</p>
                        </li>
                    </c:forEach>
                </c:if>
            </div>
        </ul>
        <ul class="last">
            <textarea placeholder="按下发送发送消息" id="message"></textarea>
            <button onclick="sendMessage()">发送</button>
        </ul>
    </filter>
    <script>
        function refresh() {
            $.ajax({
                url: "refreshServlet",
                data: {id: "${param.id}"},
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.rows !== "0" && data) {
                        for (var i = 0; i < data.rows; i++) {
                            document.getElementById("console").innerHTML += "<li>\n" +
                                "                                ${requestScope.chatUser}对你说：<p>&#12288&#12288 " + data.msg["message" + i] + "</p>\n" +
                                "                        </li>";
                        }
                    }
                }
            })
        }

        setInterval(function () {
            refresh();
        }, 1000);

        function sendMessage() {
            if (document.getElementById("message").value.length === 0){
                alert("你还没有输入！请重新输入！");
            }
            else if (document.getElementById("message").value.length < 150) {
                $.ajax({
                    url: "sendMessageServlet",
                    data: {id: "${param.id}", m: document.getElementById("message").value},
                    type: "post",
                })
                document.getElementById("console").innerHTML += "<li>\n" +
                    "                                你对${requestScope.chatUser}说：<p>&#12288&#12288 " + document.getElementById("message").value + "</p>\n" +
                    "                        </li>";

                document.getElementById("message").value = "";
                document.getElementById("console").scrollTop = document.getElementById("console").scrollHeight;
            } else {
                alert("你输入的内容过长！请重新输入！");
            }
        }
    </script>


    <div id='back-to-top' class='top_e'>
        <img src='./img/common/totop.png' width='40' height='40' id='img' alt="totop">
    </div>
    <!-- <div id='refresh' class='refresh_e'>
        <img src='../img/common/refresh.png' width='40' height='40' id='img' onclick='alert('图片已更新')'>
    </div> -->

    <footer>
        <br><br>Copyright © 2019-2021 Web fundamental. All Rights Reserved. 备案号：19302010012
        <br>所有图片以及数据，均已进入幻想。
    </footer>
</div>
</body>

</html>