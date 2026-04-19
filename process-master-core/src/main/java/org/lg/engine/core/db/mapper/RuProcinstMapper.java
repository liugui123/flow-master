package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.client.model.request.ProcinstPageRequest;
import org.lg.engine.core.db.model.RuProcinst;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RuProcinstMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RuProcinst record);

    int insertOrUpdate(RuProcinst record);

    int insertOrUpdateSelective(RuProcinst record);

    int insertSelective(RuProcinst record);

    RuProcinst selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RuProcinst record);

    int updateByPrimaryKey(RuProcinst record);

    int batchInsert(@Param("list") List<RuProcinst> list);

    List<RuProcinst> page(ProcinstPageRequest request);

    Integer pageCount(ProcinstPageRequest request);

    List<RuProcinst> selectByIdIn(@Param("idCollection") Collection<Long> idCollection);

    String selectProcNameById(@Param("id") Long id);

    List<Long> selectIdByStarterId(@Param("starterId") String starterId);

    int updateVarJsonById(@Param("updatedVarJson") String updatedVarJson, @Param("id") Long id);


    List<RuProcinst> selectByProcKeyIn(@Param("procKeyCollection") Collection<String> procKeyCollection);

    Set<Long> selectIdByProcName(@Param("procName") String procName);

    Set<String> selectDeKeyByProcName(@Param("procName") String procName);

    List<RuProcinst> selectByProcName(@Param("procName") String procName);

    Long selectIdByBizIdAndProcFormDataKey(@Param("bizId") String bizId,
                                           @Param("procFormDataKey") String procFormDataKey);

    int countByStarterIdAndProcDeKey(@Param("starterId") String starterId, @Param("procDeKey") String procDeKey);

    RuProcinst selectByProcKey(@Param("procKey") String procKey);

    RuProcinst selectByProcDeKey(@Param("procDeKey") String procDeKey);

    List<RuProcinst> selectAll();


}