package org.lg.engine.core.cmd.impl;

import com.alibaba.fastjson2.JSON;
import org.lg.engine.core.client.enumerate.SearchModelEnum;
import org.lg.engine.core.client.enumerate.UserTaskStatusEnum;
import org.lg.engine.core.client.model.ChildShape;
import org.lg.engine.core.client.model.ChildShapes;
import org.lg.engine.core.client.model.request.RuprocinstUpdateRequest;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.context.CommandApplication;
import org.lg.engine.core.context.CommandApplications;
import org.lg.engine.core.context.CommandContextFactory;
import org.lg.engine.core.db.model.RuActinst;
import org.lg.engine.core.db.model.RuProcinst;
import org.lg.engine.core.service.manager.ProcConfHelper;
import org.lg.engine.core.service.manager.RunTimeManager;
import org.lg.engine.core.utils.Logs;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 更新流程实例
 * 暂时支持更新位运行节点的修改
 */
public class RuProcinstUpdateCmd extends PressUserTaskCmd<Void> {
    private final RuprocinstUpdateRequest request;

    public RuProcinstUpdateCmd(RuprocinstUpdateRequest request) {
        super(request.getOperator(), request.getUserTaskLevel(), request.getVarJson());
        this.request = request;
    }


    @Override
    public Void execute(CommandApplication commandApplication) {
        //支持待运行节点全操作
        //有删除节点动作时，需要把已经生成的节点实例也删掉

        //更新流程实例
        RuProcinst ruProcinst = updateProcinst();
        CommandContextFactory.getCommandContext().setRuProcinst(ruProcinst);

        //更新节点和位置
        Long procinstId = ruProcinst.getId();

        //查询数据库中节点
        List<RuActinst> ruActinsts = CommandApplications.getActinstService().selectByProcInstId(procinstId);
        //找出数据库中id和key映射
        Map<String, Long> actIdAndKey = ruActinsts
                .stream()
                .collect(Collectors.toMap(RuActinst::getActinstKey, RuActinst::getId));

        //新的节点配置
        ChildShapes newChildShapes = ProcConfHelper.checkAndGetChildShapes(request.getProcJson());
        //删除现有的节点和位置
        CommandApplications.getActinstService().deleteByProcinstId(procinstId);
        CommandApplications.getActinstSiteService().deleteByProcinstId(procinstId);

        List<ChildShape> shapes = newChildShapes.getChildShapes();

        //重新插入节点
        List<RuActinst> newRuActinsts = RunTimeManager.initActinstsAndBack(
                shapes,
                actIdAndKey);
        //流程从这个节点开始，一个流程实例中只能有一个跳跃节点
        String jumpResourceId = request.getJumpResourceId();
        if (jumpResourceId != null) {
            Map<String, Long> newActIdAndKey = newRuActinsts
                    .stream()
                    .collect(Collectors.toMap(RuActinst::getActinstKey, RuActinst::getId));
            Long jumpActinstId = newActIdAndKey.get(jumpResourceId);
            if (jumpActinstId != null) {
                //把流程下正在运行的任务和用户任务结束掉
                RunTimeManager.deleteTaskByProcInstId(
                        procinstId,
                        "节点跳跃",
                        UserTaskStatusEnum.AUTO_COMPLETE);
                //跳跃节点以上标记完成
                List<Long> upActinstIds = RunTimeManager.findActIdBySearchModel(
                        jumpActinstId,
                        procinstId,
                        SearchModelEnum.UP,
                        null);
                if (Utils.isNotEmpty(upActinstIds)) {
                    RunTimeManager.signActStatusFinished(upActinstIds);
                }


                //跳跃节点以下标记待处理
                List<Long> downActinstIds = RunTimeManager.findActIdBySearchModel(
                        jumpActinstId,
                        procinstId,
                        SearchModelEnum.DOWN,
                        null);
                if (Utils.isNotEmpty(downActinstIds)) {
                    RunTimeManager.signActStatusPending(downActinstIds);
                }

                //重新执行这个节点
                RunTimeManager.runActinstAndSignEnd(procinstId, jumpActinstId, null);
            }
        }
        return null;
    }

    private RuProcinst updateProcinst() {
        RuProcinst ruProcinst = RunTimeManager.checkAndGetProcinstByKey(request.getProcKey());
        ruProcinst.setGmtModified(System.currentTimeMillis());
        ruProcinst.setProcJson(request.getProcJson());
        ruProcinst.setProcViewJson(request.getProcViewJson());
        Logs.info("更新实例ruProcinstupdate之前:ID:{},入参{}", request.getProcKey(), JSON.toJSONString(request));
        CommandApplications.getProcinstService().updateByPrimaryKey(ruProcinst);
        Logs.info("更新实例ruProcinstupdate之后:ID:{},入参{}", request.getProcKey(), JSON.toJSONString(RunTimeManager.checkAndGetRuProcinst(ruProcinst.getId(), "RuProcinstUpdateCmd")));
        return ruProcinst;
    }
}
