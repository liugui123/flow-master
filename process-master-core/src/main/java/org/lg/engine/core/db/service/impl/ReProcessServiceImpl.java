package org.lg.engine.core.db.service.impl;

import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.ChildShape;
import org.lg.engine.core.client.model.request.StartConfRequest;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.ReProcessMapper;
import org.lg.engine.core.db.model.ReProcess;
import org.lg.engine.core.db.service.ReProcessService;
import org.lg.engine.core.service.manager.ProcConfHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ReProcessServiceImpl implements ReProcessService {

    @Resource
    private ReProcessMapper reProcessMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return reProcessMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ReProcess record) {
        return reProcessMapper.insert(record);
    }

    @Override
    public ReProcess selectByPrimaryKey(Long id) {
        return reProcessMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(ReProcess record) {
        return reProcessMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<ReProcess> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        return reProcessMapper.batchInsert(list);
    }

    @Override
    public ReProcess selectFirstByProcKeyOrderByProcVersionDesc(String procKey) {
        return reProcessMapper.selectFirstByProcKeyOrderByProcVersionDesc(procKey);
    }

    @Override
    public ChildShape startActConf(StartConfRequest request) {
        ReProcess reProcess = selectFirstByProcKeyOrderByProcVersionDesc(request.getProcKey());
        Assert.isTrue(
                reProcess != null,
                WfExceptionCode.PROCESS_RE_BLANK.getMsg(),
                WfExceptionCode.PROCESS_RE_BLANK.getCode()
        );

        String procJson = reProcess.getProcJson();

        return ProcConfHelper.getStartChildShape(procJson);
    }


    @Override
    public ChildShape getActConfByActKey(String procKey, String actKey) {
        ReProcess reProcess = selectFirstByProcKeyOrderByProcVersionDesc(procKey);
        Assert.isTrue(
                reProcess != null,
                WfExceptionCode.PROCESS_RE_BLANK.getMsg(),
                WfExceptionCode.PROCESS_RE_BLANK.getCode()
        );

        String procJson = reProcess.getProcJson();
        ChildShape childShape = ProcConfHelper.getChildShapeByActKeyAndProcJson(actKey, procJson);

        Assert.isTrue(
                childShape != null,
                WfExceptionCode.PROCESS_CHILD_SHAPE_BLANK.getMsg(),
                WfExceptionCode.PROCESS_CHILD_SHAPE_BLANK.getCode()
        );
        return childShape;
    }

    @Override
    public List<ReProcess> selectLastByProcKeyIn(Collection<String> procKeyCollection) {
        if (Utils.isEmpty(procKeyCollection)) {
            return new ArrayList<>();
        }
        return reProcessMapper.selectLastByProcKeyIn(procKeyCollection);
    }

}


