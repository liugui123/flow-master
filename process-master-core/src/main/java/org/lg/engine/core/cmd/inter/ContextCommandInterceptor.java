package org.lg.engine.core.cmd.inter;

import org.lg.engine.core.client.exception.WfException;
import org.lg.engine.core.cmd.AbstractCommand;
import org.lg.engine.core.cmd.Command;
import org.lg.engine.core.cmd.impl.PressUserTaskCmd;
import org.lg.engine.core.context.CommandApplicationFactory;
import org.lg.engine.core.context.CommandContext;
import org.lg.engine.core.context.CommandContextFactory;

public class ContextCommandInterceptor extends AbstractCommandInterceptor {

    protected CommandApplicationFactory commandApplicationFactory;

    public ContextCommandInterceptor(CommandApplicationFactory commandApplicationFactory) {
        this.commandApplicationFactory = commandApplicationFactory;
    }

    @Override
    public <T> T execute(Command<T> command) {
        try {
            if (command instanceof AbstractCommand) {
                CommandContext commandContext = CommandContextFactory.getCommandContextNull();

                if (commandContext == null) {
                    commandContext = new CommandContext();
                    commandContext.setOperator(((AbstractCommand<T>) command).getOperator());
                    if (command instanceof PressUserTaskCmd) {
                        commandContext.setUserTaskLevel(((PressUserTaskCmd<T>) command).getUserTaskLevel());
                        commandContext.setVarJson(((PressUserTaskCmd<T>) command).getVarJson());
                    }
                    CommandContextFactory.setCommandContext(commandContext);
                }

                return next.execute(command);
            }
            throw new WfException("命令上下文初始化异常，命令不是合法命令");
        } finally {
            CommandContextFactory.removeCommandContext();
        }
    }
}
