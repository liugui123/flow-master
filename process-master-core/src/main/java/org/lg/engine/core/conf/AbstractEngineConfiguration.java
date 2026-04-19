package org.lg.engine.core.conf;

import org.lg.engine.core.cmd.inter.*;
import org.lg.engine.core.context.CommandApplicationFactory;
import org.lg.engine.core.execution.CommandExecutor;
import org.lg.engine.core.execution.CommandExecutorImpl;
import org.lg.engine.core.listener.dispatcher.WfEventDispatcher;
import org.lg.engine.core.listener.dispatcher.WfEventDispatcherImpl;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEngineConfiguration {


    protected List<CommandInterceptor> commandInterceptors;

    protected CommandExecutor commandExecutor;

    protected CommandApplicationFactory commandApplicationFactory;

    protected WfEventDispatcher eventDispatcher;


    public void init() {
        initFactory();
        initCommandInterceptors();
        initCommandExecutors();
        initWfEventDispatcher();
    }


    protected void initWfEventDispatcher() {
        this.eventDispatcher = new WfEventDispatcherImpl();
    }

    public void initFactory() {
        if (commandApplicationFactory == null) {
            commandApplicationFactory = new CommandApplicationFactory();
        }
    }


    public void initCommandExecutors() {
        if (commandExecutor == null) {
            CommandInterceptor first = initInterceptorChain(commandInterceptors);
            commandExecutor = new CommandExecutorImpl(first);
        }
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public void initCommandInterceptors() {
        if (commandInterceptors == null) {

            commandInterceptors = new ArrayList<>();
            commandInterceptors.add(new LogCommandInterceptor());
            commandInterceptors.add(new TransactionCommandInterceptor());
            if (commandApplicationFactory != null) {
                ContextCommandInterceptor contextCommandInterceptor = new ContextCommandInterceptor(commandApplicationFactory);
                commandInterceptors.add(contextCommandInterceptor);
            }
            commandInterceptors.add(new CommandInvoker());

        }
    }


    public CommandInterceptor initInterceptorChain(List<CommandInterceptor> chain) {
        if (chain == null || chain.isEmpty()) {
            throw new IllegalArgumentException("invalid command interceptor chain configuration: " + chain);
        }
        for (int i = 0; i < chain.size() - 1; i++) {
            chain.get(i).setNext(chain.get(i + 1));
        }
        return chain.get(0);
    }

    public WfEventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }
}
