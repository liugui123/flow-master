package org.lg.engine.core.cmd.inter;


import org.lg.engine.core.cmd.Command;

public interface CommandInterceptor {

    <T> T execute(Command<T> command);


    CommandInterceptor getNext();


    void setNext(CommandInterceptor next);
}
