package Servlet;

import DB.DBHelper;
import Tools.HTMLFilter;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet("/uploadJudgeServlet")
public class uploadJudgeServlet extends HttpServlet {
    static DiskFileItemFactory factory = new DiskFileItemFactory();
    static ServletFileUpload upload = new ServletFileUpload(factory);

    static {
        factory.setSizeThreshold(1024 * 1024);//如果文件超过1MB，直接写入临时文件夹中
        File file = new File("C:\\summer2020_pj_pic_tmp");
        factory.setRepository(file);
        upload.setSizeMax(1024 * 1024 * 20);//文件不允许超过20MB
        upload.setHeaderEncoding("UTF-8");
    }

    private boolean isValidContentType(String tp) {
        return "image/gif".equals(tp) || "image/jpeg".equals(tp) || "image/jpg".equals(tp) || "image/png".equals(tp);
    }

    private String getFileName(String contentType, String size, HttpSession session, Date now) {
        return getServletContext().getRealPath("/") + "/img/travel-images/" + size + "/" +
                session.getAttribute("username") + "-" + new SimpleDateFormat("yyyyMMddHHmmss").format(now) +
                "." + contentType.split("/")[1];
    }

    private double getCompressedRate(long size) {
        if (size > 1024 * 1024 * 10)
            return 0.1;
        else if (size > 1024 * 1024 * 2)
            return 0.2;
        else if (size > 1024 * 200)
            return 0.4;
        else
            return 0.8;
    }

    protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        PrintWriter out = response.getWriter();
        String sql;
        ResultSet rs;
        String id = request.getParameter("id");
        Date now = new Date();
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

        if (session.getAttribute("UID") == null) {
            out.println("你尚未登录！即将进入登录页面！");
            out.println("<script>\n" +
                    "            setTimeout(function(){window.location.href='login.jsp';},1000);\n" +
                    "        </script>");

        } else {
            int UID = (int) session.getAttribute("UID");
            try {
                List<FileItem> fileItems = upload.parseRequest(request); //得到FileItem集合
                String fileName = "";
                String compressedFileName = "";
                HashMap<String, String> map = new HashMap<>();
                //遍历items
                for (FileItem item : fileItems) {
                    if (item.isFormField())//如果是表单域
                    {
                        String name = item.getFieldName();
                        String value =
                                HTMLFilter.filter(item.getString("UTF-8"));
                        map.put(name, value);
                    } else if (item.getSize() > 0)//如果有文件
                    {
                        if (item.getSize() >= 1024 * 1024 * 20) {
                            out.println("上传图片过大！即将返回上传页面！");
                            out.println("<script>\n" +
                                    "            setTimeout(function(){window.location.href='upload.jsp';},1000);\n" +
                                    "        </script>");
                            return;
                        }
                        String contentType = item.getContentType();
                        if (!isValidContentType(contentType)) {
                            out.println("上传文件类型不合法！即将返回上传页面！");
                            out.println("<script>\n" +
                                    "            setTimeout(function(){window.location.href='upload.jsp';},1000);\n" +
                                    "        </script>");
                            return;
                        }
                        fileName = getFileName(contentType, "large", request.getSession(), now);
                        if (!new File(fileName).exists())
                            new File(fileName.substring(0, fileName.lastIndexOf("/"))).mkdir();

                        item.write(new File(fileName));
                        compressedFileName = getFileName(contentType, "small", request.getSession(), now);
                        Thumbnails.of(new File(fileName)).scale(getCompressedRate(item.getSize()))
                                .toFile(new File(compressedFileName));
                    } else if (id == null) {
                        out.println("你还没有上传图片！即将返回上传页面！");
                        out.println("<script>\n" +
                                "            setTimeout(function(){window.location.href='upload.jsp';},1000);\n" +
                                "        </script>");
                        return;
                    }
                }
                String title = map.get("title");
                String country = map.get("country");
                String content = map.get("content");
                String cityname = map.get("city");
                String description = map.get("description");
                if (title.equals("") || country.equals("") || content.equals("") || cityname.equals("")) {
                    out.println("表单填写不完整！即将返回上传页面！");
                    out.println("<script>\n" +
                            "            setTimeout(function(){window.location.href='upload.jsp';},1000);\n" +
                            "        </script>");
                    File file = new File(fileName);
                    if (file.delete()) System.out.println("delete");
                    file = new File(compressedFileName);
                    if (file.delete()) System.out.println("delete");
                    return;
                }
                if (description == null) description = "";
                String city = "";
                sql = "select GeoNameID from geocities where AsciiName='" + cityname + "' and Country_RegionCodeISO='" + country + "'";
                rs = DBHelper.executeQuery(sql);

                if (rs.next()) {
                    city = rs.getString("GeoNameID");
                }

                if (id != null) {
                    sql = "select * from travelimage where ImageID='" + id + "' and UID ='" + UID + "'";
                    rs = DBHelper.executeQuery(sql);
                    if (rs.next()) {
                        if ("".equals(fileName)) {
                            sql = "UPDATE travelimage SET Title='" + title + "', Description='" + description + "', CityCode='" + city + "', Country_RegionCodeISO='" + country
                                    + "', UID='" + UID + "', Content='" + content + "', submitTime='" + currentDate + "'" + " WHERE imageID='" + id + "'";
                            DBHelper.executeNonQuery(sql);
                        } else {
                            String path = rs.getString("PATH");
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
                            sql = "UPDATE travelimage SET Title='" + title + "', Description='" + description + "', CityCode='" + city + "', Country_RegionCodeISO='" + country + "', UID='" + UID
                                    + "', PATH='" + fileName.substring(fileName.lastIndexOf("/") + 1) + "', Content='" + content + "', submitTime='" + currentDate + "'" + " WHERE imageID='" + id + "'";
                            DBHelper.executeNonQuery(sql);
                        }
                    } else {
                        out.println("这不是你的图片！即将返回上传页面！");
                        out.println("<script>\n" +
                                "            setTimeout(function(){window.location.href='upload.jsp';},1000);\n" +
                                "        </script>");
                    }
                } else {
                    sql = "INSERT INTO travelimage(Title, Description, CityCode, Country_RegionCodeISO, UID, PATH, Content, submitTime)" +
                            "VALUES ('" + title + "','" + description + "','" + city + "','" + country + "','" + UID + "','" + fileName.substring(fileName.lastIndexOf("/") + 1) + "','" + content + "','" + currentDate + "')";
                    DBHelper.executeNonQuery(sql);
                }
            } catch (FileUploadBase.SizeLimitExceededException e) {
                out.println("上传图片过大！即将返回上传页面！");
                out.println("<script>\n" +
                        "            setTimeout(function(){window.location.href='upload.jsp';},1000);\n" +
                        "        </script>");
                return;
            } catch (Exception e) {
                out.println("未知错误！即将返回上传页面！");
                out.println("<script>\n" +
                        "            setTimeout(function(){window.location.href='upload.jsp';},1000);\n" +
                        "        </script>");
                e.printStackTrace();
                return;
            }
            out.println("上传成功！即将进入我的照片界面！");
            out.println("<script>\n" +
                    "            setTimeout(function(){window.location.href='my_photos.jsp';},1000);\n" +
                    "        </script>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doPost(request, response);
    }
}
