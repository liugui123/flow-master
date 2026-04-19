package org.lg.engine.core.db.service.impl;


import org.lg.engine.core.Convert;
import org.lg.engine.core.client.enumerate.ConditionTypeEnum;
import org.lg.engine.core.client.model.Assignee;
import org.lg.engine.core.client.model.ChildShape;
import org.lg.engine.core.client.model.ChildShapeAssigneeInfo;
import org.lg.engine.core.client.model.request.ActinstNextListRequest;
import org.lg.engine.core.client.model.response.NextActResponse;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.RuActinstMapper;
import org.lg.engine.core.db.model.*;
import org.lg.engine.core.db.service.*;
import org.lg.engine.core.service.manager.ProcConfHelper;
import org.lg.engine.core.service.manager.RunTimeManager;
import org.lg.engine.core.service.manager.TreeNode;
import org.lg.engine.core.service.manager.UserHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RuActinstServiceImpl implements RuActinstService {

    @Resource
    private RuActinstMapper ruActinstMapper;

    @Resource
    private RuActinstSiteService ruActinstSiteService;

    @Resource
    private RuTaskService ruTaskService;

    @Resource
    private RuTaskUserService ruTaskUserService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return ruActinstMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(RuActinst record) {
        return ruActinstMapper.insert(record);
    }

    @Override
    public RuActinst selectByPrimaryKey(Long id) {
        return ruActinstMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(RuActinst record) {
        return ruActinstMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<RuActinst> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
        return ruActinstMapper.batchInsert(list);
    }

    @Override
    public int updateStatusById(Integer updatedStatus, Long id) {
        return ruActinstMapper.updateStatusById(updatedStatus, id);
    }


    @Override
    public Integer selectStatusById(Long id) {
        return ruActinstMapper.selectStatusById(id);
    }


    @Override
    public List<Long> selectIdByIdInAndStatus(Collection<Long> idCollection, Integer status) {
        return ruActinstMapper.selectIdByIdInAndStatus(idCollection, status);
    }


    @Override
    public int updateStatusByProcInstId(Integer updatedStatus, Long procinstId) {
        return ruActinstMapper.updateStatusByProcInstId(updatedStatus, procinstId);
    }

    @Override
    public String selectActinstTypeById(Long id) {
        return ruActinstMapper.selectActinstTypeById(id);
    }



    @Override
    public List<RuActinst> selectByProcInstId(Long procinstId) {
        return ruActinstMapper.selectByProcInstId(procinstId);
    }

    @Override
    public int updateStatusByIdIn(Integer updatedStatus, Collection<Long> idCollection) {
        return ruActinstMapper.updateStatusByIdIn(updatedStatus, idCollection);
    }


    @Override
    public List<RuActinst> selectByProcInstIdInAndStatus(Collection<Long> procInstIdCollection, Integer status) {
        return ruActinstMapper.selectByProcInstIdInAndStatus(procInstIdCollection, status);
    }


    @Override
    public List<RuActinst> selectByIdIn(Collection<Long> idCollection) {
        return ruActinstMapper.selectByIdIn(idCollection);
    }

    @Override
    public List<NextActResponse> nextActinstList(ActinstNextListRequest request) {
        Long userTaskId = ruTaskUserService.checkAndGetTaskUserIdByTaskUserKeyFromAll(request.getUserTaskKey());
        RuTask ruTask = ruTaskService.checkAndGetRuTask(ruTaskUserService.selectTaskIdByUserTaskIdFromAll(userTaskId));
        RuProcinst ruProcinst = RunTimeManager.checkAndGetRuProcinstFromAll(ruTask.getProcinstId());
        Long actinstId = ruTask.getActinstId();
        // 节点信息 找到下个节点分支线 然后根据线 在去找对应的节点信息
        Set<Long> targetIdByActinstId = ruActinstSiteService.selectTargetIdByActinstId(actinstId);
        if (Utils.isEmpty(targetIdByActinstId)) {
            return new ArrayList<>(0);
        }
        Assignee starter = new Assignee();
        starter.setId(ruProcinst.getStarterId());
        starter.setName(ruProcinst.getStarterName());
        starter.setDeptId(ruProcinst.getStarterDeptId());
        starter.setDeptName(ruProcinst.getStarterDeptName());
        starter.setOrgId(ruProcinst.getStarterOrgId());
        starter.setOrgName(ruProcinst.getStarterOrgName());

        List<NextActResponse> nextActRespons = new ArrayList<>(targetIdByActinstId.size());
        //添加else条件 线的id targetIdByActinstId 当前节点下面的线 然后判断线的逻辑
        Map<Long, Integer> sequenceIdAndTypeRel = RunTimeManager.getSequenceIdAndTypeRel(targetIdByActinstId);
        List<Long> elseSeq = new ArrayList<>();
        int notPassTotal = 0;

        // 获取到当前节点下面到线 然后判断线是否可以通过 如果可以通过 获取当前线下面的节点 和节点的用户有那些
        for (Long sequenceId : targetIdByActinstId) {
            // else 条件
            if (ConditionTypeEnum.ELSE.getType().equals(sequenceIdAndTypeRel.get(sequenceId))) {
                elseSeq.add(sequenceId);
            }
            // 线  条件类型 = 自定义
            // 根据一些变量信息去判断 节点能不能通过 等等条件
            if (!RunTimeManager.pass(
                    sequenceId,
                    ruTask.getProcinstId(),
                    request.getVarJson(),
                    starter,
                    request.getOperator())) {
                notPassTotal++;
                if (notPassTotal == targetIdByActinstId.size()) {
                    //没有else条件直接弹出
                    if (Utils.isNotEmpty(elseSeq)) {
                        initNextActinstRes(ruProcinst, nextActRespons, elseSeq.get(0));
                        return nextActRespons;
                    }
                }
                continue;
            }
            initNextActinstRes(ruProcinst, nextActRespons, sequenceId);
        }
        return nextActRespons;
    }

    //获取下个节点信息
    private void initNextActinstRes(RuProcinst ruProcinst, List<NextActResponse> nextActRespons, Long sequenceId) {
        RuActinst ruActinst = ruActinstMapper.selectByPrimaryKey(ruActinstSiteService.selectFirstTargetIdByActinstId(sequenceId));
        // 下个节点信息
        NextActResponse nextActResponse = new NextActResponse();
        nextActResponse.setActKey(ruActinst.getActinstKey());
        nextActResponse.setActName(ruActinst.getActinstName());
        nextActResponse.setActType(ruActinst.getActinstType());
        // 获取流程配置中某个子节点信息
        ChildShape childShape = ProcConfHelper.getChildShapeByActKeyAndProcJson(ruActinst.getActinstKey(), ruProcinst.getProcJson());
        if (childShape != null) {
            ChildShapeAssigneeInfo assigneeInfo = childShape.getAssigneeInfo();
            if (assigneeInfo != null) {
                Collection<Assignee> assignees = assigneeInfo.getAssignees();
                if (assignees == null) {
                    assignees = new HashSet<>(1);
                }
                // 是否来自发起人
                if (assigneeInfo.getFromStarter() != null && assigneeInfo.getFromStarter()) {
                    Assignee assignee = new Assignee();
                    assignee.setId(ruProcinst.getStarterId());
                    assignee.setName(ruProcinst.getStarterName());
                    assignee.setDeptId(ruProcinst.getStarterDeptId());
                    assignee.setDeptName(ruProcinst.getStarterDeptName());
                    assignee.setOrgId(ruProcinst.getStarterOrgId());
                    assignee.setOrgName(ruProcinst.getStarterOrgName());
                    assignees.add(assignee);
//                    assigneeInfo.setAssignees(assignees);
                }
                TreeNode treeNode = UserHelper.convertUserJsonToTree(assignees);
                nextActResponse.setTaskUserTree(Convert.INSTANCE.treeNodeToTaskUsersResponse(treeNode));
            }
            nextActResponse.setFilterSelf(childShape.getFilterSelf());
//            nextActResponse.setAssigneeInfo(assigneeInfo);
            nextActResponse.setNodeMode(childShape.getNodeMode());
//            actinstNextResponse.setViewConf(childShape.getViewConf());
            nextActResponse.setIsAllDept(childShape.getIsAllDept() != null && childShape.getIsAllDept());
            nextActResponse.setRequired(childShape.getRequired() != null && childShape.getRequired());
        }
        nextActRespons.add(nextActResponse);
    }


    @Override
    public List<RuActinst> selectActinstTypeByIdIn(Collection<Long> idCollection) {
        if (Utils.isEmpty(idCollection)) {
            return new ArrayList<>(0);
        }
        return ruActinstMapper.selectActinstTypeByIdIn(idCollection);
    }


    @Override
    public Long selectFirstIdByActinstKeyAndProcinstId(String actinstKey, Long procinstId) {
        return ruActinstMapper.selectFirstIdByActinstKeyAndProcinstId(actinstKey, procinstId);
    }

    @Override
    public List<RuActinst> selectByActinstKeyInAndProcinstId(Collection<String> actinstKeyCollection, Long procinstId) {
        return ruActinstMapper.selectByActinstKeyInAndProcinstId(actinstKeyCollection, procinstId);
    }


    @Override
    public void deleteByProcinstId(Long procinstId) {
        if (procinstId == null) {
            return;
        }
        ruActinstMapper.deleteByProcinstId(procinstId);
    }

    @Override
    public Set<Long> selectIdByActinstName(String actinstName) {
        return ruActinstMapper.selectIdByActinstName(actinstName);
    }
}
