package org.lg.upone.form.controller;

import org.lg.engine.core.client.enctypt.Decrypt;
import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.upone.form.Convert;
import org.lg.upone.form.db.model.UponeSuggestion;
import org.lg.upone.form.model.req.*;
import org.lg.upone.form.model.res.*;
import org.lg.upone.form.service.UponeSuggestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * (t_upone_de_form)表控制层
 */
@RestController
@RequestMapping("/suggestion")
public class UponeSuggestionController {

    @Resource
    private UponeSuggestionService suggestionService;

    /**
     * 草稿列表
     */
    @GetMapping(value = "/list")
    @Decrypt
    public ApiResult<List<SuggestionRes>> list() {
        List<UponeSuggestion> uponeSuggestions = suggestionService.selectAll();
        List<SuggestionRes> res = Convert.INSTANCE.uponeSuggestionsToRes(uponeSuggestions);
        return ApiResult.success(res);
    }

    /**
     * 编辑表单
     */
    @PostMapping("/edit")
    @Decrypt
    public ApiResult<Void> edit(@Valid @ModelAttribute EditSuggestionReq request) {

        UponeSuggestion record = Convert.INSTANCE.editReqToUponeSuggestion(request);
        Operator operator = request.getOperator();
        record.setOperatorId(operator.getId());
        record.setOperatorName(operator.getName());
        record.setOperatorDeptId(operator.getDeptId());
        record.setOperatorDeptName(operator.getDeptName());
        record.setOperatorOrgId(operator.getOrgId());
        record.setOperatorOrgName(operator.getOrgName());
        suggestionService.insert(record);
        return ApiResult.success();
    }

}
