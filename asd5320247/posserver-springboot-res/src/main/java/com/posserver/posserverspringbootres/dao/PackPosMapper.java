package com.posserver.posserverspringbootres.dao;

import com.posserver.posserverspringbootres.pojo.PackPos;
import com.posserver.posserverspringbootres.pojo.PackPosKey;

import java.util.List;
import java.util.Map;

public interface PackPosMapper {

    /**
     * 查找所有补丁包
     */
    List<PackPos> selectAll();

    /**
     * 补丁包是否存在
     */
    int exists(PackPosKey key);

    /**
     * 插入补丁包记录
     */
    int insert(PackPos packPos);

    /**
     * 更新补丁包记录
     */
    int update(PackPos packPos);

    /**
     * 删除补丁包
     */
    int delete(PackPosKey key);

    /**
     * 查找最新補丁包
     */
    List<PackPos> selectLast(Map<String, Object> map);

}
