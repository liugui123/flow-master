package org.lg.engine.core.cmd.inter;

import org.lg.engine.core.cmd.Command;

public class TransactionCommandInterceptor extends AbstractCommandInterceptor {

    @Override
    public <T> T execute(Command<T> command) {
        return next.execute(command);
    }
}
