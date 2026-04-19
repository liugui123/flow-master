package org.lg.engine.core.service.manager;

import org.lg.engine.core.client.model.WfUser;

import java.util.ArrayList;
import java.util.Collection;

public class TreeNode extends WfUser {
    private String key;
    private String title;
    private Collection<TreeNode> children; // 直接用List存储子节点

    public TreeNode(String id, String name) {
        this.key = id;
        this.title = name;
        this.children = new ArrayList<>();
    }

    public TreeNode() {
    }

    // getters and setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(Collection<TreeNode> children) {
        this.children = children;
    }

    public void addChild(TreeNode child) {
        this.children.add(child);
    }
}