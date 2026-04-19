package org.lg.upone.form.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lg.upone.form.db.model.UponeDeForm;
import org.lg.upone.form.model.req.PageFormReq;

@Mapper
public interface UponeDeFormMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UponeDeForm record);

    int insertSelective(UponeDeForm record);

    UponeDeForm selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UponeDeForm record);

    int updateByPrimaryKey(UponeDeForm record);

    int batchInsert(@Param("list") List<UponeDeForm> list);

    /**
     * key查询草稿详情
     */
    UponeDeForm selectFirstByFormKey(@Param("formKey") String formKey);

    /**
     * 分页查询
     */
    List<UponeDeForm> page(PageFormReq request);

    Integer pageCount(PageFormReq request);

    /**
     * 删除详情
     */
    int deleteByFormKey(@Param("formKey") String formKey);
}