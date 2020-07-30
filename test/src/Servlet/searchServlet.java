package Servlet;

import DB.DBHelper;
import Entity.Travelimage;
import Tools.HTMLFilter;

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

@WebServlet("/searchServlet")
public class searchServlet extends HttpServlet {

    public searchServlet() {
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
        int page = 5;
        List<Travelimage> imgs = new ArrayList<>();
        List<Integer> cnt = new ArrayList<>();
        request.setCharacterEncoding("utf-8");
        String filter = request.getParameter("filter");
        String type = request.getParameter("type");
        String content = (request.getParameter("content") == null) ? " " : HTMLFilter.filter(request.getParameter("content"));
        System.out.println(content);
        if (filter == null) filter = " ";
        if (type == null) type = " ";
        if (!type.equals("time")) {
            if (!filter.equals("content")) {
                sql = "select * from travelimage where Title like '%" + content + "%' order by favourCount DESC";
            } else {
                sql = "select * from travelimage where Content like '%" + content + "%' order by favourCount DESC";
            }
            rs = DBHelper.executeQuery(sql);
            rows = DBHelper.getCount(sql);
            request.setAttribute("rows", rows);
            try {
                for (int i = 0; i < rows; i++) {
                    rs.next();
                    Travelimage img = new Travelimage();
                    img.setId(rs.getInt("ImageID"));
                    img.setTitle(rs.getString("Title"));
                    img.setDescription(rs.getString("Description"));
                    if (img.getDescription() == null) img.setDescription("该图片暂无简介！");
                    img.setPath(rs.getString("PATH"));
                    imgs.add(img);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            DBHelper.free(rs);
            request.setAttribute("load", "1");
            request.setAttribute("imgs", imgs);
            for (int i = 0; i < (rows + page - 1) / page; i++)
                cnt.add(i + 1);
            request.setAttribute("cnt", cnt);
            request.getRequestDispatcher("search.jsp").forward(request, response);
        } else {
            if (!filter.equals("content")) {
                sql = "select * from travelimage where Title like '%" + content + "%' order by cast(submitTime as datetime) DESC";
            } else {
                sql = "select * from travelimage where Content like '%" + content + "%' order by cast(submitTime as datetime) DESC";
            }
            rs = DBHelper.executeQuery(sql);
            rows = DBHelper.getCount(sql);
            request.setAttribute("rows", rows);
            try {
                for (int i = 0; i < rows; i++) {
                    rs.next();
                    Travelimage img = new Travelimage();
                    img.setId(rs.getInt("ImageID"));
                    img.setTitle(rs.getString("Title"));
                    img.setDescription(rs.getString("Description"));
                    if (img.getDescription() == null) img.setDescription("该图片暂无简介！");
                    img.setPath(rs.getString("PATH"));
                    imgs.add(img);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            DBHelper.free(rs);
            request.setAttribute("load", 1);
            request.setAttribute("imgs", imgs);
            for (int i = 0; i < (rows + page - 1) / page; i++)
                cnt.add(i + 1);
            request.setAttribute("cnt", cnt);
            request.getRequestDispatcher("search.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
