package com.posserver.posserverspringbootres.web.service.impl;

import com.posserver.posserverspringbootres.dao.PackPosMapper;
import com.posserver.posserverspringbootres.dao.SyjMapper;
import com.posserver.posserverspringbootres.pojo.PackPos;
import com.posserver.posserverspringbootres.pojo.PackPosKey;
import com.posserver.posserverspringbootres.pojo.Syj;
import com.posserver.posserverspringbootres.pojo.SyjKey;
import com.posserver.posserverspringbootres.web.service.PackPosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PackPosServiceImpl implements PackPosService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    SyjMapper syjMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    PackPosMapper packPosMapper;

    @Override
    public PackPos selectPackageBySyj(String shopId, String syjId) {
        // 查找收银机
        Syj syj = syjMapper.select(new SyjKey(shopId, syjId));
        if (syj != null) {
            // 查找最新补丁包
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("groupId", syj.getGroupId());
            map.put("shopId", syj.getShopId());
            map.put("type", syj.getType());
            List<PackPos> pkgList = packPosMapper.selectLast(map);
            if (pkgList != null && pkgList.size() > 0) {
                return pkgList.get(0);
            }
        }

        return null;
    }

    @Override
    public List<PackPos> selectAllPackPos() {
        return packPosMapper.selectAll();
    }

    @Override
    public boolean savePackPos(PackPos packPos) {
        if (packPosMapper.exists(packPos) > 0) {
            return packPosMapper.update(packPos) > 0;
        } else {
            return packPosMapper.insert(packPos) > 0;
        }
    }

    @Override
    public boolean deletePackPos(String shopId, String hash) {
        return packPosMapper.delete(new PackPosKey(shopId, hash)) > 0;
    }
}
