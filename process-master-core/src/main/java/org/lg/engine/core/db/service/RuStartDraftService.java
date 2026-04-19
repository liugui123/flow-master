package org.lg.engine.core.db.service;

import org.lg.engine.core.db.model.RuStartDraft;

public interface RuStartDraftService {

    void edit(RuStartDraft ruStartDraft);

    void deleteByProcFormDataKey(String procFormDataKey);
}
