package org.lg.engine.core.db.service.impl;

import org.lg.engine.core.Convert;
import org.lg.engine.core.client.enumerate.ActinstStatusEnum;
import org.lg.engine.core.client.model.request.ProcessDetailByProcinstKeyRequest;
import org.lg.engine.core.client.model.request.ProcinstPageRequest;
import org.lg.engine.core.client.model.response.ProcessDetailActStatus;
import org.lg.engine.core.client.model.response.ProcessDetailByProcResponse;
import org.lg.engine.core.client.model.response.ProcinstActinstResponse;
import org.lg.engine.core.client.model.response.ProcinstPageReponse;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.HiRuProcinstMapper;
import org.lg.engine.core.db.mapper.RuActinstMapper;
import org.lg.engine.core.db.mapper.RuProcinstMapper;
import org.lg.engine.core.db.model.RuActinst;
import org.lg.engine.core.db.model.RuProcinst;
import org.lg.engine.core.db.service.RuActinstService;
import org.lg.engine.core.db.service.RuProcinstService;
import org.lg.engine.core.db.service.RuTaskService;
import org.lg.engine.core.db.service.RuTaskUserService;
import org.lg.engine.core.service.manager.RunTimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RuProcinstServiceImpl implements RuProcinstService {

    @Resource
    private RuProcinstMapper ruProcinstMapper;
    @Resource
    private RuActinstMapper ruActinstMapper;
    @Resource
    private HiRuProcinstMapper hiRuProcinstMapper;


    @Autowired
    private RuActinstService actinstService;

    @Autowired
    private RuTaskService taskService;

    @Autowired
    private RuTaskUserService taskUserService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return ruProcinstMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(RuProcinst record) {
        return ruProcinstMapper.insert(record);
    }

    @Override
    public RuProcinst selectByPrimaryKey(Long id) {
        return ruProcinstMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(RuProcinst record) {
        return ruProcinstMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<RuProcinst> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        return ruProcinstMapper.batchInsert(list);
    }


    @Override
    public Page<ProcinstPageReponse> page(ProcinstPageRequest request) {
        Page<ProcinstPageReponse> res = new Page<>();
        request.setStartRow(request.getStartRow());
        request.setEndRow(request.getEndRow() + 1);
        List<RuProcinst> ruProcinsts = ruProcinstMapper.page(request);
        if (Utils.isEmpty(ruProcinsts)) {
            return res;
        }

        List<ProcinstPageReponse> resList = Convert.INSTANCE.ruProcinstsToProcinstPageResponses(ruProcinsts);
        List<Long> procinstIds = ruProcinsts.stream().map(RuProcinst::getId).collect(Collectors.toList());
        List<RuActinst> allRunningRuActinsts = actinstService
                .selectByProcInstIdInAndStatus(procinstIds, ActinstStatusEnum.RUNNING.getCode());
        if (Utils.isNotEmpty(allRunningRuActinsts)) {
            Map<Long, List<RuActinst>> procinstIdAndRuActinsts = allRunningRuActinsts.stream()
                    .collect(Collectors.groupingBy(RuActinst::getProcinstId));

            for (ProcinstPageReponse procinstPageReponse : resList) {
                List<RuActinst> ruActinsts = procinstIdAndRuActinsts.get(procinstPageReponse.getId());
                if (Utils.isEmpty(ruActinsts)) {
                    continue;
                }
                List<ProcinstActinstResponse> actinsts = Convert.INSTANCE
                        .ruActinstsToProcinstActinstResponses(ruActinsts);
                procinstPageReponse.setActinsts(actinsts);
                List<Long> actinstIds = actinsts.stream().map(ProcinstActinstResponse::getId)
                        .collect(Collectors.toList());
                if (Utils.isEmpty(actinstIds)) {
                    continue;
                }
                Set<Long> taskIds = taskService.selectIdByActinstIdIn(actinstIds);
                if (Utils.isEmpty(taskIds)) {
                    continue;
                }
                procinstPageReponse.setAssignees(taskUserService.selectAssigneeNameByTaskIdIn(taskIds));
            }
        }

        res.setRows(resList);
        res.setTotal(ruProcinstMapper.pageCount(request));
        return res;
    }

    @Override
    public List<RuProcinst> selectByIdIn(Collection<Long> idCollection) {
        return ruProcinstMapper.selectByIdIn(idCollection);
    }

    @Override
    public ProcessDetailByProcResponse detail(ProcessDetailByProcinstKeyRequest request) {
        RuProcinst ruProcinst = RunTimeManager.checkAndGetProcinstByKey(request.getProcinstKey());
        ProcessDetailByProcResponse response = Convert.INSTANCE.procinstToProcessDetailByProcinstIdRequest(ruProcinst);
        List<RuActinst> ruActinsts = ruActinstMapper.selectByProcInstId(ruProcinst.getId());
        List<ProcessDetailActStatus> statuses = Convert.INSTANCE.ruActinstsToProcessDetailActStatus(ruActinsts);
        response.setStatuses(statuses);
        return response;
    }

    @Override
    public void updateVarJsonById(String updatedVarJson, Long id) {
        ruProcinstMapper.updateVarJsonById(updatedVarJson, id);
    }

    @Override
    public RuProcinst selectByKey(String procinstKey) {
        return ruProcinstMapper.selectByProcKey(procinstKey);
    }

    @Override
    public Set<Long> selectIdByProcNameFromAll(String procName) {
        if (Utils.isEmpty(procName)) {
            return new HashSet<>(0);
        }
        Set<Long> res = new HashSet<>(512);
        res.addAll(ruProcinstMapper.selectIdByProcName(procName));
        res.addAll(hiRuProcinstMapper.selectProcinstIdByProcName(procName));
        return res;
    }

}




