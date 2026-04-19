package org.lg.engine.core.context;


import org.lg.engine.core.conf.EngineConfigurationForSpring;
import org.lg.engine.core.db.service.*;
import org.lg.engine.core.execution.CommandExecutor;
import org.lg.engine.core.listener.dispatcher.WfEventDispatcher;

public class CommandApplications {

    public static EngineConfigurationForSpring getEngineConfiguration() {
        return (EngineConfigurationForSpring) CommandApplicationFactory.get().getEngineConfiguration();
    }

    public static RuTaskService getRuTaskService() {
        return getEngineConfiguration().getTaskService();
    }

    public static RuActinstService getActinstService() {
        return getEngineConfiguration().getActinstService();
    }

    public static RuActinstSiteService getActinstSiteService() {
        return getEngineConfiguration().getActinstSiteService();
    }

    public static RuTaskUserService getTaskUserService() {
        return getEngineConfiguration().getTaskUserService();
    }

    public static RuProcinstService getProcinstService() {
        return getEngineConfiguration().getProcinstService();
    }

    public static HiRuTaskService getHiRuTaskService() {
        return getEngineConfiguration().getHiRuTaskService();
    }

    public static DeProcessService getDeProcessService() {
        return getEngineConfiguration().getDeProcessService();
    }

    public static ReProcessService getReProcessService() {
        return getEngineConfiguration().getReProcessService();
    }

    public static HiRuTaskUserService getHiRuTaskUserService() {
        return getEngineConfiguration().getHiRuTaskUserService();
    }

    public static HiRuProcinstService getHiRuProcinstService() {
        return getEngineConfiguration().getHiRuProcinstService();
    }

    public static WfEventDispatcher getEventDispatcher() {
        return getEngineConfiguration().getEventDispatcher();
    }

    public static RuTaskOperateLogService getTaskOperateLogService() {
        return getEngineConfiguration().getTaskOperateLogService();
    }

    public static CommandExecutor getCommandExecutor() {
        return getEngineConfiguration().getCommandExecutor();
    }


    public static RuStartDraftService getRuStartDraftService() {
        return getEngineConfiguration().getRuStartDraftService();
    }

    public static ReFormModelService getReFormModelService() {
        return getEngineConfiguration().getReFormModelService();
    }

}
