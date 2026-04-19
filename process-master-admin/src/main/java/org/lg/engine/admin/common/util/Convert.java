package org.lg.engine.admin.common.util;

import org.lg.engine.admin.model.updateprocess.UpdateProcessDTO;
import org.lg.engine.admin.model.updateprocess.UpdateProcessEdgeDTO;
import org.lg.engine.admin.model.updateprocess.UpdateProcessNodeDTO;
import org.lg.engine.core.client.model.ChildShape;
import org.lg.engine.core.client.model.request.UpdateProcessRequest;
import org.mapstruct.Mapper;
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

    Convert INSTANCE = Mappers.getMapper(Convert.class);


    ChildShape updateProcessEdgeDTOToChildShape(UpdateProcessEdgeDTO edge);

    ChildShape updateProcessNodeDTOToChildShape(UpdateProcessNodeDTO node);

    UpdateProcessRequest updateProcessDtoToReq(UpdateProcessDTO dto);
}
