package Servlet;

import DB.DBHelper;
import Entity.City;
import Entity.Country;
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

@WebServlet("/uploadServlet")
public class uploadServlet extends HttpServlet {

    public uploadServlet() {
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
        String sql, sqlTemp;
        ResultSet rs, rsTemp = null;
        int rows;
        int UID;
        String id = request.getParameter("id");
        List<Travelimage> imgs = new ArrayList<>();
        List<String> contents = new ArrayList<>();
        List<Country> countries = new ArrayList<>();
        List<City> cities = new ArrayList<>();

        if (session.getAttribute("UID") == null) {
            out.println("你尚未登录！即将进入登录页面！");
            out.println("<script>\n" +
                    "            setTimeout(function(){window.location.href='login.jsp';},1000);\n" +
                    "        </script>");

        } else {
            UID = (int) session.getAttribute("UID");

            try {
                sql = "select Content from travelimage group by Content";
                rs = DBHelper.executeQuery(sql);
                while (rs.next()) {
                    contents.add(rs.getString("Content"));
                }
                request.setAttribute("contents", contents);

                sql = "select Country_RegionName,ISO from geocountries_regions where 1 order by Country_RegionName";
                rs = DBHelper.executeQuery(sql);
                while (rs.next()) {
                    Country country = new Country();
                    country.setCountryISO(rs.getString("ISO"));
                    country.setCountry_RegionName(rs.getString("Country_RegionName"));
                    countries.add(country);
                }
                request.setAttribute("countries", countries);

                if (id != null) {
                    sql = "select * from travelimage where ImageID='" + id + "' and UID='" + UID + "'";
                    rs = DBHelper.executeQuery(sql);
                    if (rs.next()) {
                        Travelimage img = new Travelimage();
                        img.setId(rs.getInt("ImageID"));
                        img.setTitle(rs.getString("Title"));
                        img.setContent(rs.getString("Content"));
                        img.setDescription(rs.getString("Description"));
                        img.setPath(rs.getString("PATH"));

                        String ISO = rs.getString("Country_RegionCodeISO");
                        if (ISO == null) {
                            img.setCountryName("");
                            img.setCountryISO("");
                        } else {
                            sqlTemp = "select Country_RegionName from geocountries_regions where ISO = '" + ISO + "'";
                            rsTemp = DBHelper.executeQuery(sqlTemp);
                            rsTemp.next();
                            img.setCountryISO(ISO);
                            img.setCountryName(rsTemp.getString("Country_RegionName"));
                        }

                        String CityCode = rs.getString("CityCode");
                        if (CityCode == null) {
                            img.setCityName("");
                            img.setCityCode("");
                        } else {
                            sqlTemp = "select AsciiName from geocities where GeoNameID = '" + CityCode + "'";
                            rsTemp = DBHelper.executeQuery(sqlTemp);
                            rsTemp.next();
                            img.setCityCode(CityCode);
                            img.setCityName(rsTemp.getString("AsciiName"));
                        }
                        imgs.add(img);
                        request.setAttribute("imgs", imgs);

                        sql = "select AsciiName,GeoNameID from geocities where Country_RegionCodeISO='" + ISO + "'";
                        rs = DBHelper.executeQuery(sql);
                        while (rs.next()) {
                            City city = new City();
                            city.setGeoNameID(rs.getString("GeoNameID"));
                            city.setAsciiName(rs.getString("AsciiName"));
                            cities.add(city);
                        }
                        request.setAttribute("cities", cities);

                    } else {
                        out.println("这不是你的图片！即将返回上传页面！");
                        out.println("<script>\n" +
                                "            setTimeout(function(){window.location.href='upload.jsp';},1000);\n" +
                                "        </script>");
                    }
                    DBHelper.free(rs);
                    request.setAttribute("type", "1");
                } else {
                    request.setAttribute("type", "0");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            request.setAttribute("load", "1");
            request.getRequestDispatcher("upload.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
