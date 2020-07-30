<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/login.css">
    <title>login</title>
    <script src="js/login.js"></script>
    <script src="js/jquery.js"></script>
    <script src="js/common.js"></script>
</head>

<body>
<div class='box'>
    <section>
        <img src="./img/common/default.png" width="80" height="80" alt="default">
        <div>———登录———</div>
        <%
            if (request.getAttribute("type") == "1")
                out.print("<script type=\"text/javascript\">alert(\"用户名或密码错误,请重新输入！\");</script>");
            if (request.getAttribute("type") == "2")
                out.print("<script type=\"text/javascript\">alert(\"用户名或密码错误,请重新输入！\");</script>");
        %>
        <form name="login" action="loginServlet" method="POST" onsubmit="">
            <label>
                <p>用户名</p>
                <input class="normal" type="text" name="username" id="username" autocomplete="off"
                       value="${param.username}" required><br>
            </label>
            <br>
            <label>
                <p>密码<small style="right: 0px;">&#12288&#12288<%-- <a href="change_password.jsp">修改密码</a> --%></small>
                </p>
                <input class="normal" type="password" name="password" id="password" autocomplete="off"
                       value="${param.password}" required><br>
            </label>
            <%--                <small style="right: 0px;">忘记密码了？<a href="find_password.jsp">点击这里找回</a></small>--%>
            <br>
            <input type="submit" value="登录" name="submit" style="height: 40px;">
        </form>
        <small>第一次登录？<a href="register.jsp">点击这里注册</a></small><br>
        <small>&#12288&#12288&#12288<a href="home.jsp">直接访问首页</a></small>
    </section>
</div>
<footer>
    <br><br>Copyright © 2019-2021 Web fundamental. All Rights Reserved. 备案号：19302010012
    <br>所有图片以及数据，均已进入幻想。
</footer>
</body>

<script type="text/javascript">
    (function (exports) {
        function valOrFunction(val, ctx, args) {
            if (typeof val == "function") {
                return val.apply(ctx, args);
            } else {
                return val;
            }
        }

        function InvalidInputHelper(input, options) {
            input.setCustomValidity(valOrFunction(options.defaultText, window, [input]));

            function changeOrInput() {
                if (input.value == "") {
                    input.setCustomValidity(valOrFunction(options.emptyText, window, [input]));
                } else {
                    input.setCustomValidity("");
                }
            }

            function invalid() {
                if (input.value == "") {
                    input.setCustomValidity(valOrFunction(options.emptyText, window, [input]));
                } else {
                    input.setCustomValidity(valOrFunction(options.invalidText, window, [input]));
                }
            }

            input.addEventListener("change", changeOrInput);
            input.addEventListener("input", changeOrInput);
            input.addEventListener("invalid", invalid);
        }

        exports.InvalidInputHelper = InvalidInputHelper;
    })(window);


    InvalidInputHelper(document.getElementById("username"), {
        defaultText: "请输入用户名！",
        emptyText: "请输入用户名！",
        invalidText: function (input) {
            return '这个用户名"' + input.value + '"是不合法的或未被注册！';
        }
    });
    InvalidInputHelper(document.getElementById("password"), {
        defaultText: "请输入密码！",
        emptyText: "请输入密码！",
        invalidText: function (input) {
            return "不合法的密码！";
        }
    });
</script>

</html>