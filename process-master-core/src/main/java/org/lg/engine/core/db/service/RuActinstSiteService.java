package org.lg.engine.core.db.service;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.RuActinstSite;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RuActinstSiteService {

    int deleteByPrimaryKey(Long id);

    int insert(RuActinstSite record);

    RuActinstSite selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RuActinstSite record);

    int batchInsert(List<RuActinstSite> list);

    Set<Long> selectTargetIdByActinstId(@Param("actinstId") Long actinstId);

    Set<Long> selectActinstIdByTargetId(@Param("targetId") Long targetId);


    List<RuActinstSite> selectByProInstId(Long proInstId);


    void deleteByProcinstId(Long procinstId);

    Long selectFirstTargetIdByActinstId(@Param("actinstId") Long actinstId);
}