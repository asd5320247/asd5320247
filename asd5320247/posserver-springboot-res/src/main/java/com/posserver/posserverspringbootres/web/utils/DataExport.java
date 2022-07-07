package com.posserver.posserverspringbootres.web.utils;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import com.posserver.posserverspringbootres.db.OracleUtils;
import com.posserver.posserverspringbootres.db.SQLiteUtils;
import com.posserver.posserverspringbootres.pojo.BaseDb;
import com.posserver.posserverspringbootres.pojo.GenerateSQL;
import com.posserver.posserverspringbootres.pojo.Shop;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 将Oracle数据导出到SQLite
 *
 * @author ksafe
 */
public class DataExport {

    private static Logger logger = Logger.getLogger(DataExport.class);

    private static List<GenerateSQL> readGenerateSQL(String fileName) {
        logger.info("读取配置文件: " + fileName);
        if (fileName == null || fileName.isEmpty()) {
            // 如果文件名爲空直接返回
            return null;
        }
        File file = new File(fileName);
        if (!file.exists()) {
            // 如果文件不存在直接返回
            return null;
        }
        // 申明一個Xml解碼器用戶解讀Xml文件
        XStream xs = new XStream();
        try {
            // 解讀Xml文件並賦值給list
            @SuppressWarnings("unchecked")
            List<GenerateSQL> list = (List<GenerateSQL>) xs.fromXML(file);

            if (list.isEmpty()) {
                // 如果取出的list爲空則直接返回null
                return null;
            }
            // 返回list
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 出错返回null
        return null;
    }

    private static void writeGenerateSQL(String fileName, List<GenerateSQL> list) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
        OutputStream out = new FileOutputStream(file, false);
        XStream xs = new XStream();
        xs.toXML(list, out);
        out.close();
        logger.info("生成默认配置配置文件: " + fileName);
    }

    private static List<GenerateSQL> getV71GenerateSQL() {
        List<GenerateSQL> list = new ArrayList<>();
        GenerateSQL sql;

        sql = new GenerateSQL();
        sql.setTableName("PERSON");
        sql.setMemo("人事信息表");
        sql.setCreateTable("CREATE TABLE IF NOT EXISTS PERSON(GH TEXT(8) NOT NULL, NAME TEXT(60) NOT NULL, PASSWD TEXT(32) NULL, TYPE TEXT(1) NOT NULL, ISLOGIN TEXT(1) NOT NULL, SXRQ TEXT(10) NOT NULL, YYYGZ TEXT(255) NULL, ROLE TEXT(4) NOT NULL, PRIVTH TEXT(1) NOT NULL, PRIVQX TEXT(1) NOT NULL, PRIV TEXT(7) NOT NULL, ZKXE FLOAT NOT NULL, ZZKXE FLOAT NOT NULL, ISGRANT TEXT(1) NOT NULL, [GRANT] TEXT(255) NOT NULL,PRIMARY KEY(GH))");
        sql.setSelectTable("SELECT POAGH GH,POANAME NAME,LOGIN.FGETPASSWORD(POAPWD) PASSWD,POAPERTYPE TYPE,POAISLOGIN ISLOGIN,TO_CHAR(POAMAXDATE,'YYYY-MM-DD') SXRQ,POASMCOUNTER YYYGZ,NVL(POAPOSROLE,'00') ROLE,POAGTHPRIV PRIVTH,POAGQXPRIV PRIVQX,POAGDYPRIV||POAGGJPRIV||POAGPRIV1||POAGPRIV2||POAGPRIV3||POAGPRIV4||POAGPRIV5 PRIV,POAGDPZKL ZKXE,POAGZZKL ZZKXE,POAISGRANT ISGRANT,Decode(POAGRFLAG, 'Y', 'ALL', Nvl(POAGRRANGE, 'ALL')) \"GRANT\" FROM POSOPERACCNT WHERE POAPERTYPE IS NOT NULL");
        sql.setStatus(1);
        list.add(sql);

        sql = new GenerateSQL();
        sql.setTableName("COMMOD");
        sql.setMemo("商品信息表");
        sql.setCreateTable("CREATE TABLE IF NOT EXISTS COMMOD(BARCODE TEXT(13) NOT NULL, CODE TEXT(13) NOT NULL, GZ TEXT(20) NOT NULL, DZXL TEXT(10) NOT NULL, PP TEXT(6) NOT NULL, TYPE TEXT(1) NOT NULL, NAME TEXT(40) NOT NULL, UNIT TEXT(4) NOT NULL, SPEC TEXT(2) NOT NULL, BZHL FLOAT NOT NULL, LSJ FLOAT NOT NULL, HYJ FLOAT NOT NULL, PFJ FLOAT NOT NULL, XXTAX FLOAT NOT NULL, XJJG FLOAT NOT NULL, PLSL INT NOT NULL, ISSPEC TEXT(1) NOT NULL, ISBATCH TEXT(1) NOT NULL, ISZK TEXT(1) NOT NULL, ISVIPZK TEXT(1) NOT NULL, ISZS TEXT(1) NOT NULL, ISDZC TEXT(1) NOT NULL, ZKXE FLOAT NOT NULL, ZRXE FLOAT NOT NULL, FXM TEXT(13) NOT NULL, ZKFD FLOAT NOT NULL, MANAGEMODE TEXT(1) NOT NULL, ISXH TEXT(1) NOT NULL, ISYH TEXT(1) NOT NULL,PRIMARY KEY(BARCODE,GZ))");
        sql.setSelectTable("SELECT PGBARCODE BARCODE,PGGDID CODE,PGMFID GZ,PGCATID DZXL,PGPPCODE PP,PGTYPE TYPE,SUBSTR(PGCNAME,1,39) NAME, PGUNIT UNIT,PGUID  SPEC, PGBZHL BZHL,PGSJ LSJ, PGHYJ HYJ,PGPFJ  PFJ,PGXXTAX XXTAX,PGXJJ XJJG,PGMINPL PLSL,PGISMSU ISSPEC,PGISBATCH ISBATCH,PGISZK ISZK,PGISVIP ISVIPZK,PGISZS ISZS, PGISDZCM ISDZC,PGMINZK ZKXE,PGMINZKE ZRXE,PGANALCODE FXM,PGZKFD ZKFD, PGMANAMODE MANAGEMODE,PGISXH ISXH,PGISPOP ISYH FROM POSGOODS");
        sql.setStatus(1);
        list.add(sql);

        sql = new GenerateSQL();
        sql.setTableName("COMSPEC");
        sql.setMemo("商品规格表");
        sql.setCreateTable("CREATE TABLE IF NOT EXISTS COMSPEC(CODE TEXT(13) NOT NULL, SPEC TEXT(2) NOT NULL, UNIT TEXT(4) NOT NULL, BARCODE TEXT(13) NOT NULL, BZHL FLOAT NOT NULL,PRIMARY KEY(CODE,SPEC))");
        sql.setSelectTable("SELECT PGUGDID CODE,PGUID SPEC,PGUUNIT UNIT,PGUBARCODE BARCODE , PGUBZHL BZHL FROM POSGOODSUNITS");
        sql.setStatus(1);
        list.add(sql);

        sql = new GenerateSQL();
        sql.setTableName("AMOUNT");
        sql.setMemo("商品批量表");
        sql.setCreateTable("CREATE TABLE IF NOT EXISTS AMOUNT(CODE TEXT(13) NOT NULL, GZ TEXT(20) NOT NULL, SPEC TEXT(2) NOT NULL, PLLSJ FLOAT NOT NULL, PLHYJ FLOAT NOT NULL, PLSL INT NOT NULL, KSRQ TEXT(10) NOT NULL, JSRQ TEXT(10) NOT NULL,PRIMARY KEY(CODE,GZ,SPEC,PLSL))");
        sql.setSelectTable("SELECT PGAGDID CODE,PGAMFID GZ,PGAUID SPEC,PGASJ PLLSJ,PGAHYJ PLHYJ,PGAMOUNT PLSL,TO_CHAR(GGASTADATE,'YYYY-MM-DD') KSRQ,TO_CHAR(GGAENDDATE,'YYYY-MM-DD') JSRQ FROM POSGOODSAMOUNT WHERE GGAENDDATE >= TRUNC(SYSDATE)");
        sql.setStatus(1);
        list.add(sql);

        sql = new GenerateSQL();
        sql.setTableName("YHCODE");
        sql.setMemo("优惠信息表");
        sql.setCreateTable("CREATE TABLE IF NOT EXISTS YHCODE(SEQNO DOUBLE NOT NULL, DJBH TEXT(15) NOT NULL, TYPE TEXT(1) NOT NULL, CODE TEXT(13) NOT NULL, GZ TEXT(20), DZXL TEXT(10), PP TEXT(6), SPEC TEXT(2), KSRQ TEXT(10) NOT NULL, JSRQ TEXT(10) NOT NULL, KSSJ TEXT(8) NOT NULL, JSSJ TEXT(8) NOT NULL, ZKFD FLOAT NOT NULL, YHSPACE FLOAT NULL, YHLSJ FLOAT NOT NULL, YHHYJ FLOAT NOT NULL, YHZKL FLOAT NOT NULL, YHHYZKL FLOAT NOT NULL, PRIMARY KEY(SEQNO))");
        sql.setSelectTable("SELECT PPISEQ SEQNO,PPIBILLNO DJBH,PPIMODE TYPE,PPIBARCODE CODE,PPIMFID GZ,PPICATID DZXL,PPIPPCODE PP,PPISPEC SPEC,TO_CHAR(PPISTARTDATE,'YYYY-MM-DD') KSRQ,TO_CHAR(PPIENDDATE,'YYYY-MM-DD') JSRQ,PPISTARTTIME KSSJ,PPIENDTIME JSSJ,PPIZKFD ZKFD,0 YHSPACE,PPINEWSJ YHLSJ,PPINEWHYJ YHHYJ,PPINEWRATE YHZKL,PPINEWHYRATE YHHYZKL FROM POSPOPINFO WHERE PPIENDDATE >= TRUNC(SYSDATE)");
        sql.setStatus(1);
        list.add(sql);

        return list;
    }

    private static List<GenerateSQL> getBFutureGenerateSQL() {
        List<GenerateSQL> list = new ArrayList<>();
        GenerateSQL sql;

        sql = new GenerateSQL();
        sql.setTableName("PERSON");
        sql.setMemo("人事信息表");
        sql.setCreateTable("CREATE TABLE IF NOT EXISTS PERSON(GH TEXT(8) NOT NULL, NAME TEXT(60) NOT NULL, PASSWD TEXT(32) NULL, TYPE TEXT(1) NOT NULL, ISLOGIN TEXT(1) NOT NULL, SXRQ TEXT(10) NOT NULL, YYYGZ TEXT(255) NULL, ROLE TEXT(4) NOT NULL, PRIVTH TEXT(1) NOT NULL, PRIVQX TEXT(1) NOT NULL, PRIV TEXT(7) NOT NULL, ZKXE FLOAT NOT NULL, ZZKXE FLOAT NOT NULL, ISGRANT TEXT(1) NOT NULL, [GRANT] TEXT(255) NOT NULL,PRIMARY KEY(GH))");
        sql.setSelectTable("SELECT POAGH GH,POANAME NAME,LOGIN.FGETPASSWORD(POAPWD) PASSWD,POAPERTYPE TYPE,POAISLOGIN ISLOGIN,TO_CHAR(POAMAXDATE,'YYYY-MM-DD') SXRQ,POASMCOUNTER YYYGZ,NVL(POAPOSROLE,'00') ROLE,POAGTHPRIV PRIVTH,POAGQXPRIV PRIVQX,POAGDYPRIV||POAGGJPRIV||POAGPRIV1||POAGPRIV2||POAGPRIV3||POAGPRIV4||POAGPRIV5 PRIV,POAGDPZKL ZKXE,POAGZZKL ZZKXE,POAISGRANT ISGRANT,Decode(POAGRFLAG, 'Y', 'ALL', Nvl(POAGRRANGE, 'ALL')) \"GRANT\" FROM POSOPERACCNT WHERE POAPERTYPE IS NOT NULL");
        sql.setStatus(1);
        list.add(sql);

        sql = new GenerateSQL();
        sql.setTableName("COMMOD");
        sql.setMemo("商品信息表");
        sql.setCreateTable("CREATE TABLE IF NOT EXISTS COMMOD(BARCODE TEXT(13) NOT NULL, CODE TEXT(13) NOT NULL, GZ TEXT(20) NOT NULL, DZXL TEXT(10) NOT NULL, PP TEXT(6) NOT NULL, TYPE TEXT(1) NOT NULL, NAME TEXT(40) NOT NULL, UNIT TEXT(4) NOT NULL, SPEC TEXT(2) NOT NULL, BZHL FLOAT NOT NULL, LSJ FLOAT NOT NULL, HYJ FLOAT NOT NULL, PFJ FLOAT NOT NULL, XXTAX FLOAT NOT NULL, XJJG FLOAT NOT NULL, PLSL INT NOT NULL, ISSPEC TEXT(1) NOT NULL, ISBATCH TEXT(1) NOT NULL, ISZK TEXT(1) NOT NULL, ISVIPZK TEXT(1) NOT NULL, ISZS TEXT(1) NOT NULL, ISDZC TEXT(1) NOT NULL, ZKXE FLOAT NOT NULL, ZRXE FLOAT NOT NULL, FXM TEXT(13) NOT NULL, ZKFD FLOAT NOT NULL, MANAGEMODE TEXT(1) NOT NULL, ISXH TEXT(1) NOT NULL, ISYH TEXT(1) NOT NULL,PRIMARY KEY(BARCODE,GZ))");
        sql.setSelectTable("SELECT BARCODE,GDID CODE,MFID GZ,CATID DZXL,PPCODE PP,GDTYPE TYPE,SUBSTR(NAME,1,39) NAME, UNIT,GDUID SPEC, BZHL,SJ LSJ, HYJ,HYJ2 PFJ,0 XXTAX,0 XJJG,MINPL PLSL,' ' ISSPEC, ISBATCH, ISZK,ISVIP ISVIPZK, ISZS, SUBSTR(NVL(ISDZC,'N'),0,1) ISDZC,MAXZK ZKXE,MAXZKE ZRXE,BARCODE FXM,ZKFD ZKFD, NVL(MANAMODE,' ') MANAGEMODE,ISXH ISXH,ISPOP ISYH FROM POSGOODS");
        sql.setStatus(1);
        list.add(sql);

        sql = new GenerateSQL();
        sql.setTableName("COMSPEC");
        sql.setMemo("商品规格表");
        sql.setCreateTable("CREATE TABLE IF NOT EXISTS COMSPEC(CODE TEXT(13) NOT NULL, SPEC TEXT(2) NOT NULL, UNIT TEXT(4) NOT NULL, BARCODE TEXT(13) NOT NULL, BZHL FLOAT NOT NULL,PRIMARY KEY(CODE,SPEC))");
        sql.setSelectTable("SELECT PGUGDID CODE,PGUID SPEC,PGUUNIT UNIT,PGUBARCODE BARCODE , PGUBZHL BZHL FROM POSGOODSUNITS");
        sql.setStatus(1);
        list.add(sql);

        sql = new GenerateSQL();
        sql.setTableName("AMOUNT");
        sql.setMemo("商品批量表");
        sql.setCreateTable("CREATE TABLE IF NOT EXISTS AMOUNT(CODE TEXT(13) NOT NULL, GZ TEXT(20) NOT NULL, SPEC TEXT(2) NOT NULL, PLLSJ FLOAT NOT NULL, PLHYJ FLOAT NOT NULL, PLSL INT NOT NULL, KSRQ TEXT(10) NOT NULL, JSRQ TEXT(10) NOT NULL,PRIMARY KEY(CODE,GZ,SPEC,PLSL))");
        sql.setSelectTable("SELECT PGAGDID CODE,PGAMFID GZ,PGAUID SPEC,PGASJ PLLSJ,PGAHYJ PLHYJ,PGAMOUNT PLSL,TO_CHAR(GGASTADATE,'YYYY-MM-DD') KSRQ,TO_CHAR(GGAENDDATE,'YYYY-MM-DD') JSRQ FROM POSGOODSAMOUNT WHERE GGAENDDATE >= TRUNC(SYSDATE)");
        sql.setStatus(1);
        list.add(sql);

        sql = new GenerateSQL();
        sql.setTableName("YHCODE");
        sql.setMemo("优惠信息表");
        sql.setCreateTable("CREATE TABLE IF NOT EXISTS YHCODE(SEQNO DOUBLE NOT NULL, DJBH TEXT(15) NOT NULL, TYPE TEXT(1) NOT NULL, CODE TEXT(13) NOT NULL, GZ TEXT(20), DZXL TEXT(10), PP TEXT(6), SPEC TEXT(2), KSRQ TEXT(10) NOT NULL, JSRQ TEXT(10) NOT NULL, KSSJ TEXT(8) NOT NULL, JSSJ TEXT(8) NOT NULL, ZKFD FLOAT NOT NULL, YHSPACE FLOAT NULL, YHLSJ FLOAT NOT NULL, YHHYJ FLOAT NOT NULL, YHZKL FLOAT NOT NULL, YHHYZKL FLOAT NOT NULL, PRIMARY KEY(SEQNO))");
        sql.setSelectTable("SELECT PPISEQ SEQNO,PPIBILLNO DJBH,PPIMODE TYPE,PPIBARCODE CODE,PPIMFID GZ,PPICATID DZXL,PPIPPCODE PP,PPISPEC SPEC,TO_CHAR(PPISTARTDATE,'YYYY-MM-DD') KSRQ,TO_CHAR(PPIENDDATE,'YYYY-MM-DD') JSRQ,PPISTARTTIME KSSJ,PPIENDTIME JSSJ,PPIZKFD ZKFD,0 YHSPACE,PPINEWSJ YHLSJ,PPINEWHYJ YHHYJ,PPINEWRATE YHZKL,PPINEWHYRATE YHHYZKL FROM POSPOPINFO WHERE PPIENDDATE >= TRUNC(SYSDATE)");
        sql.setStatus(1);
        list.add(sql);


        //收银机多柜组销售（控制柜组）
        sql = new GenerateSQL();
        sql.setTableName("POSMANAFRAME");
        sql.setMemo("管理架构表");
        sql.setCreateTable("CREATE TABLE IF NOT EXISTS POSMANAFRAME(PMFCODE TEXT(20) NOT NULL, PMFCNAME TEXT(40) NOT NULL)");
        sql.setSelectTable("SELECT PMFCODE, PMFCNAME FROM POSMANAGEFRAME");
        sql.setStatus(1);
        list.add(sql);

        sql = new GenerateSQL();
        sql.setTableName("SYJGRANGE");
        sql.setMemo("收银范围表");
        sql.setCreateTable("CREATE TABLE IF NOT EXISTS SYJGRANGE(SGRCODE TEXT(2) NOT NULL, SGRMFID TEXT(20) NOT NULL)");
        sql.setSelectTable("SELECT SGRCODE, SGRMFID FROM POSSYJGRANGE");
        sql.setStatus(1);
        list.add(sql);

        sql = new GenerateSQL();
        sql.setTableName("SYJMAIN");
        sql.setMemo("收银机信息表");
        sql.setCreateTable("CREATE TABLE IF NOT EXISTS SYJMAIN(SYJID TEXT(10) NOT NULL, SYJGROUP TEXT(2) NOT NULL)");
        sql.setSelectTable("SELECT SYJID, SYJGROUP FROM POSSYJMAIN");
        sql.setStatus(1);
        list.add(sql);

        return list;
    }

    private static List<GenerateSQL> getDefaultGenerateSQL(boolean v71) {
        //如果没有配置文件则生成默认配置文件
        if (v71) {
            //生成V71配置文件模板
            return getV71GenerateSQL();
        } else {
            return getBFutureGenerateSQL();
        }
    }

    private static List<GenerateSQL> getGenerateSQL(String groupId, String shopId, String dataPath, boolean v71) {
        List<GenerateSQL> list;

        String fileName;

        // 根据客户ID和门店ID生成文件路径
        fileName = dataPath + File.separator + groupId + File.separator + shopId + File.separator + "GenerateSQL.xml";
        // 取门店配置
        list = readGenerateSQL(fileName);

//        if (list == null || list.isEmpty()) {
//            // 如果无门店配置则取客户配置
//            fileName = dataPath + File.separator + groupId + File.separator + "GenerateSQL.xml";
//            list = readGenerateSQL(fileName);
//        }
//
//        if (list == null || list.isEmpty()) {
//            // 如果门店配置和客户配置都没有则取默认配置
//            fileName = dataPath + File.separator + "GenerateSQL.xml";
//            list = readGenerateSQL(fileName);
//        }

        if (list != null && !list.isEmpty()) {

            List<String> tableNames = new ArrayList<>();
            for (GenerateSQL sql : list) {
                tableNames.add(sql.getTableName());
            }

            for (GenerateSQL sql : getDefaultGenerateSQL(v71)) {
                if (!tableNames.contains(sql.getTableName())) {
                    list.add(sql);
                }
            }

            return list;
        }

        //如果没有配置文件则生成默认配置文件
        list = getDefaultGenerateSQL(v71);

        /* 写配置文件
        try {
            writeGenerateSQL(fileName, list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        return list;
    }

    public static BaseDb generate(Shop shop, String dataPath) {
        BaseDb baseDb;
        OracleUtils oracle = new OracleUtils(shop.getDbHost(), shop.getDbName(), shop.getDbUser(), shop.getDbPass());
        SQLiteUtils sqlite = null;
        PreparedStatement sqlitePstmt = null;
        try {
            oracle.connect();

            String dbFileName = dataPath + "/" + shop.getGroupId() + "/" + shop.getShopId() + "/" + DateUtils.getNowTime(DateUtils.DATE_SMALL_STR) + ".db";
            sqlite = new SQLiteUtils(dbFileName);
            sqlite.connect();

            for (GenerateSQL sql : getGenerateSQL(shop.getGroupId(), shop.getShopId(), dataPath, oracle.isV71())) {
                try {
                    logger.info("生成[" + sql.getMemo() + "]");
                    sqlite.execute(sql.getCreateTable());

                    sqlite.execute("DELETE FROM " + sql.getTableName());
                    sqlite.commit();

                    ResultSet oracleRs = oracle.executeQuery(sql.getSelectTable().replace("YYYY/MM/DD", "YYYY-MM-DD"));
                    ResultSetMetaData resultSetMetaData = oracleRs.getMetaData();

                    StringBuilder insertSQL = new StringBuilder("INSERT INTO " + sql.getTableName() + "(");
                    for (int k = 1; k <= resultSetMetaData.getColumnCount(); k++) {
                        insertSQL.append(resultSetMetaData.getColumnName(k)).append(",");
                    }
                    insertSQL = new StringBuilder(insertSQL.substring(0, insertSQL.length() - 1));
                    insertSQL.append(") VALUES (");

                    for (int k = 1; k <= resultSetMetaData.getColumnCount(); k++) {
                        insertSQL.append("?,");
                    }
                    insertSQL = new StringBuilder(insertSQL.substring(0, insertSQL.length() - 1));
                    insertSQL.append(")");

                    logger.info("insertSQL:" + insertSQL);

                    sqlitePstmt = sqlite.prepareStatement(insertSQL.toString());

                    int n = 0;
                    int total = 0;
                    while (oracleRs.next()) {
                        for (int k = 1; k <= resultSetMetaData.getColumnCount(); k++) {
                            if (resultSetMetaData.getColumnTypeName(k).toLowerCase().contains("datetime")) {
                                sqlitePstmt.setDate(k, oracleRs.getDate(k));
                            } else if (resultSetMetaData.getColumnTypeName(k).toLowerCase().contains("number")) {
                                sqlitePstmt.setDouble(k, oracleRs.getDouble(k));
                            } else {
                                if (oracleRs.getString(k) == null) {
                                    sqlitePstmt.setString(k, null);
                                } else {
                                    sqlitePstmt.setString(k, new String(oracleRs.getString(k).getBytes("iso-8859-1"), "GBK"));
                                }
                            }
                        }
                        sqlitePstmt.addBatch();

                        n++;
                        if (n >= 5000) {
                            sqlitePstmt.executeBatch();
                            sqlite.commit();
                            total += n;
                            n = 0;
                            logger.info("写入数据:" + total);
                        }
                    }
                    sqlitePstmt.executeBatch();
                    sqlite.commit();
                    total += n;
                    logger.info("写入数据:" + total);
                } catch (Exception e) {
                    logger.error("到出表数据出错:" + sql.getTableName(), e);
                    sqlite.close();
                    sqlite = null;
                    return null;
                } finally {
                    if (sqlitePstmt != null) {
                        sqlitePstmt.close();
                        sqlitePstmt = null;
                    }
                }
            }
            sqlite.execute("CREATE TABLE VERSION(VERSION TEXT NOT NULL,GENERATE_TIME TEXT NOT NULL)");
            String createTime = DateUtils.getNowTime();
            sqlite.execute("INSERT INTO VERSION(VERSION,GENERATE_TIME) VALUES('" + createTime + "','" + createTime + "')");
            sqlite.commit();
            baseDb = new BaseDb();
            baseDb.setGroupId(shop.getGroupId());
            baseDb.setShopId(shop.getShopId());
            baseDb.setFileName(dbFileName);
            baseDb.setVersion(createTime);
            baseDb.setHash(DigestUtils.md5Hex(new FileInputStream(dbFileName)));
            baseDb.setCreateTime(createTime);
            return baseDb;
        } catch (Exception e) {
            logger.error("生成脱网数据失败", e);
        } finally {
            try {
                oracle.close();

                if (sqlite != null) {
                    sqlite.close();
                }
            } catch (Exception e) {
                logger.error("关闭连接失败", e);
            }
        }

        return null;
    }
}
