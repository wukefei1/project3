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

@WebServlet("/refreshServlet")
public class refreshServlet extends HttpServlet {

    public refreshServlet() {
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

        if (session.getAttribute("UID") == null) {
            out.println("你尚未登录！即将进入登录页面！");
            out.println("<script>\n" +
                    "            setTimeout(function(){window.location.href='login.jsp';},1000);\n" +
                    "        </script>");

        } else {
            int UID = (int) session.getAttribute("UID");
            String temp = DBHelper.getJson(UID, UID2);
            if (temp != null) {
                out.append(temp);
                out.close();
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
