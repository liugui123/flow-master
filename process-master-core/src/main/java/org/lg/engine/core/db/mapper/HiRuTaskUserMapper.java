package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.AssigneeOrgAndDept;
import org.lg.engine.core.db.model.HiRuTaskUser;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface HiRuTaskUserMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(HiRuTaskUser record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    HiRuTaskUser selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(HiRuTaskUser record);

    int updateBatch(List<HiRuTaskUser> list);

    int batchInsert(@Param("list") List<HiRuTaskUser> list);

    Long selectFirstTaskIdById(@Param("id") Long id);


    Long selectFirstTaskIdByUserTaskKey(@Param("userTaskKey") String userTaskKey);

    List<HiRuTaskUser> selectByTaskIdOrderByIdAsc(@Param("taskId") Long taskId);

    List<HiRuTaskUser> selectByTaskIdWithFiltersOrderByIdAsc(@Param("taskId") Long taskId, @Param("assigneeIds") List<String> assigneeIds, @Param("commentNotBlank") Boolean commentNotBlank);

    Long countIdByTaskId(@Param("taskId") Long taskId);

    List<HiRuTaskUser> selectByAssigneeIdOrderByGmtCreateDesc(@Param("assigneeId") String assigneeId);

    List<HiRuTaskUser> selectFinishedByProcInstId(@Param("procinstId") Long procinstId, @Param("status") Integer status);

    List<HiRuTaskUser> selectByTaskId(@Param("taskId") Long taskId);

    List<HiRuTaskUser> selectByAssigneeIdAndHasFlags(@Param("assigneeId") String assigneeId, @Param("flags") Integer flags);

    List<HiRuTaskUser> selectByProcinstIdIn(@Param("procinstIdCollection") Collection<Long> procinstIdCollection);

    HiRuTaskUser selectByAssigneeIdAndTaskId(@Param("assigneeId") String assigneeId, @Param("taskId") Long taskId);

    HiRuTaskUser selectFirstByUserTaskId(@Param("userTaskId") Long userTaskId);

    Long selectFirstTaskIdByUserTaskId(@Param("userTaskId") Long userTaskId);

    List<HiRuTaskUser> selectByTaskIdAndStatusIn(@Param("taskId") Long taskId,
                                                 @Param("statusCollection") Collection<Integer> statusCollection);

    HiRuTaskUser selectFirstByProcinstIdAndAssigneeIdAndAssigneeOrgId(@Param("procinstId") Long procinstId, @Param("assigneeId") String assigneeId, @Param("assigneeOrgId") String assigneeOrgId);

    List<HiRuTaskUser> pageHiUserTasks(@Param("operatorId") String operatorId,
                                       @Param("operatorOrgId") String operatorOrgId,
                                       @Param("startRow") int startRow,
                                       @Param("pageSize") Integer pageSize,
                                       @Param("procinstIds") Set<Long> procinstIds,
                                       @Param("taskIds") Set<Long> taskIds,
                                       @Param("flags") Integer flags,
                                       @Param("minGmtCreate") Long minGmtCreate,
                                       @Param("maxGmtCreate") Long maxGmtCreate
    );

    Integer countPageHiUserTasks(@Param("operatorId") String operatorId,
                                 @Param("operatorOrgId") String operatorOrgId,
                                 @Param("procinstIds") Set<Long> procinstIds,
                                 @Param("taskIds") Set<Long> taskIds,
                                 @Param("flags") Integer flags,
                                 @Param("minGmtCreate") Long minGmtCreate,
                                 @Param("maxGmtCreate") Long maxGmtCreate);

    int updateStatusByUserTaskIdAndAssigneeId(@Param("updatedStatus") Integer updatedStatus, @Param("userTaskId") Long userTaskId, @Param("assigneeId") String assigneeId);

    Set<AssigneeOrgAndDept> selectAssigneeOrgAndDeptByTaskIdsAndSpecialFlags(@Param("taskIds") Set<Long> taskIds);

    /**
     * 用户任务标识获取对应的用户任务id
     *
     * @param userTaskKey 用户任务标识
     * @return 对应的用户任务id
     */
    Long selectFirstUserTaskIdByTaskUserKey(@Param("userTaskKey") String userTaskKey);

    Long selectFirstProcinstIdByTaskUserKey(@Param("userTaskKey") String userTaskKey);


    HiRuTaskUser selectfirstByTaskUserKey(@Param("userTaskKey") String userTaskKey);


    List<String> selectAssigneeIdByTaskId(@Param("taskId") Long taskId);

    int updateCommentByTaskUserKey(@Param("updatedComment") String updatedComment, @Param("userTaskKey") String userTaskKey);


    List<HiRuTaskUser> selectByTaskIdIn(@Param("taskIdCollection") Collection<Long> taskIdCollection);

    List<HiRuTaskUser> selectByProcinstIdAndAssigneeId(@Param("procinstId") Long procinstId, @Param("assigneeId") String assigneeId);

    List<HiRuTaskUser> selectByUserTaskIdIn(@Param("userTaskIds") Collection<Long> userTaskIds);

    Long countByTaskIdAndStatus(@Param("taskId") Long taskId, @Param("status") Integer status);

    List<AssigneeOrgAndDept> selectByTaskIdInAndStatus(@Param("taskIdCollection") Collection<Long> taskIdCollection, @Param("status") Integer status);

    List<HiRuTaskUser> selectByTaskUserKey(@Param("userTaskKey") String userTaskKey);

    List<HiRuTaskUser> selectByProcinstIdAndFlagsInAndFlagsNotIn(@Param("procinstId") Long procinstId, @Param("flags") List<Integer> flags, @Param("notFlags") List<Integer> notFlags);

    List<Long> selectUserTaskIdByPid(@Param("procinstId") Long procinstId, @Param("pid") Long pid);
}