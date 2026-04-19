package org.lg.engine.core.db.service;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.client.model.request.base.BaseUserTaskQueryRequest;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.core.db.model.AssigneeOrgAndDept;
import org.lg.engine.core.db.model.HiRuTaskUser;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface HiRuTaskUserService {


    int deleteByPrimaryKey(Long id);

    int insert(HiRuTaskUser record);

    HiRuTaskUser selectByPrimaryKey(Long id);

    int updateByPrimaryKey(HiRuTaskUser record);

    int batchInsert(List<HiRuTaskUser> list);

    List<HiRuTaskUser> selectByAssigneeIdOrderByGmtCreateDesc(String assigneeId);


    List<HiRuTaskUser> selectByTaskIdOrderByIdAsc(@Param("taskId") Long taskId);


    Long countIdByTaskId(@Param("taskId") Long taskId);


    List<HiRuTaskUser> selectByAssigneeIdAndHasFlags(@Param("assigneeId") String assigneeId,
                                                     @Param("flags") Integer flags);


    List<HiRuTaskUser> selectFinishedByProcInstId(@Param("procinstId") Long procinstId);


    List<HiRuTaskUser> userListByTaskId(@Param("taskId") Long taskId);


    List<HiRuTaskUser> selectByProcinstIdIn(@Param("procinstIdCollection") Collection<Long> procinstIdCollection);


    HiRuTaskUser selectByAssigneeIdAndTaskId(@Param("assigneeId") String assigneeId, @Param("taskId") Long taskId);


    Long selectTaskIdByUserTaskId(Long userTaskId);


    List<HiRuTaskUser> selectHiRuTaskUserByHiTaskId(Long hiTaskId);


    List<HiRuTaskUser> selectByTaskIdAndStatusIn(Long taskId, Collection<Integer> statusCollection);

    void updateBatch(List<HiRuTaskUser> list);


    HiRuTaskUser selectFirstByUserTaskId(Long userTaskId);


    HiRuTaskUser selectFirstByProcinstIdAndAssigneeIdAndAssigneeOrgId(Long procinstId,
                                                                      String assigneeId,
                                                                      String assigneeOrgId);

    List<HiRuTaskUser> selectByTaskIdWithFiltersOrderByIdAsc(Long taskId, List<String> assigneeIds,
                                                             Boolean commentNotBlank);

    Page<HiRuTaskUser> pageHiRuTaskUser(BaseUserTaskQueryRequest request);

    Set<AssigneeOrgAndDept> selectAssigneeOrgAndDeptByTaskIdsAndSpecialFlags(Set<Long> taskIds);

    /**
     * 用户任务标识获取对应的用户任务id
     *
     * @param userTaskKey 用户任务标识
     * @return 对应的用户任务id
     */
    Long selectFirstUserTaskIdByTaskUserKey(String userTaskKey);

    Long selectFirstProcinstIdByTaskUserKey(String userTaskKey);


    List<String> selectAssigneeIdByTaskId(@Param("taskId") Long taskId);

    HiRuTaskUser selectfirstByTaskUserKey(String userTaskKey);

    List<HiRuTaskUser> selectByTaskIdIn(@Param("taskIdCollection") Collection<Long> taskIdCollection);

    int updateCommentByTaskUserKey(@Param("updatedComment") String updatedComment,
                                   @Param("userTaskKey") String userTaskKey);

    List<HiRuTaskUser> selectByProcinstIdAndAssigneeId(Long procinstId, String assigneeId);

    List<HiRuTaskUser> selectByUserTaskIdIn(Collection<Long> userTaskIds);

    Long countByTaskIdAndStatus(@Param("taskId") Long taskId, @Param("status") Integer status);

    List<AssigneeOrgAndDept> selectByTaskIdInAndStatus(Collection<Long> taskId, @Param("status") Integer status);

    List<HiRuTaskUser> selectByTaskUserKey(String userTaskKey);

    List<Long> selectUserTaskIdByPid(Long procinstId, Long pid);

}







