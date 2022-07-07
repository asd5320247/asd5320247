package com.posserver.posserverspringbootres.web.utils;

import com.posserver.posserverspringbootres.dao.DbMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Locale;


@Component
public class InitBean implements InitializingBean {
    private static Logger logger = Logger.getLogger(InitBean.class);

    @Autowired
    DbMapper dbMapper;

    @Value("${data.path}")
    private String dataPath;

    private static String __dataPath;

    public static String getDataPath() {
        return __dataPath;
    }

    @Override
    public void afterPropertiesSet(){
        logger.info("InitializingBean...");

        //设置本地语言，默认中文Oracle报错信息乱码
        Locale.setDefault(Locale.ENGLISH);

        //创建数据目录
        File f = new File(dataPath);
        logger.info(dataPath);
        if (!f.exists() && !f.mkdirs()) {
            logger.warn("创建数据目录失败");
        }

        __dataPath = dataPath;

        try {
            dbMapper.creatPayTable();
            dbMapper.createBaseDbTable();
            dbMapper.createShopTable();
            dbMapper.createSyjTable();
            dbMapper.createPackPosTable();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
