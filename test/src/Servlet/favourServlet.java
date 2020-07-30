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

@WebServlet("/favourServlet")
public class favourServlet extends HttpServlet {

    public favourServlet() {
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
            sql = "select * from travelimagefavor where UID='" + UID + "' and ImageID='" + id + "'";
            rows = DBHelper.getCount(sql);
            if (rows > 0) {
                sql = "delete from travelimagefavor where UID='" + UID + "' and ImageID='" + id + "'";
                DBHelper.executeNonQuery(sql);
                out.println("你已将该图片从收藏中删除！即将返回收藏页面！");
                out.println("<script>\n" +
                        "            setTimeout(function(){window.location.href='my_favourite.jsp';},1000);\n" +
                        "        </script>");
            } else {
                sql = "insert into travelimagefavor(UID,ImageID) values('" + UID + "','" + id + "')";
                DBHelper.executeNonQuery(sql);
                out.println("图片已收藏！即将进入收藏界面！");
                out.println("<script>\n" +
                        "            setTimeout(function(){window.location.href='my_favourite.jsp';},1000);\n" +
                        "        </script>");
            }
            sql = "select count(*) from travelimagefavor where ImageID='" + id + "'";
            rs = DBHelper.executeQuery(sql);
            try {
                if (rs.next()) {
                    int count = rs.getInt("count(*)");
                    sql = "update travelimage set favourCount='" + count + "' where ImageID='" + id + "'";
                    DBHelper.executeNonQuery(sql);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            DBHelper.free(rs);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
