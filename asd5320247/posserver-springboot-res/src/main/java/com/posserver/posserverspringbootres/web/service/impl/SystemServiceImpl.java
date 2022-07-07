package com.posserver.posserverspringbootres.web.service.impl;

import com.posserver.posserverspringbootres.dao.ShopMapper;
import com.posserver.posserverspringbootres.dao.SyjMapper;
import com.posserver.posserverspringbootres.pojo.Shop;
import com.posserver.posserverspringbootres.pojo.ShopKey;
import com.posserver.posserverspringbootres.pojo.Syj;
import com.posserver.posserverspringbootres.pojo.SyjKey;
import com.posserver.posserverspringbootres.web.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    SyjMapper syjMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ShopMapper shopMapper;

    @Override
    public List<Shop> selectAllShop() {
        return shopMapper.selectAll();
    }

    @Override
    public String getDefaultGroupId() {
        return "0000";
    }

    @Override
    public Shop selectShop(String shopId) {
        return shopMapper.select(new ShopKey(shopId));
    }

    @Override
    public String getDefaultGroupName() {
        return "默认客户";
    }

    @Override
    public boolean saveShop(Shop shop) {
        int ret;
        if (shopMapper.exists(shop) > 0) {
            ret = shopMapper.update(shop);
        } else {
            ret = shopMapper.insert(shop);
        }
        return ret > 0;
    }

    @Override
    public boolean deleteShop(String shopId) {
        ShopKey key = new ShopKey(shopId);
        return shopMapper.exists(key) > 0 && shopMapper.delete(key) > 0;
    }

    @Override
    public Syj selectSyj(String shopId, String syjId) {
        return syjMapper.select(new SyjKey(shopId,syjId));
    }

    @Override
    public boolean saveSyj(Syj syj) {
        int ret;
        if (syjMapper.exists(syj) > 0) {
            ret = syjMapper.update(syj);
        } else {
            ret = syjMapper.insert(syj);
        }
        return ret > 0;
    }

    @Override
    public List<Syj> selectAllSyj() {
        return syjMapper.selectAll();
    }

    @Override
    public int deleteSyj(Syj syj) {
        return syjMapper.delete(syj);
    }

    @Override
    public int updateSyjType(Syj syj) {
        return syjMapper.updateType(syj);
    }

}
