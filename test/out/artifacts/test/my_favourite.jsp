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
    <link rel="stylesheet" href="css/my_favourite.css">
    <title>my favourite</title>
    <script src="js/my_favourite.js"></script>
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
                request.getRequestDispatcher("myFavouriteServlet").forward(request, response);
            }
        %>
    </header>
    <br><br><br><br>

    <filter id="filter">
        <!-- 图片展示 -->
        <ul class="first">我的收藏</ul>

        <c:choose>
            <c:when test="${requestScope.rows>0}">
                <c:forEach items="${requestScope.cnt}" var="i" varStatus="s">
                    <c:if test="${s.first}">
                        <div id="result${i}">
                            <c:forEach items="${requestScope.imgs}" var="ele" begin="${i*5-5}" end="${i*5-1}">
                                <ul class='photo' name='ul'>
                                    <p style='position: relative;left: 25px;top: 25px;'>
                                        <a href='./details.jsp?id=${ele.id}'><img src='./img/travel-images/small/${ele.path}'
                                                                                  onload='Zoom(this,400,250)' name='img' alt="myfavourite"></a>
                                    </p>
                                    <div name='div'>
                                        <a href='./details.jsp?id=${ele.id}'>${ele.title}
                                        </a><br><br>
                                        <p class='content' name='p'>${ele.description}
                                        </p>
                                    </div>
                                    <a href='favourServlet?id=${ele.id}' class='button'
                                       style='right: 280px; color: #ff0000;'
                                       name='button2'>删除</a>
                                </ul>
                            </c:forEach>
                        </div>
                    </c:if>
                    <c:if test="${!s.first}">
                        <div id="result${i}" style="display: none">
                            <c:forEach items="${requestScope.imgs}" var="ele" begin="${i*5-5}" end="${i*5-1}">
                                <ul class='photo' name='ul'>
                                    <p style='position: relative;left: 25px;top: 25px;'>
                                        <a href='./details.jsp?id=${ele.id}'><img src='./img/travel-images/small/${ele.path}'
                                                                                  onload='Zoom(this,400,250)' name='img' alt="myfavourite"></a>
                                    </p>
                                    <div name='div'>
                                        <a href='./details.jsp?id=${ele.id}'>${ele.title}
                                        </a><br><br>
                                        <p class='content' name='p'>${ele.description}
                                        </p>
                                    </div>
                                    <a href='favourServlet?id=${ele.id}' class='button'
                                       style='right: 280px; color: #ff0000;'
                                       name='button2'>删除</a>
                                </ul>
                            </c:forEach>
                        </div>
                    </c:if>
                </c:forEach>

            </c:when>
            <c:otherwise>
                <ul>你还没有收藏图片！</ul>
            </c:otherwise>
        </c:choose>

        <ul class='last' style='border-top: none; height: 50px;'>
            <!-- 切换页面 -->
            <div style='text-align: center;'>
                <span id='span'>
                    <c:forEach items="${requestScope.cnt}" var="ele" varStatus="s">
                        <c:if test="${s.first}">
                            <a href="javascript:changePage(${ele})">&lt&lt</a>
                            <a id="now" href="javascript:changePage(${ele})">${ele}</a>
                        </c:if>
                        <c:if test="${!s.first}">
                            <a id="page${ele}" href="javascript:changePage(${ele})">${ele}</a>
                        </c:if>
                        <c:if test="${s.last}">
                            <a href="javascript:changePage(${ele})">&gt&gt</a>
                        </c:if>
                    </c:forEach>
                </span>
            </div>
        </ul>
    </filter>

    <filter id="filter1">
        <ul class="first">我的足迹</ul>
        <c:choose>
            <c:when test="${requestScope.count>0}">
                <c:forEach items="${requestScope.titles}" var="ele" varStatus="s">
                    <c:if test="${s.last}">
                        <ul name='ul' class="last">
                            <p style='position: relative;left: 0px;top: 0px;'>
                                <a href='details.jsp?id=${ele.id}'>${ele.title}</a>
                            </p>
                        </ul>
                    </c:if>
                    <c:if test="${!s.last}">
                        <ul name='ul'>
                            <p style='position: relative;left: 0px;top: 0px;'>
                                <a href='details.jsp?id=${ele.id}'>${ele.title}</a>
                            </p>
                        </ul>
                    </c:if>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <ul class="last">你最近还没有浏览过图片！</ul>
            </c:otherwise>
        </c:choose>
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