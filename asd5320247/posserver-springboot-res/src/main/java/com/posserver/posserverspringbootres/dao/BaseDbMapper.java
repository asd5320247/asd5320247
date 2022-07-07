package com.posserver.posserverspringbootres.dao;

import com.posserver.posserverspringbootres.pojo.BaseDb;
import com.posserver.posserverspringbootres.pojo.BaseDbKey;
import java.util.List;
import java.util.Map;

public interface BaseDbMapper {

    /**
     * 查找所有脱网数据
     */
    List<BaseDb> selectAll();

    /**
     * 查找脱网数据
     */
    BaseDb select(BaseDbKey key);

    /**
     * 检查脱网数据记录是否存在
     */
    int exists(BaseDbKey key);

    /**
     * 插入脱网数据记录
     */
    int insert(BaseDb baseDb);

    /**
     * 更新脱网数据记录
     */
    int update(BaseDb baseDb);

    /**
     * 删除脱网数据记录
     */
    int delete(String hash);

    /**
     * 查找门店最新脱网数据
     */
    BaseDb selectLast(Map<String, Object> map);

}
