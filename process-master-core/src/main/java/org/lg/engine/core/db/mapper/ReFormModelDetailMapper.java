package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.ReFormModelDetail;

import java.util.Collection;
import java.util.List;

public interface ReFormModelDetailMapper {

    int deleteByPrimaryKey(Long id);


    int insert(ReFormModelDetail record);


    ReFormModelDetail selectByPrimaryKey(Long id);


    int updateByPrimaryKey(ReFormModelDetail record);

    int batchInsert(@Param("list") List<ReFormModelDetail> list);


    List<ReFormModelDetail> selectByFormModelId(@Param("formModelId") Long formModelId);

    /**
     * 查询表单模型ID列表中所有结果
     */
    List<ReFormModelDetail> selectByFormModelIdIn(@Param("formModelIdCollection") Collection<Long> formModelIdCollection);

    int updateStatusById(@Param("updatedStatus")Integer updatedStatus,@Param("id")Long id);


}