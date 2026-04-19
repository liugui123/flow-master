package org.lg.engine.portal;

import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.AppGroupDetailPcResponse;
import org.lg.engine.core.client.model.response.StartProcessResponse;
import org.lg.engine.portal.model.dto.*;
import org.lg.engine.portal.model.query.AppManagePcQuery;
import org.lg.engine.portal.model.vo.AppDetailTreeAppVO;
import org.lg.engine.portal.model.vo.AppDetailTreePcVO;
import org.lg.engine.portal.model.vo.StartProcessVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 通用转换对象
 *
 * @author liugui
 * @date 2021/03/04 10:44
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface Convert {

    /**
     * 关于下面taskId和userTaskId的说明： 在流程流转(WfService)部分，前端传参过来的taskId就是后端的userTaskId,需要和后端的taskId区分
     */
    Convert INSTANCE = Mappers.getMapper(Convert.class);


}
