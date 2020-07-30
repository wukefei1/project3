<%--
  Created by IntelliJ IDEA.
  User: wukefei
  Date: 2020/7/6
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/register.css">
    <title>register</title>
    <script src="js/register.js"></script>
    <script src="js/jquery.js"></script>
    <script src="js/common.js"></script>
</head>

<body>
<div class='box'>
    <section>
        <img src="img/common/default.png" width="80" height="80" alt="default">
        <div>———注册———</div>
        <%
            if (request.getAttribute("type") == "1")
                out.print("<script type=\"text/javascript\">alert(\"两次输入的密码不一致,请重新输入！\");</script>");
            if (request.getAttribute("type") == "2")
                out.print("<script type=\"text/javascript\">alert(\"用户名或邮箱已存在,请重新输入！\");</script>");
            if (request.getAttribute("type") == "3")
                out.print("<script type=\"text/javascript\">alert(\"验证码错误,请重新输入！\");</script>");
        %>
        <script type="text/javascript">
            $(function () {
                if ("${param.type}" === "1")
                    alert("两次输入的密码不一致,请重新输入！");
            });

            function _change() {
                var imgEle = document.getElementById("vCode");
                imgEle.src = "VerifyCodeServlet?" + new Date().getTime();
            }
        </script>
        <form name="register" action="registerServlet" method="POST">
            <label>
                <p>请设置用户名</p>
                <input class="normal" type="text" name="username" id="username" placeholder="4-15位字符" autocomplete="off"
                       pattern="^[a-zA-Z0-9_-.]{4,15}$" value="${param.username}"
                       required><br>
            </label>
            <br>
            <label>
                <p>请设置邮箱</p>
                <input class="normal" type="text" name="email" id="email" placeholder="" autocomplete="off"
                       pattern="^([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22))*\x40([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d))*(\.\w{2,})+$"
                       value="${param.email}" required><br>
            </label>
            <br>
            <label>
                <p id="tips">请设置密码
                    <span>弱</span>
                    <span>中</span>
                    <span>强</span>
                </p>
                <input class="normal" type="password" name="pw1" id="pw1" placeholder="6-12位数字、字母"
                       pattern="^[[a-zA-z0-9]{6,12}" value="${param.pw1}" required><br>
            </label>
            <br>
            <label>
                <p>请确认密码</p>
                <input class="normal" type="password" name="pw2" id="pw2" placeholder="6-12位数字、字母"
                       pattern="^[[a-zA-z0-9]{6,12}" value="${param.pw2}" required><br>
            </label>
            <br>
            <label>
                <p>请输入验证码 &#12288 &#12288<img id="vCode" src="VerifyCodeServlet" style="right: 00px" alt="verifyimg"/>
                    <small><a href="javascript:_change()">换一张</a></small>
                </p>
                <input class="normal" autocomplete="off" type="text" name="code" id="code" placeholder="" required><br>
            </label>
            <br>
            <input type="submit" value="注册" name="submit" style="height: 40px;">
        </form>
        <small>已有账户？<a href="login.jsp">点击这里登录</a></small>
    </section>
    <script>
        var password = document.getElementById("pw1"); //获取文本框的对象
        //获取所有的span标签 返回值是一个数组
        var spanDoms = document.getElementsByTagName("span");
        password.onkeyup = function () {
            //获取用户输入的密码,然后判断其强度 返回0 或者 1 2 3 4
            var index = checkPassWord(this.value);
            for (var i = 0; i < spanDoms.length; i++) {
                spanDoms[i].className = "";//清空span标签所有的class样式
                if (index) {//0 代表false 其余代表true
                    spanDoms[index - 1].className = "s" + index;
                }
            }
        }

        //校验密码强度
        function checkPassWord(value) {
            // 0： 表示第一个级别 1：表示第二个级别 2：表示第三个级别
            // 3： 表示第四个级别 4：表示第五个级别
            var modes = 0;
            if (value.length < 6) {//最初级别
                return 0;
            }
            if (value.length > 7) {
                modes++;
            }
            if (value.length > 9) {
                modes++;
            }
            if (/\d/.test(value)) {//如果用户输入的密码 包含了数字
                modes++;
            }
            if (/[a-z]/.test(value)) {//如果用户输入的密码 包含了小写的a到z
                modes++;
            }
            if (/[A-Z]/.test(value)) {//如果用户输入的密码 包含了大写的A到Z
                modes++;
            }
            if (/\W/.test(value)) {//如果是非数字 字母 下划线
                modes++;
            }
            return Math.floor((modes + 1) / 2);
        }
    </script>
</div>
<footer>
    <br><br>Copyright © 2019-2021 Web fundamental. All Rights Reserved. 备案号：19302010012
    <br>所有图片以及数据，均已进入幻想。
</footer>
</body>

</html>
