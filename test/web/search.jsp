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
    <link rel='stylesheet' href='css/search.css'>
    <title>search</title>
    <script src='js/search.js'></script>
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
                request.getRequestDispatcher("searchServlet").forward(request, response);
            }
        %>
    </header>
    <br><br><br><br>

    <filter id='filter'>
        <!-- 搜索框 -->
        <ul class='first'>搜索</ul>
        <ul class='last' style='height: fit-content;'>
            <form name='form1' method='POST' action=''>
                选择过滤方式：
                <c:choose>
                    <c:when test="${param.filter==\"content\"}">
                        <label><input name='filter' type='radio' value="title">按标题过滤</label>
                        <label><input name='filter' type='radio' value="content" checked>按主题过滤</label>
                    </c:when>
                    <c:otherwise>
                        <label><input name='filter' type='radio' value="title" checked>按标题过滤</label>
                        <label><input name='filter' type='radio' value="content">按主题过滤</label>
                    </c:otherwise>
                </c:choose>
                <br><br>
                选择排序方式：
                <c:choose>
                    <c:when test="${param.type==\"time\"}">
                        <label><input name='type' type='radio' value="favour">按热度排序</label>
                        <label><input name='type' type='radio' value="time" checked>按时间排序</label>
                    </c:when>
                    <c:otherwise>
                        <label><input name='type' type='radio' value="favour" checked>按热度排序</label>
                        <label><input name='type' type='radio' value="time">按时间排序</label>
                    </c:otherwise>
                </c:choose>
                <br><br>
                请输入要过滤的内容：
                <br>
                <label>
                    <textarea name="content" type="text">${param.content}</textarea>
                </label>
                <input type='submit' value='过滤'>
            </form>
        </ul>
        <br><br>
        <ul class='first'>搜索结果</ul>

        <c:choose>
            <c:when test="${requestScope.rows>0}">
                <c:forEach items="${requestScope.cnt}" var="i" varStatus="s">
                    <c:if test="${s.first}">
                        <div id="result${i}">
                            <c:forEach items="${requestScope.imgs}" var="ele" begin="${i*5-5}" end="${i*5-1}">
                                <ul class='result' name='ul'>
                                    <p style='position: relative;left: 25px;top: 25px;'>
                                        <a href='./details.jsp?id=${ele.id}'><img
                                                src='./img/travel-images/small/${ele.path}'
                                                onload='Zoom(this,400,250)' name='img' alt="detail"></a>
                                    </p>
                                    <div name='div'>
                                        <a href='./details.jsp?id=${ele.id}'>${ele.title}
                                        </a><br><br>
                                        <p class='content' name='p'>${ele.description}
                                        </p>
                                    </div>
                                </ul>
                            </c:forEach>
                        </div>
                    </c:if>
                    <c:if test="${!s.first}">
                        <div id="result${i}" style="display: none">
                            <c:forEach items="${requestScope.imgs}" var="ele" begin="${i*5-5}" end="${i*5-1}">
                                <ul class='result' name='ul'>
                                    <p style='position: relative;left: 25px;top: 25px;'>
                                        <a href='./details.jsp?id=${ele.id}'><img
                                                src='./img/travel-images/small/${ele.path}'
                                                onload='Zoom(this,400,250)' name='img' alt="detail"></a>
                                    </p>
                                    <div name='div'>
                                        <a href='./details.jsp?id=${ele.id}'>${ele.title}
                                        </a><br><br>
                                        <p class='content' name='p'>${ele.description}
                                        </p>
                                    </div>
                                </ul>
                            </c:forEach>
                        </div>
                    </c:if>
                </c:forEach>

            </c:when>
            <c:otherwise>
                <ul>该分类下没有图片！</ul>
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