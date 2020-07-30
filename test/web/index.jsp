<%@ page import="DB.DBHelper" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Date" %>
<%--
  Created by IntelliJ IDEA.
  User: wukefei
  Date: 2020/7/6
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
</head>
<body>
Hello World!
</body>
</html>

<%
    System.out.println(new Date().getTime());

//    String sql;
//    ResultSet rs;
//    int rows;
//    sql = "select ImageID,count(*) from travelimagefavor group by ImageID";
//    rs = DBHelper.executeQuery(sql);
//    rows = DBHelper.getCount(sql);
//    for (int i = 0; i < rows; i++) {
//        try {
//            rs.next();
//            int id = rs.getInt("ImageID");
//            int count = rs.getInt("count(*)");
//            sql = "update travelimage set favourCount='" + count + "' where ImageID='" + id + "'";
////            System.out.println(sql);
//            DBHelper.executeNonQuery(sql);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }


//    sql = "select ImageID from travelimage where 1";
//    rs = DBHelper.executeQuery(sql);
//    rows = DBHelper.getCount(sql);
//    for (int i = 0; i < rows; i++) {
//        try {
//            rs.next();
//            int id = rs.getInt("ImageID");
//            java.sql.Date date = DateRandom.randomHireday();
//            sql = "update travelimage set submitTime ='" + date + "' where ImageID='" + id + "'";
////            System.out.println(sql);
//            DBHelper.executeNonQuery(sql);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
%>
