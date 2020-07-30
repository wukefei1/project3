package Servlet;

import DB.DBHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/deletePhotoServlet")
public class deletePhotoServlet extends HttpServlet {

    public deletePhotoServlet() {
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
            sql = "select * from travelimage where UID='" + UID + "' and ImageID='" + id + "'";
            rs = DBHelper.executeQuery(sql);
            try {
                if (rs.next()) {
                    String path = rs.getString("PATH");
                    sql = "delete from travelimage where UID='" + UID + "' and ImageID='" + id + "'";
                    DBHelper.executeNonQuery(sql);
                    File dirFile = new File(getServletContext().getRealPath("/img/travel-images"));
                    if (dirFile.exists()) {
                        File[] files = dirFile.listFiles();
                        if (files != null) {
                            for (File fileChildDir : files) {
                                File temp = new File(getServletContext().getRealPath("/img/travel-images") + "/" + fileChildDir.getName() + "/" + path);
                                if (temp.delete()) System.out.println("delete");
                            }
                        }
                    }
                    out.println("图片删除成功！即将返回我的照片页面！");
                    out.println("<script>\n" +
                            "            setTimeout(function(){window.location.href='my_photos.jsp';},1000);\n" +
                            "        </script>");
                } else {
                    out.println("这不是你的图片！即将返回我的照片页面！");
                    out.println("<script>\n" +
                            "            setTimeout(function(){window.location.href='my_photos.jsp';},1000);\n" +
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
