package org.lg.engine.core.db.service.impl;

import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.RuStartDraftMapper;
import org.lg.engine.core.db.model.RuStartDraft;
import org.lg.engine.core.db.service.RuStartDraftService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RuStartDraftServiceImpl implements RuStartDraftService {

    @Resource
    private RuStartDraftMapper ruStartDraftMapper;

    @Override
    public void edit(RuStartDraft ruStartDraft) {

        String formDataKey = ruStartDraft.getProcFormDataKey();

        Assert.isTrue(
                Utils.isNotEmpty(formDataKey),
                WfExceptionCode.SAVE_START_DRAFT_DATA_KEY_EMPTY.msg,
                WfExceptionCode.SAVE_START_DRAFT_DATA_KEY_EMPTY.code
        );

        RuStartDraft dbRuStartDraft = null;
        if (Utils.isNotEmpty(formDataKey)) {
            dbRuStartDraft = ruStartDraftMapper.selectFirstByProcFormDataKey(formDataKey);
        }

        if (dbRuStartDraft == null) {
            //保存草稿
            ruStartDraft.setStatus(0);
            ruStartDraft.setFlags(0);
            ruStartDraft.setGmtCreate(System.currentTimeMillis());
            ruStartDraft.setGmtModified(System.currentTimeMillis());
            ruStartDraftMapper.insert(ruStartDraft);
        } else {
            //更新草稿
            dbRuStartDraft.setDraftTitle(ruStartDraft.getDraftTitle());
            dbRuStartDraft.setGmtModified(System.currentTimeMillis());
            ruStartDraftMapper.updateByPrimaryKey(dbRuStartDraft);
        }
    }

    @Override
    public void deleteByProcFormDataKey(String procFormDataKey) {
        if (Utils.isEmpty(procFormDataKey)) {
            return;
        }
        ruStartDraftMapper.deleteByProcFormDataKey(procFormDataKey);
    }
}
