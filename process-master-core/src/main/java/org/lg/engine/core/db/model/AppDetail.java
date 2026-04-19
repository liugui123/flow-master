package org.lg.engine.core.db.model;

/**
 * 应用详情
 */
public class AppDetail {
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
     * 1：仅PC端可见 2：仅手机端可见 3：双端可见
     */
    private Integer status;

    /**
     * 1草稿  2已发布  4分组  8 流程 16表单  32流程表单 64接口
     */
    private Integer flags;

    /**
     * 详情key
     */
    private String detailKey;

    /**
     * 详情名字
     */
    private String detailName;

    /**
     * 父节点   -1代表直接挂在应用下
     */
    private Long pid;

    /**
     *
     */
    private Long appId;

    /**
     * 附加信息
     */
    private String extra;

    /**
     * 排序分数:分数越高排序越靠前
     */
    private Integer sortScore;

    /**
     * 图标
     */
    private String iconUrl;

    /**
     * Key
     */
    private String appDetailKey;

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

    public String getDetailKey() {
        return detailKey;
    }

    public void setDetailKey(String detailKey) {
        this.detailKey = detailKey;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Integer getSortScore() {
        return sortScore;
    }

    public void setSortScore(Integer sortScore) {
        this.sortScore = sortScore;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getAppDetailKey() {
        return appDetailKey;
    }

    public void setAppDetailKey(String appDetailKey) {
        this.appDetailKey = appDetailKey;
    }
}