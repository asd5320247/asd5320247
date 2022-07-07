package com.posserver.posserverspringbootres.dao;

import com.posserver.posserverspringbootres.pojo.Syj;
import com.posserver.posserverspringbootres.pojo.SyjKey;

import java.util.List;

public interface SyjMapper {

    /**
     * 查找所有收银机
     */
    List<Syj> selectAll();

    /**
     * 查找收银机
     */
    Syj select(SyjKey key);

    /**
     * 检查收银机是否存在
     */
    int exists(SyjKey key);

    /**
     * 插入收银机记录
     */
    int insert(Syj syj);

    /**
     * 更新收银机记录
     */
    int update(Syj syj);

    /**
     * 删除收银机记录
     */
    int delete(SyjKey key);

    /**
     * 更改收银机Type
     */
    int updateType(Syj syj);

}
