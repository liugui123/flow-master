package org.lg.upone.login.model.res;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.WfUser;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserLoginResponse {
    /**
     * 用户
     */
    private WfUser user;
    /**
     * 权限
     */
    private List<String> permissions;
    /**
     * 角色
     */
    private List<String> roles;

    /**
     * token
     */
    private String token = UUID.randomUUID().toString();

    /**
     * 过期时间
     */
    private Long expireAt = System.currentTimeMillis()+(1000 * 60 * 30);
}
