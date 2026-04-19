package org.lg.engine.admin.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.lg.engine.admin.model.updateprocess.UpdateProcessEdgeDTO;
import org.lg.engine.admin.model.updateprocess.UpdateProcessNodeDTO;
import org.lg.engine.admin.model.updateprocess.UpdateProcessNodeShapeUserDTO;
import org.lg.engine.core.client.enumerate.ActInstTypeEnum;
import org.lg.engine.core.client.model.Assignee;
import org.lg.engine.core.client.model.ChildShape;
import org.lg.engine.core.client.model.ChildShapeOutgoing;
import org.lg.engine.core.client.model.ChildShapes;
import org.lg.engine.core.client.utils.Utils;

import java.util.*;

public class DeProcUtils {

    /**
     * 流程图标协议转换
     */
    public static String convertView(String procViewJson) {
        //连接线
        List<UpdateProcessEdgeDTO> edges = getFromEdgeViewJson(procViewJson);
        //节点
        List<UpdateProcessNodeDTO> nodes = getFromNodeViewJson(procViewJson);
        //筛选节点的目标节点
        Map<String, List<ChildShapeOutgoing>> nodeAndTarget = nodeAndTarget(edges);
        //组装childShapes
        List<ChildShape> childShapeList = new ArrayList<>(1024);

        initEdges(edges, childShapeList);
        initNodes(nodes, nodeAndTarget, childShapeList);
        ChildShapes childShapes = new ChildShapes();
        childShapes.setChildShapes(childShapeList);
        return JSON.toJSONString(childShapes);
    }

    private static List<UpdateProcessNodeDTO> getFromNodeViewJson(String viewJson) {
        JSONObject viewJsonObj = JSON.parseObject(viewJson);
        if (viewJsonObj != null) {
            Object nodes = viewJsonObj.get("nodes");
            if (nodes != null) {
                //人员需要单独处理下

                return JSON.parseArray(JSON.toJSONString(nodes), UpdateProcessNodeDTO.class);
            }
        }
        return null;
    }

    private static List<UpdateProcessEdgeDTO> getFromEdgeViewJson(String viewJson) {
        JSONObject viewJsonObj = JSON.parseObject(viewJson);
        if (viewJsonObj != null) {
            Object edges1 = viewJsonObj.get("edges");
            if (edges1 != null) {
                return JSON.parseArray(JSON.toJSONString(edges1), UpdateProcessEdgeDTO.class);
            }
        }
        return null;
    }

    /**
     * 组装线
     */
    private static void initEdges(List<UpdateProcessEdgeDTO> edges, List<ChildShape> childShapes) {
        if (Utils.isNotEmpty(edges)) {

            for (UpdateProcessEdgeDTO edge : edges) {

                ChildShape childShape = Convert.INSTANCE.updateProcessEdgeDTOToChildShape(edge);
                //线只有一个目标节点
                ChildShapeOutgoing childShapeOutgoing = new ChildShapeOutgoing();
                childShapeOutgoing.setResourceId(edge.getTargetId());
                List<ChildShapeOutgoing> outgoing = new ArrayList<>(4);
                outgoing.add(childShapeOutgoing);
                childShape.setOutgoing(outgoing);

                childShape.setActType(ActInstTypeEnum.SEQUENCE_FLOW.getType());

                childShapes.add(childShape);
            }
        }
    }

    /**
     * 组装节点
     */
    private static void initNodes(List<UpdateProcessNodeDTO> nodes,
                                  Map<String, List<ChildShapeOutgoing>> nodeAndTarget,
                                  List<ChildShape> childShapes) {
        if (Utils.isNotEmpty(nodes)) {
            for (UpdateProcessNodeDTO node : nodes) {
                ChildShape childShape = Convert.INSTANCE.updateProcessNodeDTOToChildShape(node);
                String resourceId = node.getResourceId();
                childShape.setOutgoing(nodeAndTarget.get(resourceId));
                //初始化选择的处理人
                UpdateProcessNodeShapeUserDTO assigneeInfo = node.getAssigneeInfo();
                if (assigneeInfo != null) {
                    Set<String> assigneesStr = assigneeInfo.getAssigneesStr();
                    if (Utils.isNotEmpty(assigneesStr)) {
                        List<Assignee> assignees = new ArrayList<>(16);
                        for (String s : assigneesStr) {
                            assignees.add(JSON.parseObject(s, Assignee.class));
                        }
                        childShape.getAssigneeInfo().setAssignees(assignees);
                    }
                }
                //抄送人
                UpdateProcessNodeShapeUserDTO informerInfo = node.getInformerInfo();
                if (informerInfo != null) {
                    Set<String> assigneesStr = informerInfo.getAssigneesStr();
                    if (Utils.isNotEmpty(assigneesStr)) {
                        for (String s : assigneesStr) {
                            List<Assignee> assignees = new ArrayList<>(16);
                            assignees.add(JSON.parseObject(s, Assignee.class));
                            childShape.getInformerInfo().setAssignees(assignees);
                        }
                    }
                }
                //候选人
                UpdateProcessNodeShapeUserDTO noneAssignee = node.getNoneAssignee();
                if (noneAssignee != null) {
                    Set<String> assigneesStr = noneAssignee.getAssigneesStr();
                    if (Utils.isNotEmpty(assigneesStr)) {
                        for (String s : assigneesStr) {
                            List<Assignee> assignees = new ArrayList<>(16);
                            assignees.add(JSON.parseObject(s, Assignee.class));
                            childShape.getNoneAssignee().setAssignees(assignees);
                        }
                    }
                }
                //加签人
                UpdateProcessNodeShapeUserDTO addSignAssigneeInfo = node.getAddSignAssigneeInfo();
                if (addSignAssigneeInfo != null) {
                    Set<String> assigneesStr = addSignAssigneeInfo.getAssigneesStr();
                    if (Utils.isNotEmpty(assigneesStr)) {
                        for (String s : assigneesStr) {
                            List<Assignee> assignees = new ArrayList<>(16);
                            assignees.add(JSON.parseObject(s, Assignee.class));
                            childShape.getAddSignAssigneeInfo().setAssignees(assignees);
                        }
                    }
                }
                //转发人
                UpdateProcessNodeShapeUserDTO forwardAssigneeInfo = node.getForwardAssigneeInfo();
                if (forwardAssigneeInfo != null) {
                    Set<String> assigneesStr = forwardAssigneeInfo.getAssigneesStr();
                    if (Utils.isNotEmpty(assigneesStr)) {
                        for (String s : assigneesStr) {
                            List<Assignee> assignees = new ArrayList<>(16);
                            assignees.add(JSON.parseObject(s, Assignee.class));
                            childShape.getForwardAssigneeInfo().setAssignees(assignees);
                        }
                    }
                }
                //添加处理人
                UpdateProcessNodeShapeUserDTO addAssigneeInfo = node.getAddAssigneeInfo();
                if (addAssigneeInfo != null) {
                    Set<String> assigneesStr = addAssigneeInfo.getAssigneesStr();
                    if (Utils.isNotEmpty(assigneesStr)) {
                        for (String s : assigneesStr) {
                            List<Assignee> assignees = new ArrayList<>(16);
                            assignees.add(JSON.parseObject(s, Assignee.class));
                            childShape.getAddAssigneeInfo().setAssignees(assignees);
                        }
                    }
                }
                childShapes.add(childShape);
            }
        }
    }

    /**
     * 节点和目标节点
     */
    private static Map<String, List<ChildShapeOutgoing>> nodeAndTarget(List<UpdateProcessEdgeDTO> edges) {
        Map<String, List<ChildShapeOutgoing>> nodeAndTarget = new HashMap<>(16);
        if (Utils.isNotEmpty(edges)) {
            for (UpdateProcessEdgeDTO edge : edges) {
                //来源SourceId和本节点ResourceId
                initNodeAndTarget(edge.getResourceId(), nodeAndTarget, edge.getSourceId());
                //本节点ResourceId和目标节点
                initNodeAndTarget(edge.getTargetId(), nodeAndTarget, edge.getResourceId());
            }
        }
        return nodeAndTarget;
    }

    /**
     * 填充节点和目标节点信息
     *
     * @param targetId      目标节点
     * @param nodeAndTarget 节点和目标节点
     * @param sourceId      来源节点
     */
    private static void initNodeAndTarget(String targetId,
                                          Map<String, List<ChildShapeOutgoing>> nodeAndTarget,
                                          String sourceId) {
        ChildShapeOutgoing childShapeOutgoing = new ChildShapeOutgoing();
        childShapeOutgoing.setResourceId(targetId);

        List<ChildShapeOutgoing> exitsOuts = nodeAndTarget.get(sourceId);
        if (Utils.isNotEmpty(exitsOuts)) {
            exitsOuts.add(childShapeOutgoing);
        } else {
            List<ChildShapeOutgoing> outs = new ArrayList<>(10);
            outs.add(childShapeOutgoing);
            nodeAndTarget.put(sourceId, outs);
        }
    }
}
