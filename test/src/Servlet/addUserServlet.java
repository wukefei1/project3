package Servlet;

import DB.DBHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/addUserServlet")
public class addUserServlet extends HttpServlet {

    public addUserServlet() {
        super();
    }

    @Override
    public void init() {
        System.out.println("init");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        PrintWriter out = response.getWriter();
        String sql;
        ResultSet rs;
        int rows;
        int UID;
        String name = request.getParameter("userName");

        if (session.getAttribute("UID") == null) {
            out.println("你尚未登录！即将进入登录页面！");
            out.println("<script>\n" +
                    "            setTimeout(function(){window.location.href='login.jsp';},1000);\n" +
                    "        </script>");

        } else {
            UID = (int) session.getAttribute("UID");
            sql = "select UID from traveluser where UserName='" + name + "'";
            rs = DBHelper.executeQuery(sql);
            try {
                if (rs.next()) {
                    int UID2 = rs.getInt("UID");
                    if (UID != UID2) {
                        sql = "select state from travelfriends where UID1='" + UID + "' and UID2='" + UID2 + "'";
                        rs = DBHelper.executeQuery(sql);
                        if (rs.next()) {
                            int state = rs.getInt("state");
                            if (state == 1) {
                                out.println("你们已经是好友了！即将返回个人中心页面！");
                                out.println("<script>\n" +
                                        "            setTimeout(function(){window.location.href='personal_center.jsp';},1000);\n" +
                                        "        </script>");
                            } else if (state == 0) {
                                out.println("你已经发送过好友请求了！即将返回个人中心页面！");
                                out.println("<script>\n" +
                                        "            setTimeout(function(){window.location.href='personal_center.jsp';},1000);\n" +
                                        "        </script>");
                            }
                            return;
                        }
                        sql = "select state from travelfriends where UID1='" + UID2 + "' and UID2='" + UID + "'";
                        rs = DBHelper.executeQuery(sql);
                        if (rs.next()) {
                            int state = rs.getInt("state");
                            if (state == 1) {
                                out.println("你们已经是好友了！即将返回个人中心页面！");
                                out.println("<script>\n" +
                                        "            setTimeout(function(){window.location.href='personal_center.jsp';},1000);\n" +
                                        "        </script>");
                            } else if (state == 0) {
                                out.println("该用户已经给你发送过好友请求了！即将返回个人中心页面！");
                                out.println("<script>\n" +
                                        "            setTimeout(function(){window.location.href='personal_center.jsp';},1000);\n" +
                                        "        </script>");
                            }
                            return;
                        }
                        sql = "insert into travelfriends(UID1,UID2,state) values('" + UID + "','" + UID2 + "','0')";
                        DBHelper.executeNonQuery(sql);
                        out.println("好友请求已发送！即将返回个人中心页面！");
                        out.println("<script>\n" +
                                "            setTimeout(function(){window.location.href='personal_center.jsp';},1000);\n" +
                                "        </script>");
                    } else {
                        out.println("不能给自己发送好友请求！即将返回个人中心页面！");
                        out.println("<script>\n" +
                                "            setTimeout(function(){window.location.href='personal_center.jsp';},1000);\n" +
                                "        </script>");
                    }
                } else {
                    out.println("不存在该用户！即将返回个人中心页面！");
                    out.println("<script>\n" +
                            "            setTimeout(function(){window.location.href='personal_center.jsp';},1000);\n" +
                            "        </script>");
                }
                DBHelper.free(rs);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
