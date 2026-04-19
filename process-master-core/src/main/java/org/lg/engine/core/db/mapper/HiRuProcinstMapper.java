package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.HiRuProcinst;

import java.util.Collection;
import java.util.List;

public interface HiRuProcinstMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(HiRuProcinst record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    HiRuProcinst selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(HiRuProcinst record);

    int updateBatch(List<HiRuProcinst> list);

    int batchInsert(@Param("list") List<HiRuProcinst> list);

    HiRuProcinst selectByProcInstId(@Param("procinstId") Long procinstId);


    List<Long> selectProcinstIdByProcName(@Param("procName") String procName);

    List<String> selectDeKeyByProcName(@Param("procName") String procName);

    List<HiRuProcinst> selectProcinstByProcName(@Param("procName") String procName);

    List<HiRuProcinst> selectByProcinstIdIn(@Param("procinstIdCollection") Collection<Long> procinstIdCollection);

    Long selectIdByBizIdAndProcFormDataKey(@Param("bizId") String bizId,
                                           @Param("procFormDataKey") String procFormDataKey);

    HiRuProcinst selectByProcKey(@Param("procKey") String procKey);
}
