package org.lg.engine.core.db.service;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.client.model.request.ActinstNextListRequest;
import org.lg.engine.core.client.model.response.NextActResponse;
import org.lg.engine.core.db.model.RuActinst;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface RuActinstService {

    int deleteByPrimaryKey(Long id);

    int insert(RuActinst record);

    RuActinst selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RuActinst record);

    int batchInsert(List<RuActinst> list);


    int updateStatusById(@Param("updatedStatus") Integer updatedStatus, @Param("id") Long id);


    Integer selectStatusById(@Param("id") Long id);

    List<Long> selectIdByIdInAndStatus(@Param("idCollection") Collection<Long> idCollection, @Param("status") Integer status);

    int updateStatusByProcInstId(@Param("updatedStatus") Integer updatedStatus, @Param("procinstId") Long procinstId);


    String selectActinstTypeById(@Param("id") Long id);


    List<RuActinst> selectByProcInstId(@Param("procinstId") Long procinstId);


    int updateStatusByIdIn(@Param("updatedStatus") Integer updatedStatus, @Param("idCollection") Collection<Long> idCollection);

    List<RuActinst> selectByProcInstIdInAndStatus(@Param("procInstIdCollection") Collection<Long> procInstIdCollection, @Param("status") Integer status);

    List<RuActinst> selectByIdIn(@Param("idCollection") Collection<Long> idCollection);

    List<NextActResponse> nextActinstList(ActinstNextListRequest request);

    /**
     * 获取节点的类型 和id  >> 用户节点，系统节点，线和id
     *
     * @param idCollection
     * @return
     */
    List<RuActinst> selectActinstTypeByIdIn(Collection<Long> idCollection);

    Long selectFirstIdByActinstKeyAndProcinstId(String actinstKey, Long procinstId);

    List<RuActinst> selectByActinstKeyInAndProcinstId(@Param("actinstKeyCollection") Collection<String> actinstKeyCollection, @Param("procinstId") Long procinstId);

    void deleteByProcinstId(Long procinstId);

    Set<Long> selectIdByActinstName(String actinstName);
}
