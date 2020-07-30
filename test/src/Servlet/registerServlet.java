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
import java.util.UUID;

@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {

    public registerServlet() {
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
        String email = request.getParameter("email");
        String pw1 = request.getParameter("pw1");
        String pw2 = request.getParameter("pw2");
        String code = request.getParameter("code");
        String sessionVerifyCode = (String) session.getAttribute("vCode");
        String type = "";

        if (!code.equalsIgnoreCase(sessionVerifyCode)) {
            type = "3";
            request.setAttribute("type", type);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else if (!pw1.equals(pw2)) {
            type = "1";
            request.setAttribute("type", type);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            String sql = "select UserName from traveluser where UserName='" + username + "' or Email='" + email + "'";
            int rows = DBHelper.getCount(sql);
//            System.out.println(rows);
            if (rows > 0) {
                type = "2";
                request.setAttribute("type", type);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                String salt = UUID.randomUUID().toString();
                String pass = Sha256.getSHA256(pw1 + salt);
//                System.out.println(salt);
//                System.out.println(pass);
                java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                sql = "insert into traveluser(UserName,Pass,Email,salt,DateJoined,State) values ('" + username + "','" + pass + "','" + email + "','" + salt + "','" + currentDate + "',1)";
                DBHelper.executeNonQuery(sql);
                sql = "select * from traveluser where UserName = '" + username + "'";
                ResultSet rs = DBHelper.executeQuery(sql);
                try {
                    rs.next();
                    session.setAttribute("UID", rs.getInt("UID"));
                    session.setAttribute("username", username);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                DBHelper.free(rs);
                response.sendRedirect("home.jsp");
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
