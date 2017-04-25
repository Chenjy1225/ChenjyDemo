package com.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by chenjy on 2017/3/27.
 */

public class MysqlUtils {

    /**
     * @author chenjy
     * @summary connection to mysql
     * @param url 数据库url
     * @param user 数据库用户名
     * @param password 数据库密码
     * @return Connection
     */
    public static Connection connectionToMysql(String url,String user ,String password){

        Connection con = null;
        try {
            final String DRIVER_NAME = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER_NAME);
            con = DriverManager.getConnection(url, user, password);
            if(!con.isClosed())
            {
                System.out.println("数据库连接成功");
            }

        } catch (ClassNotFoundException e) {
            con = null;
            System.out.println("加载驱动程序出错");
        } catch (java.sql.SQLException e) {
            con = null;
            System.out.println(e.getMessage());
        }catch (Exception e) {
            System.out.println(e.getMessage());

        }

        return con;
    }

    /**
     * @author chenjy
     * @summary select from mysql
     * @param con 数据库连接
     * @param sql 数据库用户名
     * @return String
     */
    public static String query(Connection con, String sql) throws SQLException, JSONException {

        Statement statement = null;
        ResultSet result = null;

        if (con == null) {
            return resultSetToJson(result);
        }


        try {
            statement = con.createStatement();
            result = statement.executeQuery(sql);
            return resultSetToJson(result);
        } catch (SQLException e) {
            e.printStackTrace();
            return resultSetToJson(result);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {

            }
        }
        return sql;
    }

    /**
     * @author chenjy
     * @summary add,delete,update from mysql
     * @param con 数据库连接
     * @param sql 数据库用户名
     * @return Boolean
     */
    public static boolean execSQL(Connection con, String sql) {
        boolean execResult = false;
        if (con == null) {
            return execResult;
        }

        Statement statement = null;

        try {
            statement = con.createStatement();
            if (statement != null) {
                execResult = statement.execute(sql);
            }
        } catch (SQLException e) {
            execResult = false;
        }

        return execResult;
    }


    /**
     * @author chenjy
     * @summary ResultSet to JSON string
     * @param rs 结果集
     * @return String
     */
    public static String resultSetToJson(ResultSet rs) throws SQLException,JSONException
    {

        JSONArray array = new JSONArray();

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            JSONObject jsonObj = new JSONObject();

            for (int i = 1; i <= columnCount; i++) {
                String columnName =metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObj.put(columnName, value);
            }
            array.put(jsonObj);
        }

        return array.toString();
    }

}
