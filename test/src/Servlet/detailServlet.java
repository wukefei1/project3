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
import java.util.Date;

@WebServlet("/detailServlet")
public class detailServlet extends HttpServlet {

    public detailServlet() {
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
        String id = request.getParameter("id");
//        System.out.println(id);

        String sql;
        ResultSet rs;
        int rows;

        Travelimage img = new Travelimage();
        sql = "select * from travelimage where ImageID=" + id;
        rs = DBHelper.executeQuery(sql);
        try {
            if (rs.next()) {
                String sqlTemp;
                ResultSet rsTemp;

                if (session.getAttribute("UID") != null) {
                    int UID = (int) session.getAttribute("UID");
                    sqlTemp = "select * from travelimagefavor where ImageID = '" + id + "' and UID='" + UID + "'";
                    rows = DBHelper.getCount(sqlTemp);
                    img.setIsFavourite(rows > 0);

                    long timestamp = new Date().getTime();
                    sqlTemp = "select * from recentbrowse where UID='" + UID + "' and ImageID='" + id + "'";
                    rows = DBHelper.getCount(sqlTemp);
                    if (rows == 0) {
                        sqlTemp = "insert into recentbrowse(UID,ImageID,time) values('" + UID + "','" + id + "','" + timestamp + "')";
                        DBHelper.executeNonQuery(sqlTemp);
                        sqlTemp = "select * from recentbrowse where UID='" + UID + "'";
                        rows = DBHelper.getCount(sqlTemp);
                        if (rows > 10) {
                            sqlTemp = "select * from recentbrowse where UID='" + UID + "' order by time LIMIT 0," + (rows - 10);
                            rsTemp = DBHelper.executeQuery(sqlTemp);
                            try {
                                while (rsTemp.next()) {
                                    int ImageID = rsTemp.getInt("ImageID");
                                    sqlTemp = "delete from recentbrowse where UID='" + UID + "' and ImageID='" + ImageID + "'";
                                    DBHelper.executeNonQuery(sqlTemp);
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    } else {
                        sqlTemp = "update recentbrowse set time='" + timestamp + "'where UID='" + UID + "' and ImageID='" + id + "'";
                        DBHelper.executeNonQuery(sqlTemp);
                    }
                }

                img.setId(rs.getInt("ImageID"));
                img.setTitle(rs.getString("Title"));
                img.setContent(rs.getString("Content"));
                img.setDescription(rs.getString("Description"));
                if (img.getDescription() == null) img.setDescription("该图片暂无简介！");
                img.setPath(rs.getString("PATH"));
                img.setSubmitTime(rs.getDate("submitTime"));

                String ISO = rs.getString("Country_RegionCodeISO");
                if (ISO == null) img.setCountryName("");
                else {
                    sqlTemp = "select Country_RegionName from geocountries_regions where ISO = '" + ISO + "'";
                    rsTemp = DBHelper.executeQuery(sqlTemp);
                    rsTemp.next();
                    img.setCountryName(rsTemp.getString("Country_RegionName"));
                }

                String CityCode = rs.getString("CityCode");
                if (CityCode == null) img.setCityName("");
                else {
                    sqlTemp = "select AsciiName from geocities where GeoNameID = '" + CityCode + "'";
                    rsTemp = DBHelper.executeQuery(sqlTemp);
                    rsTemp.next();
                    img.setCityName(rsTemp.getString("AsciiName"));
                }

                String UID = rs.getString("UID");
                if (UID == null) img.setAuthor("");
                else {
                    sqlTemp = "select UserName from traveluser where UID = '" + UID + "'";
                    rsTemp = DBHelper.executeQuery(sqlTemp);
                    rsTemp.next();
                    img.setAuthor(rsTemp.getString("UserName"));
                }


                sqlTemp = "select count(*) from travelimagefavor where ImageID = '" + id + "'";
                rsTemp = DBHelper.executeQuery(sqlTemp);
                rsTemp.next();
                img.setLikeNum(rsTemp.getInt("count(*)"));
                DBHelper.free(rsTemp);

                request.setAttribute("details", img);
                request.setAttribute("load", "1");
                request.getRequestDispatcher("details.jsp?id=" + id).forward(request, response);
            } else {
                out.println("不合法的图片ID！即将返回首页！");
                out.println("<script>\n" +
                        "            setTimeout(function(){window.location.href='home.jsp';},1000);\n" +
                        "        </script>");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DBHelper.free(rs);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
