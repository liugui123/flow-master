package org.lg.engine.core.service.manager;

import org.lg.engine.core.Convert;
import org.lg.engine.core.client.model.Assignee;
import org.lg.engine.core.client.utils.Utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserHelper {
    /**
     * 有一组JSON数据[{"deptId":"deptid1","id":"111","name":"admin2","orgId":"aaa"},{"deptId":"deptid2","id":"222","name":"admin3","orgId":"aaa"},{"deptId":"deptid3","id":"333","name":"admin4","orgId":"bbb"}],
     * 我想把上面的JSON数据通过java转换成结构为包含key，title，children的树对象，其中key对应JSON里的对象id，title对应JSON里对象的name，children是子节点，子节点按照orgId最上层deptId其次，id在叶子节点，orgId相同时归类到一个orgId下面，deptId同理
     */
    public static TreeNode convertUserJsonToTree(Collection<Assignee> assignees) {
        TreeNode root = new TreeNode("root", "root");
        if (Utils.isEmpty(assignees)) {
            return root;
        }
        Map<String, TreeNode> orgNodes = new HashMap<>(); // 用于存储按照orgId分类的节点

        for (Assignee assignee : assignees) {
            String id = assignee.getId();
            String name = assignee.getName();
            String deptId = assignee.getDeptId();
            String orgId = assignee.getOrgId();
            String deptName = assignee.getDeptName();
            String orgName = assignee.getOrgName();

            TreeNode currentNode = Convert.INSTANCE.assigneeToTreeNode(assignee);
            currentNode.setKey(id);
            currentNode.setTitle(name);
            if (!orgNodes.containsKey(orgId)) {
                TreeNode orgNode = new TreeNode(orgId, orgName == null ? orgId : orgName); // 创建一个org级别的节点
                orgNodes.put(orgId, orgNode);
            }

            TreeNode orgNode = orgNodes.get(orgId);
            boolean deptNodeFound = false;
            for (TreeNode deptNode : orgNode.getChildren()) {
                if (deptNode.getKey().equals(deptId)) {
                    deptNode.addChild(currentNode);
                    deptNodeFound = true;
                    break;
                }
            }
            if (!deptNodeFound) {
                TreeNode deptNode = new TreeNode(deptId, deptName == null ? deptId : deptName); // 创建一个dept级别的节点
                deptNode.addChild(currentNode);
                orgNode.addChild(deptNode);
            }
        }
        root.setChildren(orgNodes.values());
        return root;
    }
}