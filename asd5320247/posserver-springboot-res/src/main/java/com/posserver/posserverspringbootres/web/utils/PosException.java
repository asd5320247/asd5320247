package com.posserver.posserverspringbootres.web.utils;

/**
 * PosServer异常
 * Created by ksafe on 2014/5/22.
 */
public class PosException extends Exception {

    public PosException(String message) {
        super(message);
    }

    public PosException(String message, Throwable cause) {
        super(message, cause);
    }
}
