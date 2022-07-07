package com.posserver.posserverspringbootres.dao;


import com.posserver.posserverspringbootres.pojo.BaseDb;

import java.util.List;

public interface BaseMapper {
    int insertBase(BaseDb baseDb);
    List<BaseDb> getAllBase();
}
