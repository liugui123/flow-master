package org.lg.engine.core.service;

import org.lg.engine.core.execution.CommandExecutor;

public abstract class AbstractService implements CommonService {

    protected CommandExecutor commandExecutor = null;

    @Override
    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }
}
