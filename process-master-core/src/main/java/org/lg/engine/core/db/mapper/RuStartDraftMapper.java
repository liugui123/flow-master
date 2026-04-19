package org.lg.engine.core.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.lg.engine.core.client.model.request.MyStartDraftRequest;
import org.lg.engine.core.db.model.RuStartDraft;

import java.util.List;

public interface RuStartDraftMapper {
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
    int insert(RuStartDraft record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    RuStartDraft selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(RuStartDraft record);

    int batchInsert(@Param("list") List<RuStartDraft> list);

    /**
     * 分页
     */
    List<RuStartDraft> page(MyStartDraftRequest request);

    /**
     * 分页计数
     */
    Integer pageCount(MyStartDraftRequest request);

    /**
     * 按照数据源key查找
     */
    RuStartDraft selectFirstByProcFormDataKey(@Param("procFormDataKey") String procFormDataKey);

    /**
     * 按照数据源key删除
     */
    int deleteByProcFormDataKey(@Param("procFormDataKey") String procFormDataKey);
}