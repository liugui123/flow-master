package org.lg.upone.form.service.impl;

import org.lg.engine.core.client.utils.KeyHelper;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.core.client.utils.Utils;
import org.lg.upone.form.Convert;
import org.lg.upone.form.common.constant.Constant;
import org.lg.upone.form.db.mapper.UponeDeFormMapper;
import org.lg.upone.form.db.mapper.UponeReFormMapper;
import org.lg.upone.form.db.mapper.UponeRuFormMapper;
import org.lg.upone.form.db.model.UponeDeForm;
import org.lg.upone.form.db.model.UponeReForm;
import org.lg.upone.form.db.model.UponeRuForm;
import org.lg.upone.form.model.req.*;
import org.lg.upone.form.model.res.*;
import org.lg.upone.form.service.UponeFormService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UponeFormServiceImpl implements UponeFormService {

    @Resource
    private UponeDeFormMapper uponeDeFormMapper;
    @Resource
    private UponeReFormMapper uponeReFormMapper;

    @Resource
    private UponeRuFormMapper uponeRuFormMapper;

    @Override
    public Page<DraftFormRes> draft(PageFormReq request) {
        Page<DraftFormRes> res = new Page<>();
        request.setStartRow(request.getStartRow());
        request.setEndRow(request.getEndRow() + 1);
        List<UponeDeForm> uponeDeForms = uponeDeFormMapper.page(request);
        if (Utils.isEmpty(uponeDeForms)) {
            return res;
        }
        List<DraftFormRes> resList = Convert.INSTANCE.uponeDeFormsToRes(uponeDeForms);
        res.setRows(resList);
        res.setTotal(uponeDeFormMapper.pageCount(request));
        return res;
    }

    @Override
    public Page<PublishFormRes> publish(PageFormReq request) {
        Page<PublishFormRes> res = new Page<>();
        request.setStartRow(request.getStartRow());
        request.setEndRow(request.getEndRow() + 1);
        List<UponeReForm> uponeReForms = uponeReFormMapper.page(request);
        if (Utils.isEmpty(uponeReForms)) {
            return res;
        }
        List<PublishFormRes> resList = Convert.INSTANCE.uponeReFormsToRes(uponeReForms);
        res.setRows(resList);
        res.setTotal(uponeReFormMapper.pageCount(request));
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EditFormRes edit(EditFormReq request) {
        UponeDeForm uponeDeForm = Convert.INSTANCE.editFormReqToUponeDeForm(request);

        UponeDeForm dbUponeDeForm = uponeDeFormMapper.selectFirstByFormKey(request.getFormKey());
        if (dbUponeDeForm == null) {
            //保存草稿
            uponeDeForm.setFormKey(KeyHelper.randomKey(Constant.FORM_KEY_PREFIX));
            uponeDeForm.setStatus(0);
            uponeDeForm.setFlags(0);
            uponeDeForm.setGmtCreate(System.currentTimeMillis());
            uponeDeForm.setGmtModified(System.currentTimeMillis());
            uponeDeFormMapper.insert(uponeDeForm);

            if (request.isPublish()) {
                //发布
                UponeReForm reForm = Convert.INSTANCE.uponeDeFormToRe(uponeDeForm);
                reForm.setFormDeId(uponeDeForm.getId());
                reForm.setFormVersion(0);
                reForm.setStatus(0);
                uponeReFormMapper.insert(reForm);

                return Convert.INSTANCE.uponeDeFormToEditRes(uponeDeForm);
            }
        } else {
            //更新草稿
            dbUponeDeForm.setFormJson(request.getFormJson());
            dbUponeDeForm.setFormName(request.getFormName());
            dbUponeDeForm.setGmtModified(System.currentTimeMillis());
            dbUponeDeForm.setFormKey(request.getFormKey());
            uponeDeFormMapper.updateByPrimaryKey(dbUponeDeForm);

            if (request.isPublish()) {
                //发布
                UponeReForm reForm = Convert.INSTANCE.uponeDeFormToRe(dbUponeDeForm);
                reForm.setStatus(0);
                reForm.setFormDeId(dbUponeDeForm.getId());
                Integer version = uponeReFormMapper.selectMaxFormVersionByFormKey(uponeDeForm.getFormKey());
                if (version == null) {
                    version = 0;
                } else {
                    version = version + 1;
                }
                reForm.setFormVersion(version);
                uponeReFormMapper.insert(reForm);
                return Convert.INSTANCE.uponeDeFormToEditRes(uponeDeForm);
            }
        }
        return null;
    }

    @Override
    public DetailFormRes detail(DetailFormReq request) {
        String formKey = request.getFormKey();
        if (Utils.isEmpty(formKey)) {
            return null;
        }
        UponeDeForm uponeDeForm = uponeDeFormMapper.selectFirstByFormKey(request.getFormKey());
        if (uponeDeForm == null) {
            return null;
        }
        return Convert.INSTANCE.uponeDeFormsToDetailRes(uponeDeForm);
    }

    @Override
    public void del(DetailFormReq request) {
        String formKey = request.getFormKey();
        if (Utils.isEmpty(formKey)) {
            return;
        }
        uponeDeFormMapper.deleteByFormKey(formKey);
    }

    @Override
    public EditRuFormRes ruFormEdit(EditRuFormReq request) {
        UponeRuForm uponeRuForm = Convert.INSTANCE.editRuFormReqToUponeRuForm(request);

        String formDataKey = request.getFormDataKey();
        UponeRuForm dbUponeRuForm = null;
        if (Utils.isNotEmpty(formDataKey)) {
            dbUponeRuForm = uponeRuFormMapper.selectFirstByFormDataKey(formDataKey);
        }

        EditRuFormRes res = new EditRuFormRes();
        if (dbUponeRuForm == null) {
            //保存草稿
            uponeRuForm.setFormDataKey(KeyHelper.randomKey(Constant.RU_FORM_DATA_KEY_PREFIX));
            uponeRuForm.setStatus(0);
            uponeRuForm.setFlags(0);
            uponeRuForm.setGmtCreate(System.currentTimeMillis());
            uponeRuForm.setGmtModified(System.currentTimeMillis());
            uponeRuFormMapper.insert(uponeRuForm);
            res.setFormDataKey(uponeRuForm.getFormDataKey());
            return res;
        } else {
            //更新草稿
            dbUponeRuForm.setFormConfJson(request.getFormConfJson());
            dbUponeRuForm.setFormDataJson(request.getFormDataJson());
            dbUponeRuForm.setGmtModified(System.currentTimeMillis());
            dbUponeRuForm.setFormKey(request.getFormKey());
            uponeRuFormMapper.updateByPrimaryKey(dbUponeRuForm);
            res.setFormDataKey(dbUponeRuForm.getFormDataKey());
        }
        return res;
    }

    @Override
    public DetailRuFormRes ruFormDetail(DetailRuFormReq request) {
        String formDataKey = request.getFormDataKey();
        if (Utils.isEmpty(formDataKey)) {
            return null;
        }
        UponeRuForm uponeRuForm = uponeRuFormMapper.selectFirstByFormDataKey(formDataKey);
        if (uponeRuForm == null) {
            return null;
        }
        return Convert.INSTANCE.uponeReFormsToDetailRes(uponeRuForm);
    }

    @Override
    public void copy(ReFormCopyReq request) {
        UponeReForm db = uponeReFormMapper.selectFirstByFormKeyMax(request.getFormKey());
        if (db != null) {
            UponeDeForm copyOne = Convert.INSTANCE.uponeReFormToUponeDeForm(db);
            copyOne.setFormName("复制："+copyOne.getFormName());
            copyOne.setId(null);
            copyOne.setGmtModified(System.currentTimeMillis());
            copyOne.setGmtCreate(System.currentTimeMillis());
            copyOne.setFormVersion(null);
            copyOne.setFormKey(KeyHelper.randomKey(Constant.FORM_KEY_PREFIX));
            uponeDeFormMapper.insert(copyOne);
        }

    }
}
