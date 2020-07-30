package Servlet;

import DB.DBHelper;
import Entity.User;

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

@WebServlet("/myFriendsServlet")
public class myFriendsServlet extends HttpServlet {

    public myFriendsServlet() {
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
        List<User> users = new ArrayList<>();

        if (session.getAttribute("UID") == null) {
            out.println("你尚未登录！即将进入登录页面！");
            out.println("<script>\n" +
                    "            setTimeout(function(){window.location.href='login.jsp';},1000);\n" +
                    "        </script>");

        } else {
            UID = (int) session.getAttribute("UID");
            String username = (String) session.getAttribute("username");
            sql = "select * from travelfriends where UID2='" + UID + "' and state='1'";
            rs = DBHelper.executeQuery(sql);
            rows = DBHelper.getCount(sql);
            request.setAttribute("rows", rows);

            for (int i = 0; i < rows; i++) {
                try {
                    User user = new User();
                    rs.next();
                    int UID1 = rs.getInt("UID1");
                    sql = "select * from traveluser where UID='" + UID1 + "'";
                    ResultSet rsTemp = DBHelper.executeQuery(sql);
                    if (rsTemp.next()) {
                        user.setName(rsTemp.getString("UserName"));
                        user.setEmail(rsTemp.getString("Email"));
                        user.setDate(rsTemp.getDate("DateJoined"));
                        user.setId(UID1);
                        users.add(user);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            request.setAttribute("users", users);
            request.setAttribute("load", "1");
            request.getRequestDispatcher("my_friends.jsp").forward(request, response);
        }

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
