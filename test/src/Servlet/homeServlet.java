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

@WebServlet("/homeServlet")
public class homeServlet extends HttpServlet {

    public homeServlet() {
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
        List<Travelimage> toutu = new ArrayList<>();

        sql = "select ImageID,count(*) from travelimagefavor group by ImageID order by count(*) DESC LIMIT 0,3";
        rs = DBHelper.executeQuery(sql);
        rows = DBHelper.getCount(sql);
        try {
            for (int i = 0; i < rows; i++) {
                rs.next();
                int id = rs.getInt("ImageID");
                String sqltemp;
                sqltemp = "select * from travelimage where ImageID=" + id;
                ResultSet rstemp = DBHelper.executeQuery(sqltemp);
                Travelimage img = new Travelimage();
                rstemp.next();
                img.setId(id);
                img.setTitle(rstemp.getString("Title"));
                img.setDescription(rstemp.getString("Description"));
                if (img.getDescription() == null) img.setDescription("该图片暂无简介！");
                img.setPath(rstemp.getString("PATH"));
//                System.out.println(img.getPath());
                toutu.add(img);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        request.setAttribute("toutu", toutu);

        sql = "select * from travelimage order by cast(submitTime as datetime) DESC LIMIT 0,6";
        rs = DBHelper.executeQuery(sql);
        rows = DBHelper.getCount(sql);
        try {
            for (int j = 0; j < 2; j++) {
                List<Travelimage> show = new ArrayList<>();
                for (int i = 0; i < rows / 2; i++) {
                    rs.next();
                    Travelimage img = new Travelimage();
                    img.setId(rs.getInt("ImageID"));
                    img.setTitle(rs.getString("Title"));
                    img.setDescription(rs.getString("Description"));
                    if (img.getDescription() == null) img.setDescription("该图片暂无简介！");
                    img.setPath(rs.getString("PATH"));
//                System.out.println(img.getPath());
                    show.add(img);
                }
                request.setAttribute("show" + j, show);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        request.setAttribute("load", "1");
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
