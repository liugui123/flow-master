package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.DeProcess;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface DeProcessMapper {
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
    int insert(DeProcess record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    DeProcess selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(DeProcess record);

    int batchInsert(@Param("list") List<DeProcess> list);

    int updateProcessStatus(@Param("id") Long id, @Param("status") Integer status, @Param("gmtModified") Date gmtModified);

    DeProcess selectFirstByProcKey(@Param("procKey") String procKey);

    List<DeProcess> selectByProcKeyInAndStatus(@Param("procKeyCollection") Collection<String> procKeyCollection, @Param("status") Integer status);

    int deleteByProcKey(@Param("procKey") String procKey);

    String selectFirstProcFormKeyByProcKey(@Param("procKey") String procKey);

    List<String> selectProcFormKeyByProcKeyIn(@Param("procKeyCollection") Collection<String> procKeyCollection);

    int updateBusinessTypeById(@Param("id") Long id, @Param("businessType") Integer businessType);

    List<DeProcess> businessTypeList(@Param("procKeys") List<String> procKeys);

    int updateProcFormKey(@Param("id") Long id, @Param("procFormKey") String procFormKey);

    List<String> selectProcKeyByProcFormKey(@Param("procFormKey") String procFormKey);

    List<String> selectProcKeyByProcName(@Param("procName") String procName);
}