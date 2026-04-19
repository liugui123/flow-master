package org.lg.engine.core.cmd.impl;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.cmd.AbstractCommand;

@Getter
@Setter
public abstract class PressUserTaskCmd<T> extends AbstractCommand<T> {

    protected final Integer userTaskLevel;

    protected String varJson;

    protected PressUserTaskCmd(Operator user, Integer userTaskLevel, String varJson) {
        super(user);
        this.userTaskLevel = userTaskLevel;
        this.varJson = varJson;
    }

}
