package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.RuActinstSite;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper
public interface RuActinstSiteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RuActinstSite record);

    int insertSelective(RuActinstSite record);

    RuActinstSite selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RuActinstSite record);

    int updateByPrimaryKey(RuActinstSite record);

    int updateBatch(List<RuActinstSite> list);

    int updateBatchSelective(List<RuActinstSite> list);

    int batchInsert(@Param("list") List<RuActinstSite> list);

    List<RuActinstSite> selectByActinstId(@Param("actinstId") Long actinstId);

    Set<Long> selectTargetIdByActinstId(@Param("actinstId") Long actinstId);

    Set<Long> selectActinstIdByTargetId(@Param("targetId") Long targetId);

    Set<Long> selectActinstIdByTargetIdIn(@Param("targetIdCollection") Collection<Long> targetIdCollection);

    Long selectFirstTargetIdByActinstId(@Param("actinstId") Long actinstId);

    List<RuActinstSite> selectByProInstId(@Param("proInstId") Long proInstId);

    List<Long> selectActinstIdByProInstId(@Param("proInstId") Long proInstId);

    RuActinstSite getInstProByProInstIdAndActInstIdAndTargetId(@Param("proInstId") Long proInstId,
                                                               @Param("actInstId") Long actInstId,
                                                               @Param("targetId") Long targetId);

    List<Long> selectTargetIdByProInstId(@Param("proInstId") Long proInstId);

    int deleteByActinstIdIn(@Param("actinstIdCollection") Collection<Long> actinstIdCollection);

    int deleteByTargetIdIn(@Param("targetIdCollection") Collection<Long> targetIdCollection);

    int deleteByProInstId(@Param("proInstId") Long proInstId);


}