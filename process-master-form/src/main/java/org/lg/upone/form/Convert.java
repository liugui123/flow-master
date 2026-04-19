package org.lg.upone.form;

import org.lg.upone.form.db.model.UponeDeForm;
import org.lg.upone.form.db.model.UponeReForm;
import org.lg.upone.form.db.model.UponeRuForm;
import org.lg.upone.form.db.model.UponeSuggestion;
import org.lg.upone.form.model.req.EditFormReq;
import org.lg.upone.form.model.req.EditRuFormReq;
import org.lg.upone.form.model.req.EditSuggestionReq;
import org.lg.upone.form.model.res.*;
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

    /**
     * 关于下面taskId和userTaskId的说明： 在流程流转(WfService)部分，前端传参过来的taskId就是后端的userTaskId,需要和后端的taskId区分
     */
    Convert INSTANCE = Mappers.getMapper(Convert.class);


    UponeDeForm editFormReqToUponeDeForm(EditFormReq request);

    UponeReForm uponeDeFormToRe(UponeDeForm uponeDeForm);

    EditFormRes uponeDeFormToEditRes(UponeDeForm uponeDeForm);

    List<DraftFormRes> uponeDeFormsToRes(List<UponeDeForm> uponeDeForms);
    DraftFormRes uponeDeFormToPageRes(UponeDeForm uponeDeForm);

    DetailFormRes uponeDeFormsToDetailRes(UponeDeForm uponeDeForm);

    List<PublishFormRes> uponeReFormsToRes(List<UponeReForm> uponeReForms);
    PublishFormRes uponeReFormsToRe(UponeReForm uponeReForm);

    UponeRuForm editRuFormReqToUponeRuForm(EditRuFormReq request);

    DetailRuFormRes uponeReFormsToDetailRes(UponeRuForm uponeRuForm);

    UponeDeForm uponeReFormToUponeDeForm(UponeReForm db);

    List<SuggestionRes> uponeSuggestionsToRes(List<UponeSuggestion> uponeSuggestions);

    SuggestionRes uponeSuggestionToRes(UponeSuggestion uponeSuggestion);

    UponeSuggestion editReqToUponeSuggestion(EditSuggestionReq request);
}
