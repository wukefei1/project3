package Tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class NavigatorList extends SimpleTagSupport {
    @Override
    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter jspWriter = getJspContext().getOut();
        Object user = pageContext.getSession().getAttribute("username");


        if (user != null) {
            String userName = (String) user;
            jspWriter.println("<div class='header_right'>\n" +
                    "            <ul>\n" +
                    "                <a>\n" +
                    "                    <li class='show_list'><span> &#12288 " + userName + " &#12288 </span>\n" +
                    "                        <ul class='move_list' style='width: 170px;right: " + (10 * userName.length() - 75) + "px;'>\n" +
                    "                            <li style='border-radius: 10px 10px 0px 0px;'><a href='./upload.jsp'> &#12288 <img\n" +
                    "                                    class='h' src='./img/common/upload_w.png' width='20' height='20'> &#12288 上传</a>\n" +
                    "                            </li>\n" +
                    "                            <li><a href='./my_photos.jsp'> &#12288 <img class='h' src='./img/common/my pictures_w.png'\n" +
                    "                                                                        width='20' height='20'> &#12288 我的照片</a>\n" +
                    "                            </li>\n" +
                    "                            <li><a href='./my_favourite.jsp'> &#12288 <img class='h'\n" +
                    "                                                                           src='./img/common/my favourite_w.png'\n" +
                    "                                                                           width='20' height='20'> &#12288 我的收藏</a>\n" +
                    "                            </li>\n" +
                    "                            <li><a href='./my_friends.jsp'> &#12288 <img\n" +
                    "                                    class='h' src='./img/common/my friends_w.png' width='20' height='20'> &#12288\n" +
                    "                                我的好友</a>\n" +
                    "                            </li>\n" +
                    "                            <li><a href='./personal_center.jsp'> &#12288 <img\n" +
                    "                                    class='h' src='./img/common/personal center_w.png' width='20' height='20'> &#12288\n" +
                    "                                个人中心</a>\n" +
                    "                            </li>\n" +
                    "                            <li style='border-radius: 0px 0px 10px 10px;'><a href='logoutServlet'> &#12288 <img\n" +
                    "                                    class='h' src='./img/common/log in_w.png' width='20' height='20'> &#12288 登出</a>\n" +
                    "                            </li>\n" +
                    "                        </ul>\n" +
                    "                    </li>\n" +
                    "                </a>\n" +
                    "            </ul>\n" +
                    "        </div>");
        } else {
            jspWriter.println("<div class='header_right'>\n" +
                    "            <ul><a href='./login.jsp'>\n" +
                    "                <li class='show_list'><span>&#12288&#12288登录&#12288&#12288</span></li>\n" +
                    "            </a></ul>\n" +
                    "        </div>");
        }
    }
}
