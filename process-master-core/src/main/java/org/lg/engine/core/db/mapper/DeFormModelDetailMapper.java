package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.DeFormModelDetail;

import java.util.List;

public interface DeFormModelDetailMapper {

    int deleteByPrimaryKey(Long id);


    int insert(DeFormModelDetail record);


    DeFormModelDetail selectByPrimaryKey(Long id);


    int updateByPrimaryKey(DeFormModelDetail record);

    int batchInsert(@Param("list") List<DeFormModelDetail> list);


    int deleteByFormModelId(@Param("formModelId") Long formModelId);


    List<DeFormModelDetail> selectByFormModelId(@Param("formModelId") Long formModelId);

}