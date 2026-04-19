package org.lg.engine.core.execution;

import org.lg.engine.core.cmd.Command;
import org.lg.engine.core.cmd.inter.CommandInterceptor;


public class CommandExecutorImpl implements CommandExecutor {

    protected CommandInterceptor first;

    public CommandExecutorImpl(CommandInterceptor first) {
        this.first = first;
    }

    @Override
    public <T> T execute(Command<T> command) {
        return first.execute(command);
    }
}
