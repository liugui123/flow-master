package org.lg.engine.core.cmd.inter;

import com.alibaba.fastjson.JSON;
import org.lg.engine.core.cmd.Command;
import org.lg.engine.core.utils.Logs;

public class LogCommandInterceptor extends AbstractCommandInterceptor {


    @Override
    public <T> T execute(Command<T> command) {
//        if (!Logs.isDebugEnabled()) {
//            return next.execute(command);
//        }
        try {
            return next.execute(command);
        } finally {
            Logs.info("flow cmd finished:{}", JSON.toJSONString(command));
        }

    }
}
