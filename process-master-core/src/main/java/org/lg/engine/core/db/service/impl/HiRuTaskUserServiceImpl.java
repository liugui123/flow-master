package org.lg.engine.core.db.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.lg.engine.core.Convert;
import org.lg.engine.core.client.enumerate.UserTaskFlagsEnum;
import org.lg.engine.core.client.exception.WfException;
import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.client.model.request.CopyUserTaskRequest;
import org.lg.engine.core.client.model.request.DoneUserTaskRequest;
import org.lg.engine.core.client.model.request.MyStartUserTaskRequest;
import org.lg.engine.core.client.model.request.base.BaseUserTaskQueryRequest;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.context.CommandApplications;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.mapper.HiRuTaskUserMapper;
import org.lg.engine.core.db.model.AssigneeOrgAndDept;
import org.lg.engine.core.db.model.HiRuTaskUser;
import org.lg.engine.core.db.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class HiRuTaskUserServiceImpl implements HiRuTaskUserService {

    @Resource
    private HiRuTaskUserMapper hiRuTaskUserMapper;

    @Resource
    private RuProcinstService ruProcinstService;

    @Resource
    private RuActinstService ruActinstService;

    @Resource
    private RuTaskService ruTaskService;

    @Resource
    private RuTaskUserService ruTaskUserService;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return hiRuTaskUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(HiRuTaskUser record) {
        if (CommandContextFactory.getCommandContext().getRuTaskUser() != null) {
            record.setAppKey(CommandContextFactory.getCommandContext().getRuTaskUser().getAppKey());
        } else if (CommandContextFactory.getCommandContext().getRuProcinst() != null) {
            record.setAppKey(CommandContextFactory.getCommandContext().getRuProcinst().getAppKey());
        }
        return hiRuTaskUserMapper.insert(record);
    }

    @Override
    public HiRuTaskUser selectByPrimaryKey(Long id) {
        return hiRuTaskUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(HiRuTaskUser record) {
        return hiRuTaskUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public int batchInsert(List<HiRuTaskUser> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
//        List<Long> deptIds = new ArrayList<>();
        for (HiRuTaskUser record : list) {
            if (CommandContextFactory.getCommandContext().getRuTaskUser() != null) {
                record.setAppKey(CommandContextFactory.getCommandContext().getRuTaskUser().getAppKey());
            } else if (CommandContextFactory.getCommandContext().getRuProcinst() != null) {
                record.setAppKey(CommandContextFactory.getCommandContext().getRuProcinst().getAppKey());
            }
//            if (StringUtils.isBlank(record.getAssigneeDeptName()) && NumberUtils
//                    .isCreatable(record.getAssigneeDeptId())) {
//                deptIds.add(Long.parseLong(record.getAssigneeDeptId()));
//            }
        }
//        if (deptIds.size() > 0) {
//            Map<Long, String> deptNameMap = CommandApplications.getRemoteService().getDeptNameMapByDeptIds(deptIds);
//            list.forEach(v -> {
//                if (StringUtils.isBlank(v.getAssigneeDeptName()) && NumberUtils
//                        .isCreatable(v.getAssigneeDeptId())) {
//                    v.setAssigneeDeptName(deptNameMap.get(Long.parseLong(v.getAssigneeDeptId())));
//                }
//            });
//        }
        return hiRuTaskUserMapper.batchInsert(list);
    }

    @Override
    public Long countIdByTaskId(Long taskId) {
        return hiRuTaskUserMapper.countIdByTaskId(taskId);
    }

    @Override
    public List<HiRuTaskUser> selectByAssigneeIdAndHasFlags(String assigneeId, Integer flags) {
        return hiRuTaskUserMapper.selectByAssigneeIdAndHasFlags(assigneeId, flags);
    }

    @Override
    public List<HiRuTaskUser> selectByAssigneeIdOrderByGmtCreateDesc(String assigneeId) {
        return hiRuTaskUserMapper.selectByAssigneeIdOrderByGmtCreateDesc(assigneeId);
    }

    @Override
    public List<HiRuTaskUser> selectByTaskIdOrderByIdAsc(Long taskId) {
        return hiRuTaskUserMapper.selectByTaskIdOrderByIdAsc(taskId);
    }

    @Override
    public List<HiRuTaskUser> selectFinishedByProcInstId(Long procinstId) {
        Integer status = 1;
        return hiRuTaskUserMapper.selectFinishedByProcInstId(procinstId, status);
    }

    @Override
    public List<HiRuTaskUser> userListByTaskId(Long taskId) {
        return hiRuTaskUserMapper.selectByTaskId(taskId);
    }

    @Override
    public List<HiRuTaskUser> selectByProcinstIdIn(Collection<Long> procinstIdCollection) {
        return hiRuTaskUserMapper.selectByProcinstIdIn(procinstIdCollection);
    }

    @Override
    public HiRuTaskUser selectByAssigneeIdAndTaskId(String assigneeId, Long taskId) {
        return hiRuTaskUserMapper.selectByAssigneeIdAndTaskId(assigneeId, taskId);
    }

    @Override
    public Long selectTaskIdByUserTaskId(Long userTaskId) {
        return hiRuTaskUserMapper.selectFirstTaskIdByUserTaskId(userTaskId);
    }

    @Override
    public List<HiRuTaskUser> selectHiRuTaskUserByHiTaskId(Long hiTaskId) {
        return hiRuTaskUserMapper.selectByTaskId(hiTaskId);
    }

    @Override
    public List<HiRuTaskUser> selectByTaskIdAndStatusIn(Long taskId, Collection<Integer> statusCollection) {
        return hiRuTaskUserMapper.selectByTaskIdAndStatusIn(taskId, statusCollection);
    }

    @Override
    public void updateBatch(List<HiRuTaskUser> list) {
        hiRuTaskUserMapper.updateBatch(list);
    }

    @Override
    public HiRuTaskUser selectFirstByUserTaskId(Long userTaskId) {
        return hiRuTaskUserMapper.selectFirstByUserTaskId(userTaskId);
    }

    @Override
    public HiRuTaskUser selectFirstByProcinstIdAndAssigneeIdAndAssigneeOrgId(Long procinstId, String assigneeId,
                                                                             String assigneeOrgId) {
        return hiRuTaskUserMapper
                .selectFirstByProcinstIdAndAssigneeIdAndAssigneeOrgId(procinstId, assigneeId, assigneeOrgId);
    }

    @Override
    public List<HiRuTaskUser> selectByTaskIdWithFiltersOrderByIdAsc(Long taskId, List<String> assigneeIds,
                                                                    Boolean commentNotBlank) {
        return hiRuTaskUserMapper.selectByTaskIdWithFiltersOrderByIdAsc(taskId, assigneeIds, commentNotBlank);
    }

    /**
     * 获取历史任务
     */
    @Override
    public Page<HiRuTaskUser> pageHiRuTaskUser(BaseUserTaskQueryRequest request) {
        Page<HiRuTaskUser> res = new Page<>();
        Operator operator = request.getOperator();
        String procinstName = request.getProcinstName();
        Set<Long> procinstIds = null;
        if (Utils.isNotEmpty(procinstName)) {
            procinstIds = ruProcinstService.selectIdByProcNameFromAll(procinstName);
            if (Utils.isEmpty(procinstIds)) {
                return new Page<>();
            }
        }
        String actinstName = request.getActinstName();
        Set<Long> taskIds = null;
        if (Utils.isNotEmpty(actinstName)) {
            Set<Long> actinstIdByName = ruActinstService.selectIdByActinstName(actinstName);
            if (Utils.isEmpty(actinstIdByName)) {
                return new Page<>();
            }
            taskIds = ruTaskService.selectTaskIdByActinstIdsFromAll(actinstIdByName);
        }

        //查询不同的历史任务
        UserTaskFlagsEnum flagsEnum;
        if (request instanceof DoneUserTaskRequest) {
            //只查已办
            flagsEnum = null;
        } else if (request instanceof CopyUserTaskRequest) {
            flagsEnum = UserTaskFlagsEnum.COPY;
        } else if (request instanceof MyStartUserTaskRequest) {
            flagsEnum = UserTaskFlagsEnum.MY_START;
        } else {
            throw new WfException("查询的历史任务类型不支持");
        }

        List<HiRuTaskUser> hiRuTaskUsers = hiRuTaskUserMapper.pageHiUserTasks(
                operator.getId(),
                operator.getOrgId(),
                request.getStartRow(),
                request.getPageSize(),
                procinstIds,
                taskIds,
                flagsEnum == null ? null : flagsEnum.getFlag(),
                request.getMinGmtCreate(),
                request.getMaxGmtCreate());
        if (Utils.isEmpty(hiRuTaskUsers)) {
            return new Page<>();
        }
        res.setRows(hiRuTaskUsers);
        res.setTotal(hiRuTaskUserMapper.countPageHiUserTasks(
                operator.getId(),
                operator.getOrgId(),
                procinstIds,
                taskIds,
                UserTaskFlagsEnum.MY_START.getFlag(),
                request.getMinGmtCreate(),
                request.getMaxGmtCreate()
        ));
        return res;
    }

    @Override
    public Set<AssigneeOrgAndDept> selectAssigneeOrgAndDeptByTaskIdsAndSpecialFlags(Set<Long> taskIds) {
        return hiRuTaskUserMapper.selectAssigneeOrgAndDeptByTaskIdsAndSpecialFlags(taskIds);
    }

    @Override
    public Long selectFirstUserTaskIdByTaskUserKey(String userTaskKey) {
        return hiRuTaskUserMapper.selectFirstUserTaskIdByTaskUserKey(userTaskKey);
    }

    @Override
    public Long selectFirstProcinstIdByTaskUserKey(String userTaskKey) {
        return hiRuTaskUserMapper.selectFirstProcinstIdByTaskUserKey(userTaskKey);
    }

    @Override
    public HiRuTaskUser selectfirstByTaskUserKey(String userTaskKey) {
        return hiRuTaskUserMapper.selectfirstByTaskUserKey(userTaskKey);
    }

    @Override
    public List<HiRuTaskUser> selectByTaskIdIn(Collection<Long> taskIdCollection) {
        if (CollectionUtils.isEmpty(taskIdCollection)) {
            return new ArrayList<>();
        }
        return hiRuTaskUserMapper.selectByTaskIdIn(taskIdCollection);
    }

    @Override
    public int updateCommentByTaskUserKey(String updatedComment, String userTaskKey) {
        return hiRuTaskUserMapper.updateCommentByTaskUserKey(updatedComment, userTaskKey);
    }

    @Override
    public List<HiRuTaskUser> selectByProcinstIdAndAssigneeId(Long procinstId, String assigneeId) {
        return hiRuTaskUserMapper.selectByProcinstIdAndAssigneeId(procinstId, assigneeId);
    }

    @Override
    public List<HiRuTaskUser> selectByUserTaskIdIn(Collection<Long> userTaskIds) {
        return hiRuTaskUserMapper.selectByUserTaskIdIn(userTaskIds);
    }

    @Override
    public Long countByTaskIdAndStatus(Long taskId, Integer status) {
        return hiRuTaskUserMapper.countByTaskIdAndStatus(taskId, 0);
    }

    @Override
    public List<AssigneeOrgAndDept> selectByTaskIdInAndStatus(Collection<Long> taskId, Integer status) {
        if (Utils.isEmpty(taskId)) {
            return new ArrayList<>();
        }
        return hiRuTaskUserMapper.selectByTaskIdInAndStatus(taskId, status);
    }

    @Override
    public List<HiRuTaskUser> selectByTaskUserKey(String userTaskKey) {
        return hiRuTaskUserMapper.selectByTaskUserKey(userTaskKey);
    }

    @Override
    public List<Long> selectUserTaskIdByPid(Long procinstId, Long pid) {
        return hiRuTaskUserMapper.selectUserTaskIdByPid(procinstId, pid);
    }

    @Override
    public List<String> selectAssigneeIdByTaskId(Long taskId) {
        if (taskId == null) {
            return new ArrayList<>(0);
        }
        return hiRuTaskUserMapper.selectAssigneeIdByTaskId(taskId);
    }
}







