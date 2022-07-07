package com.posserver.posserverspringbootres.web.service;

import com.posserver.posserverspringbootres.pojo.Shop;
import com.posserver.posserverspringbootres.pojo.Syj;

import java.util.List;

public interface SystemService {
    List selectAllShop();

    String getDefaultGroupId();

    Shop selectShop(String shopId);

    String getDefaultGroupName();

    boolean saveShop(Shop shop);

    boolean deleteShop(String shopId);

    Syj selectSyj(String shopId, String syjId);

    boolean saveSyj(Syj syj);

    List<Syj> selectAllSyj();

    int deleteSyj(Syj syj);

    int updateSyjType(Syj syj);
}
