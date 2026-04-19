package org.lg.engine.core.db.service;

import org.lg.engine.core.client.model.CommentPermission;
import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.client.model.request.ProcessDetailByProcinstKeyRequest;
import org.lg.engine.core.client.model.request.ProcinstPageRequest;
import org.lg.engine.core.client.model.response.ProcessDetailByProcResponse;
import org.lg.engine.core.client.model.response.ProcinstPageReponse;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.core.db.model.RuProcinst;
import org.lg.engine.core.db.model.RuTask;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RuProcinstService {


    int deleteByPrimaryKey(Long id);

    int insert(RuProcinst record);

    RuProcinst selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RuProcinst record);

    int batchInsert(List<RuProcinst> list);

    Page<ProcinstPageReponse> page(ProcinstPageRequest request);


    List<RuProcinst> selectByIdIn(Collection<Long> idCollection);

    ProcessDetailByProcResponse detail(ProcessDetailByProcinstKeyRequest request);

    void updateVarJsonById(String updatedVarJson, Long id);

    RuProcinst selectByKey(String procinstKey);

    Set<Long> selectIdByProcNameFromAll(String procName);
}























































