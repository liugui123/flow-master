package org.lg.engine.core.execution;

import org.lg.engine.core.cmd.Command;

public interface CommandExecutor {

    <T> T execute(Command<T> command);

}
