package org.lg.engine.core.db.service;

import org.lg.engine.core.db.model.HiRuProcinst;

import java.util.List;

public interface HiRuProcinstService {


    int deleteByPrimaryKey(Long id);

    int insert(HiRuProcinst record);

    HiRuProcinst selectByPrimaryKey(Long id);

    int updateByPrimaryKey(HiRuProcinst record);

    int batchInsert(List<HiRuProcinst> list);

    HiRuProcinst selectByProcInstId(Long procinstId);

    int updateBatch(List<HiRuProcinst> list);

}


































