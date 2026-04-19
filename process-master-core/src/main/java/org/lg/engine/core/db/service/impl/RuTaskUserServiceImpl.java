package org.lg.engine.core.db.service.impl;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.lg.engine.core.Convert;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.ChildShape;
import org.lg.engine.core.client.model.ChildShapeRun;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.mapper.*;
import org.lg.engine.core.db.model.*;
import org.lg.engine.core.db.service.RuActinstService;
import org.lg.engine.core.db.service.RuTaskService;
import org.lg.engine.core.db.service.RuTaskUserService;
import org.lg.engine.core.service.manager.ProcConfHelper;
import org.lg.engine.core.service.manager.RunTimeManager;
import org.lg.engine.core.utils.Logs;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RuTaskUserServiceImpl implements RuTaskUserService {

    @Resource
    private RuTaskUserMapper ruTaskUserMapper;
    @Resource
    private HiRuTaskUserMapper hiRuTaskUserMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return ruTaskUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(RuTaskUser record) {
        if (CommandContextFactory.getCommandContext().getRuTaskUser() != null) {
            record.setAppKey(CommandContextFactory.getCommandContext().getRuTaskUser().getAppKey());
        } else if (CommandContextFactory.getCommandContext().getRuProcinst() != null) {
            record.setAppKey(CommandContextFactory.getCommandContext().getRuProcinst().getAppKey());
        }
        return ruTaskUserMapper.insert(record);
    }

    @Override
    public RuTaskUser selectByPrimaryKey(Long id) {
        return ruTaskUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(RuTaskUser record) {
        return ruTaskUserMapper.updateByPrimaryKey(record);
    }


    @Override
    public int batchInsert(List<RuTaskUser> list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }
//        List<Long> deptIds = new ArrayList<>();
        for (RuTaskUser record : list) {
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
        try {
            if (list.size() > 2000) {
                List<List<RuTaskUser>> lists = Lists.partition(list, 2000);
                for (List<RuTaskUser> ruTaskUsers : lists) {
                    ruTaskUserMapper.batchInsert(ruTaskUsers);
                }
            } else {
                ruTaskUserMapper.batchInsert(list);
            }
        } catch (Exception e) {
            Logs.error("ruTaskUserMapper.batchInsert error,入参；{}", JSON.toJSONString(list));
            throw e;
        }

        return list.size();
    }

    @Override
    public int updateBatch(List<RuTaskUser> list) {
        return ruTaskUserMapper.updateBatch(list);
    }


    @Override
    public List<RuTaskUser> selectByProcinstIdId(Long procinstId) {
        return ruTaskUserMapper.selectByProcInstId(procinstId);
    }

    @Override
    public void deleteByProcInstId(Long procinstId) {
        ruTaskUserMapper.deleteByProcInstId(procinstId);
    }

    @Override
    public Long countIdByTaskId(Long taskId) {
        return ruTaskUserMapper.countIdByTaskId(taskId);
    }

    @Override
    public Long countByPid(Long pid) {
        return ruTaskUserMapper.countByPid(pid);
    }

    @Override
    public int deleteByTaskId(Long taskId) {
        return ruTaskUserMapper.deleteByTaskId(taskId);
    }

    @Override
    public List<RuTaskUser> selectByTaskId(Long taskId) {
        return ruTaskUserMapper.selectByTaskId(taskId);
    }

    @Override
    public Set<String> selectAssigneeIdByTaskId(Long taskId) {
        return ruTaskUserMapper.selectAssigneeIdByTaskId(taskId);
    }

    @Override
    public List<String> selectAssigneeNameByTaskIdIn(Collection<Long> taskIdCollection) {
        return ruTaskUserMapper.selectAssigneeNameByTaskIdIn(taskIdCollection);
    }

    @Override
    public int updatePidById(Long updatedPid, Long id) {
        return ruTaskUserMapper.updatePidById(updatedPid, id);
    }

    @Override
    public RuTaskUser checkAndGetUserTaskFromAll(Long userTaskId) {
        RuTaskUser ruTaskUser = ruTaskUserMapper.selectByPrimaryKey(userTaskId);
        if (ruTaskUser == null) {
            HiRuTaskUser hiRuTaskUser = hiRuTaskUserMapper.selectFirstByUserTaskId(userTaskId);
            if (hiRuTaskUser != null) {
                ruTaskUser = Convert.INSTANCE.hiRuTaskUserToRuTaskUser(hiRuTaskUser);
                ruTaskUser.setId(hiRuTaskUser.getUserTaskId());
            }
        }
        Assert.isTrue(ruTaskUser != null,
                WfExceptionCode.USER_TASK_AND_HI_BLANK.getMsg(),
                WfExceptionCode.USER_TASK_AND_HI_BLANK.getCode()
        );
        return ruTaskUser;
    }

    @Override
    public List<RuTaskUser> selectByPid(Long pid) {
        return ruTaskUserMapper.selectByPid(pid);
    }

    @Override
    public List<Long> selectIdByPid(Long pid) {
        return ruTaskUserMapper.selectIdByPid(pid);
    }

    @Override
    public int deleteByIdIn(Collection<Long> idCollection) {
        if (Utils.isEmpty(idCollection)) {
            return 0;
        }
        return ruTaskUserMapper.deleteByIdIn(idCollection);
    }

    @Override
    public Long checkAndGetTaskUserIdByTaskUserKeyFromAll(String userTaskKey) {
        Long ruUserTaskId = ruTaskUserMapper.selectFirstIdByTaskUserKey(userTaskKey);
        if (ruUserTaskId != null) {
            return ruUserTaskId;
        }
        Long hiTaskId = hiRuTaskUserMapper.selectFirstUserTaskIdByTaskUserKey(userTaskKey);
        Assert.isTrue(hiTaskId != null,
                WfExceptionCode.ILLEGAL_TASK_USER_KEY.getMsg(),
                WfExceptionCode.ILLEGAL_TASK_USER_KEY.getCode()
        );
        return hiTaskId;
    }

    @Resource
    private RuProcinstMapper ruProcinstMapper;
    @Resource
    private ReProcessMapper reProcessMapper;
    @Resource
    private HiRuProcinstMapper hiRuProcinstMapper;
    @Resource
    private RuActinstMapper ruActinstMapper;


    @Override
    public List<RuTaskUser> selectByTaskIdIn(Collection<Long> taskIdCollection) {
        if (CollectionUtils.isEmpty(taskIdCollection)) {
            return new ArrayList<>();
        }
        return ruTaskUserMapper.selectByTaskIdIn(taskIdCollection);
    }


    @Override
    public Long countByTaskIdInAndStatusNot(Collection<Long> taskIdCollection, Integer notStatus) {
        if (Utils.isEmpty(taskIdCollection) || notStatus == null) {
            return 0L;
        }
        return ruTaskUserMapper.countByTaskIdInAndStatusNot(taskIdCollection, notStatus);
    }

    @Override
    public Long selectTaskIdByUserTaskIdFromAll(Long userTaskId) {
        Long taskId = ruTaskUserMapper.selectFirstTaskIdById(userTaskId);
        if (taskId == null) {
            taskId = hiRuTaskUserMapper.selectFirstTaskIdById(userTaskId);
        }
        return taskId;
    }


}








