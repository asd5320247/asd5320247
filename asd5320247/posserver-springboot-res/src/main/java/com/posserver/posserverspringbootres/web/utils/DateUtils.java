package com.posserver.posserverspringbootres.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件名：DateUtils.java 日期处理相关工具类
 * 版本信息：V1.0
 * 日期：2013-03-11
 * Copyright BDVCD Corporation 2013
 * 版权所有 http://www.bdvcd.com
 */
public class DateUtils {

    /**
     * 定义常量*
     */
    private static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";

    /**
     * 获取系统当前时间
     */
    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
        return df.format(new Date());
    }

    /**
     * 获取系统当前时间
     */
    public static String getNowTime(String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
        return df.format(new Date());
    }

}
