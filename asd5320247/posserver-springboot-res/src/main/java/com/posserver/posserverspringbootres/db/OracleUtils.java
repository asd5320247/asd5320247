package com.posserver.posserverspringbootres.db;

import com.posserver.posserverspringbootres.web.utils.PosException;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleUtils extends DbUtils {
    private final static Logger logger = Logger.getLogger(OracleUtils.class);

    private OracleDataSource ods = null;
    private String dbHost;
    private String dbName;
    private String dbUser;
    private String dbPass;
    private boolean isV71;
    private String shopId;
    private String shopName;

    public OracleUtils(String dbHost, String dbName, String dbUser, String dbPass) {
        this.dbHost = dbHost;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

    public boolean isV71() {
        return isV71;
    }

    public String getShopId() {
        return shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void connect() throws PosException, SQLException {
        ods = new OracleDataSource();
        String db_Url;
        //sting.contains判断是否包含子字符串。必须是连续的子字符串， 简单来说就是判断dbHost的值是否包含括号中的字符串 ":"
        if (dbHost.contains(":")) {
            db_Url = "jdbc:oracle:thin:@" + dbHost + "/" + dbName;
        } else {
            db_Url = "jdbc:oracle:thin:@" + dbHost + ":1521/" + dbName;
        }
        try {
            ods.setURL(db_Url);
            ods.setUser(dbUser);
            ods.setPassword(dbPass);
            connection = ods.getConnection();
        } catch (SQLException e) {
            logger.error(e);
            throw new PosException("Oracle数据库连接失败", e);
        }
        super.connect();

        this.getShopInfo();
    }

    private void getShopInfo() throws PosException {

        String sql;
        ResultSet rs;
        int count = 0;
        try {
            sql = "SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME='POSSYJMAIN'";
            rs = this.executeQuery(sql);
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();

            if (count == 0) {
                isV71 = true;
                sql = "SELECT * FROM PRIVATEPARA WHERE PPARAID IN ('14','16')";
            } else {
                isV71 = false;
                sql = "SELECT * FROM APPPRIVATEPARA WHERE PPARAID IN ('14','16')";
            }

            rs = this.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("PPARAID").equals("14")) {
                    shopId = rs.getString("PPARAVALUE");
                }
                if (rs.getString("PPARAID").equals("16")) {
                    shopName = new String(rs.getString("PPARAVALUE").getBytes("iso-8859-1"), "GBK");
                }
            }

            if (StringUtils.isEmpty(shopId) || StringUtils.isEmpty(shopName)) {
                throw new PosException("未获取到门店信息");
            }
        } catch (Exception e) {
            throw new PosException("获取门店信息失败:" + e.getLocalizedMessage(), e);
        }
    }
}