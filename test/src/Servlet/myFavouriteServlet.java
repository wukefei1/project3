package Servlet;

import DB.DBHelper;
import Entity.Travelimage;

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

@WebServlet("/myFavouriteServlet")
public class myFavouriteServlet extends HttpServlet {

    public myFavouriteServlet() {
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
        int page = 5;
        List<Travelimage> imgs = new ArrayList<>();
        List<Travelimage> titles = new ArrayList<>();
        List<Integer> cnt = new ArrayList<>();

        if (session.getAttribute("UID") == null) {
            out.println("你尚未登录！即将进入登录页面！");
            out.println("<script>\n" +
                    "            setTimeout(function(){window.location.href='login.jsp';},1000);\n" +
                    "        </script>");

        } else {
            UID = (int) session.getAttribute("UID");
            sql = "select ImageID from travelimagefavor where UID='" + UID + "'";
            rs = DBHelper.executeQuery(sql);
            rows = DBHelper.getCount(sql);
//            System.out.println(rows);
            for (int i = 0; i < rows; i++) {
                try {
                    Travelimage img = new Travelimage();
                    rs.next();
                    int ImageID = rs.getInt("ImageID");
                    sql = "select * from travelimage where ImageID = '" + ImageID + "'";
                    ResultSet rsTemp = DBHelper.executeQuery(sql);
                    if (rsTemp.next()) {
                        String des = rsTemp.getString("Description");
                        if (des == null) des = "该图片暂无简介！";
                        img.setTitle(rsTemp.getString("Title"));
                        img.setDescription(des);
                        img.setPath(rsTemp.getString("PATH"));
                        img.setId(ImageID);
                    }
                    imgs.add(img);
                    DBHelper.free(rsTemp);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            sql = "select ImageID from recentbrowse where UID='" + UID + "' order by time DESC ";
            rs = DBHelper.executeQuery(sql);
            int count = 0;
            try {
                while (rs.next()) {
                    int id = rs.getInt("ImageID");
                    sql = "select Title from travelimage where ImageID='" + id + "'";
                    ResultSet rsTemp = DBHelper.executeQuery(sql);
                    if (rsTemp.next()) {
                        Travelimage img = new Travelimage();
                        img.setId(id);
                        img.setTitle(rsTemp.getString("Title"));
                        titles.add(img);
                        count++;
                    }
                    DBHelper.free(rsTemp);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            DBHelper.free(rs);
            request.setAttribute("rows", rows);
            request.setAttribute("count", count);
            for (int i = 0; i < (rows + page - 1) / page; i++)
                cnt.add(i + 1);
            request.setAttribute("cnt", cnt);
            request.setAttribute("load", "1");
            request.setAttribute("imgs", imgs);
            request.setAttribute("titles", titles);
            request.getRequestDispatcher("my_favourite.jsp").forward(request, response);
        }

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
