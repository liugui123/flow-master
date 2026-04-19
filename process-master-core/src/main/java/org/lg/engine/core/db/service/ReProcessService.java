package org.lg.engine.core.db.service;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.client.model.ChildShape;
import org.lg.engine.core.client.model.request.StartConfRequest;
import org.lg.engine.core.db.model.ReProcess;
import org.lg.engine.core.db.model.RuTask;
import org.lg.engine.core.db.model.RuTaskUser;

import java.util.Collection;
import java.util.List;

public interface ReProcessService {

    int deleteByPrimaryKey(Long id);

    int insert(ReProcess record);

    ReProcess selectByPrimaryKey(Long id);

    int updateByPrimaryKey(ReProcess record);

    int batchInsert(List<ReProcess> list);


    ReProcess selectFirstByProcKeyOrderByProcVersionDesc(@Param("procKey") String procKey);


    ChildShape startActConf(StartConfRequest request);


    ChildShape getActConfByActKey(String procKey, String actKey);

    List<ReProcess> selectLastByProcKeyIn(Collection<String> procKeyCollection);

}

















































