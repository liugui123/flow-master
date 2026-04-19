package org.lg.engine.core.db.service;

import org.lg.engine.core.db.model.RuTaskUser;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RuTaskUserService {


    int deleteByPrimaryKey(Long id);

    int insert(RuTaskUser record);

    RuTaskUser selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RuTaskUser record);

    int batchInsert(List<RuTaskUser> list);

    int updateBatch(List<RuTaskUser> list);


    List<RuTaskUser> selectByProcinstIdId(Long procinstId);

    void deleteByProcInstId(Long procinstId);

    Long countIdByTaskId(Long taskId);


    Long countByPid(Long pid);

    int deleteByTaskId(Long taskId);


    List<RuTaskUser> selectByTaskId(Long taskId);


    Set<String> selectAssigneeIdByTaskId(Long taskId);


    List<String> selectAssigneeNameByTaskIdIn(Collection<Long> taskIdCollection);



    int updatePidById(Long updatedPid, Long id);


    RuTaskUser checkAndGetUserTaskFromAll(Long userTaskId);



    List<RuTaskUser> selectByPid(Long pid);


    List<Long> selectIdByPid(Long pid);


    int deleteByIdIn(Collection<Long> idCollection);


    /**
     * 查找用户任务标识对应的主键
     *
     * @param userTaskKey 用户任务标识
     * @return id
     */
    Long checkAndGetTaskUserIdByTaskUserKeyFromAll(String userTaskKey);

    List<RuTaskUser> selectByTaskIdIn(Collection<Long> taskIdCollection);

    Long countByTaskIdInAndStatusNot(Collection<Long> taskIdCollection, Integer notStatus);

    Long selectTaskIdByUserTaskIdFromAll(Long userTaskId);
}




















