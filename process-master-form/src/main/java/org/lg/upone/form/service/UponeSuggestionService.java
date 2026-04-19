package org.lg.upone.form.service;

import org.lg.upone.form.db.model.UponeSuggestion;
import java.util.List;
public interface UponeSuggestionService{


    int deleteByPrimaryKey(Long id);

    int insert(UponeSuggestion record);

    int insertSelective(UponeSuggestion record);

    UponeSuggestion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UponeSuggestion record);

    int updateByPrimaryKey(UponeSuggestion record);

    int batchInsert(List<UponeSuggestion> list);

    List<UponeSuggestion> selectAll();
}
