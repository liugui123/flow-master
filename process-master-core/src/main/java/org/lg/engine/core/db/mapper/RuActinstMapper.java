package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.RuActinst;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Mapper
public interface RuActinstMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RuActinst record);

    int insertSelective(RuActinst record);

    RuActinst selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RuActinst record);

    int updateByPrimaryKey(RuActinst record);

    int updateBatch(List<RuActinst> list);

    int updateBatchSelective(List<RuActinst> list);

    int batchInsert(@Param("list") List<RuActinst> list);

    int updateStatusById(@Param("updatedStatus") Integer updatedStatus, @Param("id") Long id);

    int updateStatusAndEndTimeById(@Param("updatedStatus") Integer updatedStatus, @Param("updatedEndTime") Date updatedEndTime, @Param("id") Long id);

    int updateStatusByIdIn(@Param("updatedStatus") Integer updatedStatus, @Param("idCollection") Collection<Long> idCollection);

    Integer selectStatusById(@Param("id") Long id);

    List<Integer> selectStatusByIdIn(@Param("idCollection") Collection<Long> idCollection);

    List<Long> selectIdByIdInAndStatus(@Param("idCollection") Collection<Long> idCollection, @Param("status") Integer status);

    List<Long> selectIdByIdInAndStatusNot(@Param("idCollection") Collection<Long> idCollection, @Param("notStatus") Integer notStatus);

    int updateStatusByProcInstId(@Param("updatedStatus") Integer updatedStatus, @Param("procinstId") Long procinstId);

    String selectActinstTypeById(@Param("id") Long id);

    RuActinst selectByActinstKey(@Param("actinstKey") String actinstKey);

    RuActinst selectFirstByActinstKeyAndStatus(@Param("actinstKey") String actinstKey, @Param("status") Integer status);

    RuActinst selectFirstByActinstKeyAndStatusAndProcinstId(@Param("actinstKey") String actinstKey, @Param("status") Integer status, @Param("procinstId") Long procinstId);

    List<RuActinst> selectActInstId(@Param("status") Integer status, @Param("procinstId") Long procinstId);

    List<RuActinst> selectByProcInstId(@Param("procinstId") Long procinstId);

    List<RuActinst> selectStartActInstId(@Param("actinstType") String actinstType, @Param("procinstId") Long procinstId);

    List<RuActinst> selectByProcInstIdInAndStatus(@Param("procInstIdCollection") Collection<Long> procInstIdCollection, @Param("status") Integer status);

    List<RuActinst> selectByIdIn(@Param("idCollection") Collection<Long> idCollection);

    List<RuActinst> selectByProcinstIdAndStatus(@Param("procinstId") Long procinstId, @Param("status") Integer status);

    /**
     * 获取节点的类型 和id  >> 用户节点，系统节点，线和id
     *
     * @param idCollection
     * @return
     */
    List<RuActinst> selectActinstTypeByIdIn(@Param("idCollection") Collection<Long> idCollection);

    Long selectFirstIdByActinstKeyAndProcinstId(@Param("actinstKey") String actinstKey, @Param("procinstId") Long procinstId);

    Set<Long> selectIdByActinstName(@Param("actinstName") String actinstName);

    /**
     * 获取所有的节点数据，后门订正历史数据用
     *
     * @return
     */
    List<RuActinst> backdoorPage(@Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize);

    List<RuActinst> selectByActinstKeyIn(@Param("actinstKeyCollection") Collection<String> actinstKeyCollection);

    List<RuActinst> selectByActinstKeyInAndProcinstId(@Param("actinstKeyCollection") Collection<String> actinstKeyCollection, @Param("procinstId") Long procinstId);

    String selectVarJsonById(@Param("id") Long id);

    List<Long> selectIdByProcinstIdAndActinstNameIn(@Param("procinstId") Long procinstId, @Param("actinstNames") List<String> actinstNames);

    int deleteByActinstKeyIn(@Param("actinstKeyCollection") Collection<String> actinstKeyCollection);

    int deleteByIdIn(@Param("idCollection") Collection<Long> idCollection);

    int deleteByProcinstId(@Param("procinstId") Long procinstId);


}