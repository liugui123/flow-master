package org.lg.engine.core.db.model;

/**
 * 运行时节点实例
 */
public class RuActinst {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Long gmtCreate;

    /**
     *
     */
    private Long gmtModified;

    /**
     * 流程实例id
     */
    private Long procinstId;

    /**
     * 运行实例名称
     */
    private String actinstName;

    /**
     * 流程图中定义节点的标识
     */
    private String actinstKey;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 流程实例类型 开始节点 start_node 用户节点 user_node \n系统节点 sys_node 结束节点 end_node\n线 sequence_flow\n
     */
    private String actinstType;

    /**
     * 标志位
     */
    private Integer flags;

    /**
     * 状态 0运行中 1已完成 2 待处理
     */
    private Integer status;

    /**
     * 节点规则json数据，存放人员/表单权限/操作规则/高级规则信息
     */
    private String varJson;

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

    public Long getProcinstId() {
        return procinstId;
    }

    public void setProcinstId(Long procinstId) {
        this.procinstId = procinstId;
    }

    public String getActinstName() {
        return actinstName;
    }

    public void setActinstName(String actinstName) {
        this.actinstName = actinstName;
    }

    public String getActinstKey() {
        return actinstKey;
    }

    public void setActinstKey(String actinstKey) {
        this.actinstKey = actinstKey;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getActinstType() {
        return actinstType;
    }

    public void setActinstType(String actinstType) {
        this.actinstType = actinstType;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVarJson() {
        return varJson;
    }

    public void setVarJson(String varJson) {
        this.varJson = varJson;
    }
}