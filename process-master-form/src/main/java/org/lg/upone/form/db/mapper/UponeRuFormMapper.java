package org.lg.upone.form.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lg.upone.form.db.model.UponeRuForm;

@Mapper
public interface UponeRuFormMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UponeRuForm record);

    int insertSelective(UponeRuForm record);

    UponeRuForm selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UponeRuForm record);

    int updateByPrimaryKey(UponeRuForm record);

    int batchInsert(@Param("list") List<UponeRuForm> list);

    UponeRuForm selectFirstByFormDataKey(@Param("formDataKey") String formDataKey);
}