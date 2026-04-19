package org.lg.engine.core.context;

import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.utils.Assert;

public class CommandContextFactory {

    protected static ThreadLocal<CommandContext> commandContextThreadLocal = new ThreadLocal<>();


    public static CommandContext getCommandContext() {
        CommandContext commandContext = commandContextThreadLocal.get();
        Assert.isTrue(commandContext != null,
                WfExceptionCode.COMMAND_CONTEXT_BLANK.getMsg(),
                WfExceptionCode.COMMAND_CONTEXT_BLANK.getCode(),
                WfExceptionCode.COMMAND_CONTEXT_BLANK.getMsg());
        return commandContext;
    }

    public static CommandContext getCommandContextNull() {
        return commandContextThreadLocal.get();
    }

    public static void setCommandContext(CommandContext CommandContext) {
        commandContextThreadLocal.set(CommandContext);
    }

    public static void removeCommandContext() {
        commandContextThreadLocal.remove();
    }
}
