package org.lg.engine.core.context;

import org.lg.engine.core.conf.AbstractEngineConfiguration;


public class CommandApplication {


    private CommandApplication() {
    }


    private volatile static CommandApplication commandApplication;


    public static CommandApplication get() {
        if (commandApplication == null) {
            synchronized (CommandApplication.class) {
                if (commandApplication == null) {
                    commandApplication = new CommandApplication();
                }
            }
        }
        return commandApplication;
    }


    protected AbstractEngineConfiguration engineConfiguration;

    public AbstractEngineConfiguration getEngineConfiguration() {
        return engineConfiguration;
    }

    public void setEngineConfiguration(AbstractEngineConfiguration engineConfiguration) {
        this.engineConfiguration = engineConfiguration;
    }
}
