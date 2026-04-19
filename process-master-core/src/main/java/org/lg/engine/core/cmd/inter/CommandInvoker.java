package org.lg.engine.core.cmd.inter;

import org.lg.engine.core.client.utils.WfConstant;
import org.lg.engine.core.cmd.Command;
import org.lg.engine.core.context.CommandApplicationFactory;
import org.lg.engine.core.utils.Logs;

public class CommandInvoker extends AbstractCommandInterceptor {


    @Override
    public <T> T execute(Command<T> command) {
        try {
            //todo 这里可以做一些前置检查，比如人员权限等
            return command.execute(CommandApplicationFactory.get());
        } catch (Throwable throwable) {
            Logs.error(WfConstant.WF_LOG_PREFIX + "流程命令执行出错", throwable);
            throw throwable;
        }
    }
}
