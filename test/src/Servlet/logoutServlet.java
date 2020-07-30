package Servlet;

import DB.DBHelper;
import DB.Sha256;

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

@WebServlet("/logoutServlet")
public class logoutServlet extends HttpServlet {

    public logoutServlet() {
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

        session.removeAttribute("UID");
        session.removeAttribute("username");
        out.println("登出成功！即将返回首页！");
        out.println("<script>\n" +
                "            setTimeout(function(){window.location.href='home.jsp';},1000);\n" +
                "        </script>");
//        response.sendRedirect("home.jsp");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
