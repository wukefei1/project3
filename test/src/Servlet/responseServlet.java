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

@WebServlet("/responseServlet")
public class responseServlet extends HttpServlet {

    public responseServlet() {
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
        String UID2 = request.getParameter("id");
        String type = request.getParameter("type");

        if (session.getAttribute("UID") == null) {
            out.println("你尚未登录！即将进入登录页面！");
            out.println("<script>\n" +
                    "            setTimeout(function(){window.location.href='login.jsp';},1000);\n" +
                    "        </script>");

        } else {
            UID = (int) session.getAttribute("UID");
            sql = "select state from travelfriends where UID1='" + UID2 + "' and UID2='" + UID + "'";
            rs = DBHelper.executeQuery(sql);
            try {
                if (rs.next()) {
                    int state = rs.getInt("state");
                    if (state == 1) {
                        out.println("你们已经是好友了！即将返回个人中心页面！");
                        out.println("<script>\n" +
                                "            setTimeout(function(){window.location.href='personal_center.jsp';},1000);\n" +
                                "        </script>");
                    } else if (state == 0) {
                        if (type.equals("1")) {
                            sql = "update travelfriends set state='1' where UID1='" + UID2 + "' and UID2='" + UID + "'";
                            DBHelper.executeNonQuery(sql);
                            sql = "insert into travelfriends(UID1,UID2,state) values('" + UID + "','" + UID2 + "','1')";
                            DBHelper.executeNonQuery(sql);
                            out.println("你通过了该用户的好友请求！即将返回个人中心页面！");
                            out.println("<script>\n" +
                                    "            setTimeout(function(){window.location.href='personal_center.jsp';},1000);\n" +
                                    "        </script>");
                        } else if (type.equals("0")) {
                            sql = "delete from travelfriends where UID1='" + UID2 + "' and UID2='" + UID + "'";
                            DBHelper.executeNonQuery(sql);
                            out.println("你拒绝了该用户的好友请求！即将返回个人中心页面！");
                            out.println("<script>\n" +
                                    "            setTimeout(function(){window.location.href='personal_center.jsp';},1000);\n" +
                                    "        </script>");
                        }
                    }
                } else {
                    out.println("该用户没有给你发好友申请！即将返回个人中心页面！");
                    out.println("<script>\n" +
                            "            setTimeout(function(){window.location.href='personal_center.jsp';},1000);\n" +
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
