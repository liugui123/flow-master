package org.lg.upone.form.service;

import org.lg.engine.core.client.utils.Page;
import org.lg.upone.form.model.req.*;
import org.lg.upone.form.model.res.*;

/**
 * 表单服务
 */
public interface UponeFormService {

    Page<DraftFormRes> draft(PageFormReq request);

    Page<PublishFormRes> publish(PageFormReq request);

    EditFormRes edit(EditFormReq request);

    DetailFormRes detail(DetailFormReq request);

    void del(DetailFormReq request);

    EditRuFormRes ruFormEdit(EditRuFormReq request);

    DetailRuFormRes ruFormDetail(DetailRuFormReq request);

    void copy(ReFormCopyReq request);
}
