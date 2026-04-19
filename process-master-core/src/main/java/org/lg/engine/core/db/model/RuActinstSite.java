package org.lg.engine.core.db.model;

/**
 * 节点实例的位置
 */
public class RuActinstSite {
    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * 修改时间
     */
    private Long gmtModified;

    /**
     * 流程实例id
     */
    private Long proInstId;

    /**
     * 当前节点id
     */
    private Long actinstId;

    /**
     * 当前节点的后面id
     */
    private Long targetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getProInstId() {
        return proInstId;
    }

    public void setProInstId(Long proInstId) {
        this.proInstId = proInstId;
    }

    public Long getActinstId() {
        return actinstId;
    }

    public void setActinstId(Long actinstId) {
        this.actinstId = actinstId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
}