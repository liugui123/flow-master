package org.lg.upone.form.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.lg.upone.form.db.model.UponeSuggestion;
import java.util.List;
import org.lg.upone.form.db.mapper.UponeSuggestionMapper;
import org.lg.upone.form.service.UponeSuggestionService;
@Service
public class UponeSuggestionServiceImpl implements UponeSuggestionService{

    @Resource
    private UponeSuggestionMapper uponeSuggestionMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return uponeSuggestionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UponeSuggestion record) {
        return uponeSuggestionMapper.insert(record);
    }

    @Override
    public int insertSelective(UponeSuggestion record) {
        return uponeSuggestionMapper.insertSelective(record);
    }

    @Override
    public UponeSuggestion selectByPrimaryKey(Long id) {
        return uponeSuggestionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UponeSuggestion record) {
        return uponeSuggestionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UponeSuggestion record) {
        return uponeSuggestionMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<UponeSuggestion> list) {
        return uponeSuggestionMapper.batchInsert(list);
    }

    @Override
    public List<UponeSuggestion> selectAll() {
        return uponeSuggestionMapper.selectAll();
    }



}
