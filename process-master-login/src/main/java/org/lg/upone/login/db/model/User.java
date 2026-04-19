package org.lg.upone.login.db.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 用户表
 */
public class User {
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
     * 0默认
     */
    private Integer status;

    /**
     * 0默认
     */
    private Integer flags;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户编码
     */
    private String userKey;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     *
     */
    private String deptId;

    /**
     *
     */
    private String deptName;

    /**
     *
     */
    private String orgId;

    /**
     *
     */
    private String orgName;

    private Set<Roles> roles = new HashSet<>();

    public User() {
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.userKey = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
        this.gmtCreate = System.currentTimeMillis();
        this.gmtModified = System.currentTimeMillis();
        this.flags = 0;
        this.status = 0;
        this.deptId="deptid";
        this.orgId="aaa";
        this.deptName="xx部门";
        this.orgName="xx公司";
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}