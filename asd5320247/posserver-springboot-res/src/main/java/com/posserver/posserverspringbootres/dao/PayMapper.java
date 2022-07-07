package com.posserver.posserverspringbootres.dao;

import com.posserver.posserverspringbootres.pojo.Pay;

import java.util.List;

public interface PayMapper {
    int insertPayList(Pay pay);
    List<Pay> getNewPayList(String outTradeNo);
}
