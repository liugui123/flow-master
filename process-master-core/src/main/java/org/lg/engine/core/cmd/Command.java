package org.lg.engine.core.cmd;

import org.lg.engine.core.context.CommandApplication;

public interface Command<T> {


    T execute(CommandApplication commandApplication);

}
