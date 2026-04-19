package org.lg.engine.core.conf;


import lombok.Getter;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandApplicationFactory;
import org.lg.engine.core.db.service.*;
import org.lg.engine.core.service.CommonService;
import org.lg.engine.core.service.DeProcDefinitionService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EngineConfigurationForSpring extends AbstractEngineConfiguration implements
        ApplicationListener<ContextRefreshedEvent> {

    protected RuTaskService taskService;

    protected RuActinstService actinstService;

    protected RuActinstSiteService actinstSiteService;

    protected RuTaskUserService taskUserService;

    protected RuTaskOperateLogService taskOperateLogService;

    protected RuProcinstService procinstService;

    protected HiRuProcinstService hiRuProcinstService;
    protected HiRuTaskService hiRuTaskService;

    protected HiRuTaskUserService hiRuTaskUserService;

    protected DeProcessService deProcessService;

    protected ReProcessService reProcessService;

    protected DeProcDefinitionService deProcDefinitionService;

    protected ReFormModelService reFormModelService;

    protected RuStartDraftService ruStartDraftService;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        super.init();
        ApplicationContext applicationContext = event.getApplicationContext();
        this.taskService = applicationContext.getBean(RuTaskService.class);
        this.actinstService = applicationContext.getBean(RuActinstService.class);
        this.actinstSiteService = applicationContext.getBean(RuActinstSiteService.class);
        this.taskUserService = applicationContext.getBean(RuTaskUserService.class);
        this.procinstService = applicationContext.getBean(RuProcinstService.class);
        this.hiRuProcinstService = applicationContext.getBean(HiRuProcinstService.class);
        this.hiRuTaskService = applicationContext.getBean(HiRuTaskService.class);
        this.hiRuTaskUserService = applicationContext.getBean(HiRuTaskUserService.class);
        this.deProcessService = applicationContext.getBean(DeProcessService.class);
        this.deProcDefinitionService = applicationContext.getBean(DeProcDefinitionService.class);
        this.reProcessService = applicationContext.getBean(ReProcessService.class);
        this.taskOperateLogService = applicationContext.getBean(RuTaskOperateLogService.class);
        this.ruStartDraftService = applicationContext.getBean(RuStartDraftService.class);
        this.reFormModelService = applicationContext.getBean(ReFormModelService.class);
        for (CommonService value : applicationContext.getBeansOfType(CommonService.class).values()) {
            value.setCommandExecutor(getCommandExecutor());
        }
        CommandApplication commandApplication = CommandApplicationFactory.get();
        commandApplication.setEngineConfiguration(this);
    }

}
