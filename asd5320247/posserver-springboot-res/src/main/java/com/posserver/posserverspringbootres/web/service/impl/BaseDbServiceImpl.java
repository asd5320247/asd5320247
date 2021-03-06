package com.posserver.posserverspringbootres.web.service.impl;

import com.posserver.posserverspringbootres.pojo.BaseDb;
import com.posserver.posserverspringbootres.dao.BaseDbMapper;
import com.posserver.posserverspringbootres.dao.ShopMapper;
import com.posserver.posserverspringbootres.pojo.Shop;
import com.posserver.posserverspringbootres.pojo.ShopKey;
import com.posserver.posserverspringbootres.web.service.BaseDbService;
import com.posserver.posserverspringbootres.web.utils.DataExport;
import com.posserver.posserverspringbootres.web.utils.DateUtils;
import com.posserver.posserverspringbootres.web.utils.InitBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ksafe
 */
@Service
public class BaseDbServiceImpl implements BaseDbService {

    private static Logger logger = Logger.getLogger(BaseDbServiceImpl.class);
    private static boolean isRunning = false;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    BaseDbMapper baseDbMapper;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ShopMapper shopMapper;

    @Override
    public List<BaseDb> selectAllBaseDb() {
        return baseDbMapper.selectAll();
    }

    @Override
    public BaseDb selectLastBaseDb(String shopId) {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", "0000");
        map.put("shopId", shopId);
        return baseDbMapper.selectLast(map);
    }

    @Override
    public BaseDb selectLastBaseDb(String shopId, String version) {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", "0000");
        map.put("shopId", shopId);
        map.put("version", version);
        return baseDbMapper.selectLast(map);
    }

    private boolean saveBaseDb(BaseDb baseDb) {
        int ret;
        if (baseDbMapper.exists(baseDb) > 0) {
            ret = baseDbMapper.update(baseDb);
        } else {
            ret = baseDbMapper.insert(baseDb);
        }
        return ret > 0;
    }

    @Override
    public boolean deleteBaseDb(String hash) {
        return baseDbMapper.delete(hash) > 0;
    }

    /*
     * ???????????????????????? ????????????,2-22??????5??????????????????????????????
     *
     * @see org.ksafe.org.ksafe.posserver.web.service.BaseDbService#Schedule()
     */
    @Scheduled(cron = "0 0/5 6-22 * * ?")
    @Override
    public void Schedule() {
        generateBaseDb(false);
    }

    @Override
    public boolean generateBaseDb(boolean force) {
        logger.info("????????????????????????????????????...");
        if (isRunning) {
            logger.info("????????????????????????,?????????...");
            return false;
        }
        try {
            isRunning = true;

            List<Shop> lst = shopMapper.selectActivate();
            if (lst != null) {
                logger.info("????????????????????????,?????????:" + lst.size());
                for (Shop shop : lst) {
                    logger.info("????????????[" + shop.getShopId() + "]????????????...");
                    BaseDb baseDb = selectLastBaseDb(shop.getShopId());
                    if (force || baseDb == null || !baseDb.getCreateTime().contains(DateUtils.getNowTime(DateUtils.DATE_SMALL_STR))) {
                        generateBaseDb(shop.getShopId());
                        logger.info("??????[" + shop.getShopId() + "]????????????????????????.");
                    } else {
                        logger.info("??????[" + shop.getShopId() + "]????????????????????????,?????????...");
                    }
                }
            }
            logger.info("??????????????????????????????.");
            return false;
        } finally {
            isRunning = false;
        }
    }

    /**
     * ??????????????????
     */
    private boolean generateBaseDb(String shopId) {
        logger.info("????????????????????????,??????ID:" + shopId);

        Shop shop = shopMapper.select(new ShopKey(shopId));
        if (shop == null) {
            return false;
        }

        BaseDb baseDb = DataExport.generate(shop, InitBean.getDataPath());
        if (baseDb != null) {
            logger.info("??????????????????????????????,??????ID:" + shopId);
            return saveBaseDb(baseDb);
        }
        return false;
    }
}
