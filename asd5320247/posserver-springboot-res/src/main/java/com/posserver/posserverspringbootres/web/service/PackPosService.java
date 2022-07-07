package com.posserver.posserverspringbootres.web.service;

import com.posserver.posserverspringbootres.pojo.PackPos;

import java.util.List;

public interface PackPosService {

    /**
     * 查詢所有补丁包
     *
     * @return 补丁包列表
     */
    List<PackPos> selectAllPackPos();

    /**
     * 根据收银机查找补丁包
     *
     * @param shopId 门店ID
     * @param syjId  收银机ID
     * @return 补丁包
     */
    PackPos selectPackageBySyj(String shopId, String syjId);

    boolean savePackPos(PackPos packPos);

    boolean deletePackPos(String shopId, String hash);
}
