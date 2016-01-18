package com.mama100.crawler.framework.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mama100.crawler.framework.domain.BasePage;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/** 
 * Created by DELL on 2015-9-9.
 */
public class DbUtils {
    
	private static ComboPooledDataSource ds = new ComboPooledDataSource();
    
    public static int insertOrUpdate(String sql){
        Connection conn = null;
        Statement statement = null;
        int  result=-1;
        try {
            conn= ds.getConnection();
            statement = conn.createStatement();
            result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("exeception sql:" + sql);
        }finally {
            try {
                statement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("exeception sql:" + sql);
            }
        }
        return result;
    }

    public static ResultSet query(String sql){
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn= ds.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                statement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return  resultSet;
    }

    public static boolean insertItem(BasePage obj){
        Connection conn = null;
        boolean result = true;
        String sql=null;
        try {
            conn=ds.getConnection();
            conn.setAutoCommit(false);
            sql = "";
            PreparedStatement preState = conn.prepareStatement(sql);
            // TODO
            preState.executeUpdate();
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            result = false;
            System.out.println("SQLexception sql:" + sql);
            e.printStackTrace();
        }
        return  result;
    }
  	
}
