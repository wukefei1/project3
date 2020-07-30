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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/chatServlet")
public class chatServlet extends HttpServlet {

    public chatServlet() {
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
        String UID2 = request.getParameter("id");
        String userName = "";
        List<String> messages = new ArrayList<>();

        if (session.getAttribute("UID") == null) {
            out.println("你尚未登录！即将进入登录页面！");
            out.println("<script>\n" +
                    "            setTimeout(function(){window.location.href='login.jsp';},1000);\n" +
                    "        </script>");

        } else {
            int UID = (int) session.getAttribute("UID");
            sql = "select * from travelfriends where UID1='" + UID2 + "' and UID2='" + UID + "' and state='1'";
            if (DBHelper.isExist(sql)) {
                try {
                    sql = "select * from traveluser where UID='" + UID2 + "'";
                    rs = DBHelper.executeQuery(sql);
                    if (rs.next()) {
                        userName = rs.getString("UserName");
                    }
                    request.setAttribute("chatUser", userName);
                    sql = "select * from message where UID1='" + UID2 + "' and UID2='" + UID + "' and state='0' order by messageID";
                    rs = DBHelper.executeQuery(sql);
                    rows = DBHelper.getCount(sql);
                    request.setAttribute("rows", rows);
                    while (rs.next()) {
                        messages.add(rs.getString("message"));
                    }
                    sql = "update message set state='1' where UID1='" + UID2 + "' and UID2='" + UID + "' and state='0'";
                    DBHelper.executeNonQuery(sql);
                    request.setAttribute("message", messages);
                    request.setAttribute("load", "1");
                    request.getRequestDispatcher("chat.jsp").forward(request, response);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                out.println("该用户不是你的好友！即将返回我的好友页面！");
                out.println("<script>\n" +
                        "            setTimeout(function(){window.location.href='my_friends.jsp';},1000);\n" +
                        "        </script>");
            }
        }

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
