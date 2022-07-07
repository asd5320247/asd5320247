package com.posserver.posserverspringbootres.db;

import org.apache.log4j.Logger;
import com.posserver.posserverspringbootres.web.utils.PosException;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class SQLiteUtils extends DbUtils {
    private final static Logger logger = Logger.getLogger(SQLiteUtils.class);
    private String dbFileName;

    public SQLiteUtils(String dbFileName) {
        this.dbFileName = dbFileName;
    }

    /**
     * 创建数据库连接
     */
    public void connect() throws PosException, SQLException {
        // 检查目录及文件
        File f = new File(dbFileName);

        if (!f.getParentFile().exists()) {
            logger.info("创建目录:" + f.getParentFile().getAbsolutePath());
            f.getParentFile().mkdirs();
        }

        if (f.exists()) {
            logger.info("删除已存在文件:" + dbFileName);
            f.delete();

            long time = new Date().getTime();
            while (time < new Date().getTime() - 10000) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.error("等待文件被删除", e);
                }
                if (!f.exists()) {
                    break;
                }
            }

            if (f.exists()) {
                throw new PosException("清除已存在数据文件失败");
            }
        }

        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
        } catch (SQLException e) {
            throw new PosException("找不到SQLite驱动", e);
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
        } catch (SQLException e) {
            throw new PosException("SQLite数据库连接失败", e);
        }
        super.connect();
    }
}

