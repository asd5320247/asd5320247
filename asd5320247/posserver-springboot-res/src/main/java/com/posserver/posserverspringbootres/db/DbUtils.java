package com.posserver.posserverspringbootres.db;


import com.posserver.posserverspringbootres.web.utils.PosException;

import java.sql.*;

/**
 * 数据库工具
 * Created by ksafe on 2014/5/22.
 */
public class DbUtils {
    Connection connection;
    private Statement statement;

    public void connect() throws PosException, SQLException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new PosException("设置不自动提交失败", e);
        }
        statement = null;
    }

    public void close() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement = null;
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    private Statement getStatement() throws SQLException {
        if (statement == null) {
            statement = connection.createStatement();
        }
        return statement;
    }

    public boolean execute(String sql) throws SQLException {
        return getStatement().execute(sql);
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        return getStatement().executeQuery(sql);
    }

}
