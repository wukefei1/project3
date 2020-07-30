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
    <link rel='stylesheet' href='css/upload.css'>
    <title>upload</title>
    <script src='js/upload.js'></script>
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
            <li><a class='navigation' href='./search.jsp'>搜索</a></li>
        </ul>
        <navigator:getNavigator/>
        <%
            if (request.getAttribute("load") != "1") {
                request.getRequestDispatcher("uploadServlet").forward(request, response);
            }
        %>
    </header>
    <br><br><br><br>

    <filter id='filter'>
        <c:if test="${requestScope.type==1}">
            <c:forEach items="${requestScope.imgs}" var="ele">
                <ul class='last' style='height: 1000px;'>
                    <form enctype='multipart/form-data' name='upload_form' action='uploadJudgeServlet?id=${ele.id}' id="upload"
                          method='POST'>
                        <div>
                            <a><img src='img/travel-images/large/${ele.path}' id='img0' onload='Zoom(this,570,380)'
                                    onclick='showImage(this.src)'>
                            </a>
                        </div>
                        <p id='hint'>点击图片以查看原图</p>
                        <p id='fileName'>${ele.path}</p>
                        <label for='file0' id='upFile'>上传文件</label>
                        <input type='file' name='file0' id='file0' accept='image/jpeg,image/jpg,image/png'
                               onchange='getImg()' hidden>
                        <br><br>
                        <label>
                            图片标题：<br>
                            <input class='normal' type='text' name='title' id='title' value='${ele.title}' required>
                        </label>
                        <br><br>
                        <label>
                            图片描述：<br>
                            <textarea class='normal' type='text' name='description' id='description'
                                      required>${ele.description}</textarea>
                        </label>
                        <br><br>
                        <select name='content' id='content'>
                            <option value="">请选择主题</option>
                            <c:forEach items="${requestScope.contents}" var="content">
                                <c:if test="${ele.content==content}">
                                    <option value="${content}" selected>${content}</option>
                                </c:if>
                                <c:if test="${ele.content!=content}">
                                    <option value="${content}">${content}</option>
                                </c:if>
                            </c:forEach>
                        </select>&#12288
                        <select id="country" name="country">
                            <option value="">请选择国家</option>
                            <c:forEach items="${requestScope.countries}" var="country">
                                <c:if test="${ele.countryISO==country.countryISO}">
                                    <option value="${country.countryISO}" selected>${country.country_RegionName}</option>
                                </c:if>
                                <c:if test="${ele.countryISO!=country.countryISO}">
                                    <option value="${country.countryISO}">${country.country_RegionName}</option>
                                </c:if>
                            </c:forEach>
                        </select>&#12288
                        <select id="city" name="city">
                            <c:if test="${ele.cityCode==\"\"}">
                                <option value="" selected>请选择地区</option>
                            </c:if>
                            <c:if test="${ele.cityCode!=\"\"}">
                                <option value="">请选择地区</option>
                            </c:if>
                            <c:forEach items="${requestScope.cities}" var="city">
                                <c:if test="${ele.cityCode==city.geoNameID}">
                                    <option value="${city.asciiName}" selected>${city.asciiName}</option>
                                </c:if>
                                <c:if test="${ele.cityCode!=city.geoNameID}">
                                    <option value="${city.asciiName}">${city.asciiName}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <script src="js/filter_onchange.js"></script>
                        <br><br>
                        <input type='submit' value='提交' name='submit'>
                    </form>
                </ul>
            </c:forEach>
        </c:if>

        <c:if test="${requestScope.type==0}">
            <ul class='last' style='height: 600px;'>
                <form enctype='multipart/form-data' name='upload_form' action='uploadJudgeServlet' id="upload" method='POST'>
                    <div>
                        <a><img src='' id='img0' onload='Zoom(this,570,380)' onclick='showImage(this.src)'>
                        </a>
                    </div>
                    <p id='hint' hidden>点击图片以查看原图</p>
                    <p id='fileName'></p>
                    <label for='file0' id='upFile'>上传文件</label>
                    <input type='file' name='file0' id='file0' accept='image/jpeg,image/jpg,image/png'
                           onchange='getImg()' hidden>
                    <br><br>
                    <label>
                        图片标题：<br>
                        <input class='normal' type='text' name='title' id='title' value='' required>
                    </label>
                    <br><br>
                    <label>
                        图片描述：<br>
                        <textarea class='normal' type='text' name='description' id='description' required></textarea>
                    </label>
                    <br><br>
                    <select name='content' id='content'>
                        <option value="" selected>请选择主题</option>
                        <c:forEach items="${requestScope.contents}" var="ele">
                            <option value="${ele}">${ele}</option>
                        </c:forEach>
                    </select>&#12288&#12288
                    <select id="country" name="country">
                        <option value="" selected>请选择国家</option>
                        <c:forEach items="${requestScope.countries}" var="ele">
                            <option value="${ele.countryISO}">${ele.country_RegionName}</option>
                        </c:forEach>
                    </select>&#12288&#12288
                    <select id="city" name="city">
                        <option value="" selected>请选择地区</option>
                    </select>
                    <script src="js/filter_onchange.js"></script>
                    <br><br>
                    <input type='submit' value='提交' name='submit'>
                </form>
            </ul>
        </c:if>
    </filter>

    <script>
        document.getElementById("upload").onsubmit = function (e) {
            if (!confirm("请确认信息无误")) e.preventDefault();
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