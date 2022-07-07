package com.posserver.posserverspringbootres.web.service;


import com.posserver.posserverspringbootres.pojo.BaseDb;

import java.util.List;

public interface BaseDbService {
    BaseDb selectLastBaseDb(String shopId);

    BaseDb selectLastBaseDb(String shopId, String version);

    void Schedule();

    /**
     * 生成脱网数据包
     *
     * @param force 强制生成
     * @return
     */
    boolean generateBaseDb(boolean force);

    boolean deleteBaseDb(String hash);

    List<BaseDb> selectAllBaseDb();
}
