# 卓越软件开发基础project开发文档

19302010012 吴可非

### 整体概述

#### 相关链接

###### github地址
[https://github.com/wukefei1/project3](https://github.com/wukefei1/project3)

###### 公网访问地址
[http://wukefei.xyz:55554/project/home.jsp](http://wukefei.xyz:55554/project/home.jsp)

#### 功能概述
>用户能够注册账、使用已经注册过的账号登录。在未登录前，用户在网站首页可以通过点击查看图片详情，可以在搜索页搜索展示筛选结果；但是不能收藏任何图片，也不能查看自己的好友列表等。在用户登录后，用户可以通过用户名搜索其他用户并发出添加请求，待该用户同意后两者互为好友。用户可以收藏不同的展品，并决定自己的收藏是否向好友公开。如果向好友公开收藏，好友可以互相通过点击好友列表对应用户名来查看其收藏的图片列表。如果不公开，则不展示。

#### 项目结构

```
├─src
│  ├─DB
│  │      DBHelper.java
│  │      Sha256.java
│  │      VerifyCode.java
│  │      
│  ├─Entity
│  │      City.java
│  │      Country.java
│  │      Travelimage.java
│  │      User.java
│  │      
│  ├─net
│  │  └─coobird
│  │                      
│  ├─Servlet
│  │      addUserServlet.java
│  │      changeStateServlet.java
│  │      chatServlet.java
│  │      deleteFriendServlet.java
│  │      deletePhotoServlet.java
│  │      detailServlet.java
│  │      favourServlet.java
│  │      friendFavouriteServlet.java
│  │      homeServlet.java
│  │      loginServlet.java
│  │      logoutServlet.java
│  │      myFavouriteServlet.java
│  │      myFriendsServlet.java
│  │      myPhotosServlet.java
│  │      personalCenterServlet.java
│  │      refreshServlet.java
│  │      registerServlet.java
│  │      responseServlet.java
│  │      searchServlet.java
│  │      sendMessageServlet.java
│  │      uploadJudgeServlet.java
│  │      uploadServlet.java
│  │      VerifyCodeServlet.java
│  │      
│  ├─Tag
│  │      NavigatorList.java
│  │      
│  └─Tools
│          DateRandom.java
│          Delete.java
│          HTMLFilter.java
│          
└─web
    │  chat.jsp
    │  details.jsp
    │  friends_favourite.jsp
    │  home.jsp
    │  index.jsp
    │  login.jsp
    │  my_favourite.jsp
    │  my_friends.jsp
    │  my_photos.jsp
    │  personal_center.jsp
    │  register.jsp
    │  search.jsp
    │  upload.jsp
    ├─css
    ├─img
    ├─js
    └─WEB-INF
```

#### 后端工具

    1. 后端JSP+Servlet+JavaBean的MVC架构实现
    2. 服务器Apache Tomcat，数据库MySQL

#### 前端设计

    沿用之前PJ的设计样式、包括js+css。

#### 数据库结构

```
geocities：储存城市
geocountries_regions：储存国家和地区
message：储存用户之间发送的消息
recentbrowse：储存最近浏览
travelfriends：储存好友
travelimage：储存图片
travelimagefavor：储存收藏
traveluser：储存用户
```


### 项目完成情况

>基础功能全部实现，bonus部分完成情况将在后面写出

#### 首页轮播

    参考了之前做过的lab实现。

#### 分页功能

    获取照片列表后生成每一页，将不展示的页隐藏，按键可以展示对应页。

```html
<c:forEach items="${requestScope.cnt}" var="i" varStatus="s">
    <c:if test="${s.first}">
        <div id="result${i}">
            ...
        </div>
    </c:if>
    <c:if test="${!s.first}">
        <div id="result${i}" style="display: none">
            ...
        </div>
    </c:if>
</c:forEach>
```
```javascript
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
```

### bonus完成情况

#### 项目文档

#### 详情页图片局部放大

    js实现

```javascript
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
```

#### 注册时验证码功能

    通过java生成验证码图片，点击注册时发请求给Servlet判断验证码是否正确。

```java
package DB;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class VerifyCode {
    private final int w = 70;
    private final int h = 35;
    private final Random r = new Random();
    // {"宋体", "华文楷体", "黑体", "华文新魏", "华文隶书", "微软雅黑", "楷体_GB2312"}
    private final String[] fontNames = {"宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};
    // 背景色
    private final Color bgColor = new Color(255, 255, 255);
    // 验证码上的文本
    private String text;

    // 生成随机的颜色
    private Color randomColor() {
        int red = r.nextInt(150);
        int green = r.nextInt(150);
        int blue = r.nextInt(150);
        return new Color(red, green, blue);
    }

    // 生成随机的字体
    private Font randomFont() {
        int index = r.nextInt(fontNames.length);
        String fontName = fontNames[index];//生成随机的字体名称
        int style = r.nextInt(4);//生成随机的样式, 0(无样式), 1(粗体), 2(斜体), 3(粗体+斜体)
        int size = r.nextInt(5) + 24; //生成随机字号, 24 ~ 28
        return new Font(fontName, style, size);
    }

    // 画干扰线
    private void drawLine(BufferedImage image) {
        int num = 3;//一共画3条
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {//生成两个点的坐标，即4个值
            int x1 = r.nextInt(w);
            int y1 = r.nextInt(h);
            int x2 = r.nextInt(w);
            int y2 = r.nextInt(h);
            g2.setStroke(new BasicStroke(1.5F));
            g2.setColor(Color.BLUE); //干扰线是蓝色
            g2.drawLine(x1, y1, x2, y2);//画线
        }
    }

    // 随机生成一个字符
    private char randomChar() {
        // 可选字符
        String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
        int index = r.nextInt(codes.length());
        return codes.charAt(index);
    }

    // 创建BufferedImage
    private BufferedImage createImage() {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(this.bgColor);
        g2.fillRect(0, 0, w, h);
        return image;
    }

    // 调用这个方法得到验证码
    public BufferedImage getImage() {
        BufferedImage image = createImage();//创建图片缓冲区
        Graphics2D g2 = (Graphics2D) image.getGraphics();//得到绘制环境
        StringBuilder sb = new StringBuilder();//用来装载生成的验证码文本
        // 向图片中画4个字符
        for (int i = 0; i < 4; i++) {//循环四次，每次生成一个字符
            String s = randomChar() + "";//随机生成一个字母
            sb.append(s); //把字母添加到sb中
            float x = i * 1.0F * w / 4; //设置当前字符的x轴坐标
            g2.setFont(randomFont()); //设置随机字体
            g2.setColor(randomColor()); //设置随机颜色
            g2.drawString(s, x, h - 5); //画图
        }
        this.text = sb.toString(); //把生成的字符串赋给了this.text
        drawLine(image); //添加干扰线
        return image;
    }

    // 返回验证码图片上的文本
    public String getText() {
        return text;
    }

    // 保存图片到指定的输出流
    public static void output(BufferedImage image, OutputStream out)
            throws IOException {
        ImageIO.write(image, "JPEG", out);
    }
}
```

#### 好友用户实时聊天

    通过轮询实现。

```javascript
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
```

#### 云部署

    打包生成war包后进行部署。