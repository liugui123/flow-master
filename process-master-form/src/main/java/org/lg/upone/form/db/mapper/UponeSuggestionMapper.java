package org.lg.upone.form.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lg.upone.form.db.model.UponeSuggestion;

@Mapper
public interface UponeSuggestionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UponeSuggestion record);

    int insertSelective(UponeSuggestion record);

    UponeSuggestion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UponeSuggestion record);

    int updateByPrimaryKey(UponeSuggestion record);

    int batchInsert(@Param("list") List<UponeSuggestion> list);

    List<UponeSuggestion> selectAll();
}