package DB;

import java.sql.*;


public final class DBHelper {
    // 此方法为获取数据库连接
    public static Connection getConnection() {
        Connection conn = null;
        try {
            String driver = "com.mysql.cj.jdbc.Driver"; // 数据库驱动
            String url = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC";//数据库
            String user = "root"; // 用户名
            String password = "1657181739"; // 密码
            Class.forName(driver);// 加载数据库驱动
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can't find the Driver!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }


    /**
     * 增删改【Add、Del、Update】
     *
     * @param sql
     * @return int
     */

    public static int executeNonQuery(String sql) {
        int result = 0;
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
        } catch (SQLException err) {
            err.printStackTrace();
            free(null, stmt, conn);
        } finally {
            free(null, stmt, conn);
        }
        return result;
    }


    /**
     * 增删改【Add、Delete、Update】
     *
     * @param sql
     * @param obj
     * @return int
     */

    public static int executeNonQuery(String sql, Object... obj) {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                pstmt.setObject(i + 1, obj[i]);
            }
            result = pstmt.executeUpdate();
        } catch (SQLException err) {
            err.printStackTrace();
            free(null, pstmt, conn);
        } finally {
            free(null, pstmt, conn);
        }
        return result;
    }


    /**
     * 查【Query】
     *
     * @param sql
     * @return ResultSet
     */

    public static ResultSet executeQuery(String sql) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException err) {
            err.printStackTrace();
            free(rs, stmt, conn);
        }
        return rs;
    }


    /**
     * 查【Query】
     *
     * @param sql
     * @param obj
     * @return ResultSet
     */

    public static ResultSet executeQuery(String sql, Object... obj) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                pstmt.setObject(i + 1, obj[i]);
            }
            rs = pstmt.executeQuery();
        } catch (SQLException err) {
            err.printStackTrace();
            free(rs, pstmt, conn);
        }
        return rs;
    }


    /**
     * 判断记录是否存在
     *
     * @param sql
     * @return Boolean
     */

    public static Boolean isExist(String sql) {
        ResultSet rs = null;
        try {
            rs = executeQuery(sql);
            rs.last();
            int count = rs.getRow();
            return count > 0;
        } catch (SQLException err) {
            err.printStackTrace();
            free(rs);
            return false;
        } finally {
            free(rs);
        }
    }


    /**
     * 判断记录是否存在
     *
     * @param sql
     * @return Boolean
     */

    public static Boolean isExist(String sql, Object... obj) {
        ResultSet rs = null;
        try {
            rs = executeQuery(sql, obj);
            rs.last();
            int count = rs.getRow();
            return count > 0;
        } catch (SQLException err) {
            err.printStackTrace();
            free(rs);
            return false;
        } finally {
            free(rs);
        }
    }


    /**
     * 获取查询记录的总行数
     *
     * @param sql
     * @return int
     */

    public static int getCount(String sql) {
        int result = 0;
        ResultSet rs = null;
        try {
            rs = executeQuery(sql);
            rs.last();
            result = rs.getRow();
        } catch (SQLException err) {
            free(rs);
            err.printStackTrace();
        } finally {
            free(rs);
        }
        return result;
    }


    /**
     * 获取查询记录的总行数
     *
     * @param sql
     * @param obj
     * @return int
     */

    public static int getCount(String sql, Object... obj) {
        int result = 0;
        ResultSet rs = null;
        try {
            rs = executeQuery(sql, obj);
            rs.last();
            result = rs.getRow();
        } catch (SQLException err) {
            err.printStackTrace();
        } finally {
            free(rs);
        }
        return result;
    }


    /**
     * 释放【ResultSet】资源
     *
     * @param rs
     */

    public static void free(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }


    /**
     * 释放【Statement】资源
     *
     * @param st
     */

    public static void free(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }


    /**
     * 释放【Connection】资源
     *
     * @param conn
     */

    public static void free(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }


    /**
     * 释放所有数据资源
     *
     * @param rs
     * @param st
     * @param conn
     */

    public static void free(ResultSet rs, Statement st, Connection conn) {
        free(rs);
        free(st);
        free(conn);
    }

    public static String getJson(int UID, String UID2) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql;
        conn = getConnection();
        try {
            stmt = conn.createStatement();
            StringBuilder messages = new StringBuilder("{");

            sql = String.format("select * from travelfriends where UID1='%s' and UID2='%d' and state='1'", UID2, UID);
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                sql = String.format("select * from message where UID1='%s' and UID2='%d' and state='0' order by messageID", UID2, UID);
                rs = stmt.executeQuery(sql);
                int i = 0;
                while (rs.next()) {
                    messages.append("\"message").append(i).append("\":\"").append(rs.getString("message")).append("\",");
                    i++;
                }
                messages.deleteCharAt(messages.length() - 1);
                messages.append("}");
                String json = "{" + "\"rows\":\"" + i + "\",\"msg\":" + messages + "}";
//                System.out.println(json);
                sql = String.format("update message set state='1' where UID1='%s' and UID2='%d' and state='0'", UID2, UID);
                stmt.executeUpdate(sql);
                return json;
            } else {
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            free(rs, stmt, conn);
        } finally {
            free(rs, stmt, conn);
        }
        free(rs, stmt, conn);
        return "";
    }
}