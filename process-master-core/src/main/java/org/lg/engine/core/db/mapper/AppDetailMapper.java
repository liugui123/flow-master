package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.db.model.AppDetail;

import java.util.Collection;
import java.util.List;

public interface AppDetailMapper {
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
    int insert(AppDetail record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(AppDetail record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    AppDetail selectByPrimaryKey(Long id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(AppDetail record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(AppDetail record);

    int batchInsert(@Param("list") List<AppDetail> list);

    List<AppDetail> selectByFlagsAndAppIdAndStatusAndDetailNameLike(@Param("flags") Integer flags,
                                                                    @Param("appId") Long appId,
                                                                    @Param("status") Integer status,
                                                                    @Param("detailName") String detailName);

    List<AppDetail> selectAllByPid(@Param("pid") Long pid);

    int updateFlagsByDetailKey(@Param("updatedFlags") Integer updatedFlags, @Param("detailKey") String detailKey);

    int updateStatusByDetailKey(@Param("updatedStatus") Integer updatedStatus, @Param("detailKey") String detailKey);

    AppDetail selectFirstByDetailKey(@Param("detailKey") String detailKey);

    AppDetail selectFirstByAppDetailKey(@Param("appDetailKey") String appDetailKey);

    Long selectFirstIdByDetailKey(@Param("detailKey") String detailKey);

    int updateDetailStatusById(@Param("updateStatus") Integer status, @Param("id") Long id);

    List<AppDetail> selectByAppId(@Param("appId") Long appId);

    List<AppDetail> selectByAppIdAndPidByDetailName(@Param("appId") Long appId, @Param("likeDetailName") String likeDetailName);

    List<AppDetail> selectByAppIdAndDetailName(@Param("appId") Long appId, @Param("detailName") String detailName);

    List<AppDetail> selectByAppIdAndDetailNameIn(@Param("appId") Long appId, @Param("detailNameCollection") Collection<String> detailNameCollection);

    int updateSortScore(@Param("id") Long id, @Param("sortScore") Integer sortScore,
                        @Param("gmtModified") Long gmtModified);

    List<AppDetail> selectByAppIdAndPid(@Param("appId") Long appId, @Param("pid") Long pid);

    int updatePidById(@Param("id") Long id, @Param("appId") Long appId, @Param("pid") Long pid, @Param("gmtModified") Long gmtModified);

    int updateIconUrlById(@Param("updatedIconUrl") String updatedIconUrl, @Param("id") Long id);

    /**
     * 更新名称
     */
    int updateDetailNameByDetailKey(@Param("updatedDetailName") String updatedDetailName, @Param("detailKey") String detailKey);

    /**
     *
     */
    Long selectIdByAppDetailKey(@Param("appDetailKey") String appDetailKey);
}