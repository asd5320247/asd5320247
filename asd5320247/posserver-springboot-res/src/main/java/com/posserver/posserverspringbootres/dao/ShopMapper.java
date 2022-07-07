package com.posserver.posserverspringbootres.dao;

import com.posserver.posserverspringbootres.pojo.Shop;
import com.posserver.posserverspringbootres.pojo.ShopKey;

import java.util.List;

public interface ShopMapper {

    /**
     * 查找所有门店
     */
    List<Shop> selectAll();

    /**
     * 查找门店
     */
    Shop select(ShopKey key);

    /**
     * 检查门店是否存在
     */
    int exists(ShopKey key);

    /**
     * 插入门店记录
     */
    int insert(Shop shop);

    /**
     * 更新门店记录
     */
    int update(Shop shop);

    /**
     * 删除门店记录
     */
    int delete(ShopKey key);

    /**
     * 查询所有可用的门店（status=1）
     */
    List<Shop> selectActivate();
}
