package org.lg.engine.core.cmd;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.Operator;

@Getter
@Setter
public abstract class AbstractCommand<T> implements Command<T> {

    protected final Operator operator;


    public AbstractCommand(Operator operator) {
        this.operator = operator;
    }


}
