package org.lg.engine.core.db.model;

/**
 * 应用详情
 */
public class ReServiceModel {
    /**
     *
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
     *
     */
    private Integer status;

    /**
     * 0 人员接口 1 条件接口
     */
    private Integer flags;

    /**
     * 业务方的填入注册表单标识，不能重复
     */
    private String serviceModelKey;

    /**
     * 名字
     */
    private String name;

    /**
     * 三方查询接口
     */
    private String url;

    /**
     * 应用key
     */
    private String appKey;

    /**
     * 和id对应
     */
    private String modelKey;

    /**
     * 模型发布来源id
     */
    private Long deServiceModelId;

    /**
     *
     */
    private Integer modelVersion;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public String getServiceModelKey() {
        return serviceModelKey;
    }

    public void setServiceModelKey(String serviceModelKey) {
        this.serviceModelKey = serviceModelKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public Long getDeServiceModelId() {
        return deServiceModelId;
    }

    public void setDeServiceModelId(Long deServiceModelId) {
        this.deServiceModelId = deServiceModelId;
    }

    public Integer getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(Integer modelVersion) {
        this.modelVersion = modelVersion;
    }
}