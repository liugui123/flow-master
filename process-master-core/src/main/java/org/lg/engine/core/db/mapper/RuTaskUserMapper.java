package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.AssigneeOrgAndDept;
import org.lg.engine.core.db.model.RuTaskUser;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RuTaskUserMapper {

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
    int insert(RuTaskUser record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    RuTaskUser selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(RuTaskUser record);

    int updateBatch(List<RuTaskUser> list);

    int batchInsert(@Param("list") List<RuTaskUser> list);

    List<Long> selectTaskIdByUserId(@Param("userId") Long userId);

    int deleteByUserIdAndTaskId(@Param("userId") String userId, @Param("taskId") Long taskId);

    RuTaskUser selectFirstByAssigneeIdAndTaskId(@Param("assigneeId") String assigneeId, @Param("taskId") Long taskId);

    List<Long> selectIdByAssigneeIdAndTaskId(@Param("assigneeId") String assigneeId, @Param("taskId") Long taskId);

    List<Long> selectIdByParentTaskUserId(@Param("pid") Long pid);

    int deleteByProcInstId(@Param("procinstId") Long procinstId);

    List<RuTaskUser> selectByProcInstId(@Param("procinstId") Long procinstId);

    List<RuTaskUser> selectByProcInstIdAndTaskId(@Param("procinstId") Long procinstId, @Param("taskId") Long taskId);

    Integer upTaskUserFlags(@Param("id") Long id, @Param("status") Integer status);

    List<RuTaskUser> findListByTaskId(@Param("taskIdList") List<Long> taskIdList);

    Long countIdByTaskId(@Param("taskId") Long taskId);

    Long countByPid(@Param("pid") Long pid);

    int deleteByTaskId(@Param("taskId") Long taskId);

    List<RuTaskUser> selectByTaskId(@Param("taskId") Long taskId);

    Set<String> selectAssigneeIdByTaskId(@Param("taskId") Long taskId);

    int updateStatusByTaskIdAndAssigneeId(@Param("updatedStatus") Integer updatedStatus, @Param("taskId") Long taskId,
                                          @Param("assigneeId") String assigneeId);

    List<RuTaskUser> selectByAssigneeIdAndPidIsNullAndStatusOrderByGmtCreateDesc(@Param("assigneeId") String assigneeId,
                                                                                 @Param("status") Integer status);

    List<String> selectAssigneeNameByTaskIdIn(@Param("taskIdCollection") Collection<Long> taskIdCollection);

    List<Long> selectIdByTaskId(@Param("taskId") Long taskId);

    int updatePidById(@Param("updatedPid") Long updatedPid, @Param("id") Long id);

    List<String> selectAssigneeNameByTaskId(@Param("taskId") Long taskId);

    List<RuTaskUser> selectByAssigneeIdAndHasFlags(@Param("assigneeId") String assigneeId,
                                                   @Param("flags") Integer flags);

    List<RuTaskUser> selectByProcinstIdIn(@Param("procinstIdCollection") Collection<Long> procinstIdCollection);

    List<RuTaskUser> selectByTaskIdOrderByIdAsc(@Param("taskId") Long taskId);

    RuTaskUser selectByAssigneeIdAndTaskId(@Param("assigneeId") String assigneeId, @Param("taskId") Long taskId);

    Long selectFirstTaskIdById(@Param("id") Long id);

    Long selectFirstTaskIdByUserTaskKey(@Param("userTaskKey") String userTaskKey);

    List<RuTaskUser> selectByPid(@Param("pid") Long pid);

    List<Long> selectIdByPid(@Param("pid") Long pid);

    int deleteByIdIn(@Param("idCollection") Collection<Long> idCollection);

    List<RuTaskUser> selectByUserTaskLevelNotNull(@Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize);

    List<RuTaskUser> selectByTaskIdWithFiltersOrderByIdAsc(@Param("taskId") Long taskId,
                                                           @Param("assigneeIds") List<String> assigneeIds, @Param("commentNotBlank") Boolean commentNotBlank);

    List<RuTaskUser> pagePendingUserTasks(@Param("assigneeId") String assigneeId,
                                          @Param("assigneeOrgId") String assigneeOrgId,
                                          @Param("startRow") int startRow,
                                          @Param("pageSize") int pageSize,
                                          @Param("procinstIds") Set<Long> procinstIds,
                                          @Param("taskIds") Set<Long> taskIds,
                                          @Param("minGmtCreate") Long minGmtCreate,
                                          @Param("maxGmtCreate") Long maxGmtCreate
    );

    Integer countPagePendingUserTasks(@Param("assigneeId") String assigneeId,
                                      @Param("assigneeOrgId") String assigneeOrgId,
                                      @Param("procinstIds") Set<Long> procinstIds,
                                      @Param("taskIds") Set<Long> taskIds,
                                      @Param("minGmtCreate") Long minGmtCreate,
                                      @Param("maxGmtCreate") Long maxGmtCreate);

    Set<AssigneeOrgAndDept> selectAssigneeOrgAndDeptByTaskIdsAndSpecialFlags(@Param("taskIds") Set<Long> taskIds);

    /**
     * 查找用户任务标识对应的主键
     *
     * @param userTaskKey 用户任务标识
     * @return id
     */
    Long selectFirstIdByTaskUserKey(@Param("userTaskKey") String userTaskKey);

    Long selectFirstProcinstIdByTaskUserKey(@Param("userTaskKey") String userTaskKey);


    RuTaskUser selectfirstByTaskUserKey(@Param("userTaskKey") String userTaskKey);

    List<RuTaskUser> selectByTaskIdIn(@Param("taskIdCollection") Collection<Long> taskIdCollection);

    List<RuTaskUser> selectByTaskIdInAndStatus(@Param("taskIdCollection") Collection<Long> taskIdCollection, @Param("status") Integer status);


    Long countByTaskIdInAndStatusNot(@Param("taskIdCollection") Collection<Long> taskIdCollection,
                                     @Param("notStatus") Integer notStatus);

    List<RuTaskUser> selectByTaskIdsAndStatusAndUserTaskLevelWithFlags(@Param("taskIds") List<Long> taskIds,
                                                                       @Param("status") Integer status, @Param("flagList") List<Integer> flagList,
                                                                       @Param("notFlagList") List<Integer> notFlagList, @Param("userTaskLevel") Integer userTaskLevel);

    List<RuTaskUser> selectByIdInAndStatus(@Param("ids") List<Long> ids, @Param("status") Integer status);

    List<RuTaskUser> selectByTaskIdInWithLimit(@Param("taskIdCollection") Collection<Long> taskIdCollection, @Param("limitValue") Integer limitValue);

    List<RuTaskUser> selectByTaskIdsAndAssigneeIds(@Param("taskIds") Collection<Long> taskIds, @Param("assigneeIds") Collection<String> assigneeIds);

    List<RuTaskUser> selectByProcinstIdAndAssigneeId(@Param("procinstId") Long procinstId, @Param("assigneeId") String assigneeId);


}