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

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {

    public loginServlet() {
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

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String type = "";

        String sql = "select * from traveluser where UserName = '" + username + "'";
        ResultSet rs = DBHelper.executeQuery(sql);
        try {
            if (rs.next()) {
                String pass = rs.getString("Pass");
                String salt = rs.getString("salt");
                if (Sha256.getSHA256(password + salt).equals(pass)) {
                    System.out.println("success");
                    session.setAttribute("UID", rs.getInt("UID"));
                    session.setAttribute("username", username);
                    response.sendRedirect("home.jsp");
                } else {
                    type = "2";
                    request.setAttribute("type", type);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
//                    System.out.println("error:2");
                }
                DBHelper.free(rs);
            } else {
                type = "1";
                request.setAttribute("type", type);
                request.getRequestDispatcher("login.jsp").forward(request, response);
//                System.out.println("error:1");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
