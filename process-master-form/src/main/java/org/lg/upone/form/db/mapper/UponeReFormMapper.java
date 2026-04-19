package org.lg.upone.form.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lg.upone.form.db.model.UponeReForm;
import org.lg.upone.form.model.req.PageFormReq;

@Mapper
public interface UponeReFormMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UponeReForm record);

    int insertSelective(UponeReForm record);

    UponeReForm selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UponeReForm record);

    int updateByPrimaryKey(UponeReForm record);

    int batchInsert(@Param("list") List<UponeReForm> list);

    Integer selectMaxFormVersionByFormKey(@Param("formKey") String formKey);

    /**
     * 分页查询
     * 按照key分组查询版本最大的那个
     */
    List<UponeReForm> page(PageFormReq request);

    Integer pageCount(PageFormReq request);

    /**
     * key查询草稿详情
     */
    UponeReForm selectFirstByFormKeyMax(@Param("formKey") String formKey);
}