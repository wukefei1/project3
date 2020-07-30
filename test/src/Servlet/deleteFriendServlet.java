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

@WebServlet("/deleteFriendServlet")
public class deleteFriendServlet extends HttpServlet {

    public deleteFriendServlet() {
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
        String id = request.getParameter("id");

        if (session.getAttribute("UID") == null) {
            out.println("你尚未登录！即将进入登录页面！");
            out.println("<script>\n" +
                    "            setTimeout(function(){window.location.href='login.jsp';},1000);\n" +
                    "        </script>");

        } else {
            UID = (int) session.getAttribute("UID");
            sql = "select * from travelfriends where UID1='" + UID + "' and UID2='" + id + "'";
            rs = DBHelper.executeQuery(sql);
            try {
                if (rs.next()) {
                    sql = "delete from travelfriends where UID1='" + UID + "' and UID2='" + id + "'";
                    DBHelper.executeNonQuery(sql);
                    sql = "delete from travelfriends where UID2='" + UID + "' and UID1='" + id + "'";
                    DBHelper.executeNonQuery(sql);
                    out.println("好友删除成功！即将返回我的好友页面！");
                    out.println("<script>\n" +
                            "            setTimeout(function(){window.location.href='my_friends.jsp';},1000);\n" +
                            "        </script>");
                } else {
                    out.println("他不是你的好友！即将返回我的好友页面！");
                    out.println("<script>\n" +
                            "            setTimeout(function(){window.location.href='my_friends.jsp';},1000);\n" +
                            "        </script>");
                }
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
