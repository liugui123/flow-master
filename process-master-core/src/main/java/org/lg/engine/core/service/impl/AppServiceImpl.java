package org.lg.engine.core.service.impl;

import org.lg.engine.core.Convert;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.client.model.request.UpdateAppRequest;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.AdminAppMapper;
import org.lg.engine.core.db.model.AdminApp;
import org.lg.engine.core.service.AppService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class AppServiceImpl implements AppService {

    @Resource
    private AdminAppMapper adminAppMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateAppRequest updateAppRequest) {
        Long id = selectFirstIdByAppKey(updateAppRequest.getAppKey());
        if (id == null) {
            Operator operator = updateAppRequest.getOperator();
            AdminApp adminApp = Convert.INSTANCE.updateAppRequestToAdminApp(updateAppRequest);
            adminApp.setGmtCreate(System.currentTimeMillis());
            adminApp.setGmtModified(System.currentTimeMillis());
            adminApp.setStatus(0);
            adminApp.setFlags(0);
            adminApp.setCreatorId(operator.getId());
            adminApp.setCreatorName(operator.getName());
            adminApp.setCreatorOrgId(operator.getOrgId());
            adminApp.setCreatorOrgName(operator.getOrgName());
            adminApp.setCreatorDeptId(operator.getDeptId());
            adminApp.setCreatorDeptName(operator.getDeptName());
            adminApp.setSortScore(0);
            adminApp.setAppKey(UUID.randomUUID().toString().replace("-", ""));
            adminAppMapper.insert(adminApp);
        } else {
            AdminApp adminApp = adminAppMapper.selectByPrimaryKey(id);
            Assert.isTrue(
                    adminApp != null,
                    WfExceptionCode.APP_BLANK.getMsg(),
                    WfExceptionCode.APP_BLANK.getCode()
            );
            adminApp.setAppName(updateAppRequest.getAppName());
            adminApp.setAppDesc(updateAppRequest.getAppDesc());
            adminApp.setAppIcon(updateAppRequest.getAppIcon());
            adminApp.setGmtModified(System.currentTimeMillis());
            adminAppMapper.updateByPrimaryKey(adminApp);
        }
    }

//    @Override
//    public AppResponse getByAppKey(String appKey) {
//        if (Utils.isEmpty(appKey)) {
//            return null;
//        }
//        AdminApp adminApp = adminAppMapper.selectFirstByAppKey(appKey);
//        return Convert.INSTANCE.adminAppToAppResponse(adminApp);
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String appKey) {
        Long id = selectFirstIdByAppKey(appKey);
        if (id == null) {
            return;
        }
        adminAppMapper.deleteByPrimaryKey(id);
    }

//    @Override
//    public List<AdminApp> selectByCreatorIdAndCreatorOrgId(String creatorId, String orgId) {
//        return adminAppMapper.selectByCreatorIdAndCreatorOrgId(creatorId, orgId);
//    }

    @Override
    public Long selectFirstIdByAppKey(String appKey) {
        if (Utils.isEmpty(appKey)) {
            return null;
        }
        return adminAppMapper.selectFirstIdByAppKey(appKey);
    }

    @Override
    public AdminApp selectFirstByAppKey(String appKey) {
        if (Utils.isEmpty(appKey)) {
            return null;
        }
        return adminAppMapper.selectFirstByAppKey(appKey);
    }

//    @Override
//    public void setSortScore(AppSetSortScoreRequest request) {
//        AdminApp adminApp = adminAppMapper.selectByPrimaryKey(request.getId());
//        Assert.isTrue(
//                adminApp != null,
//                WfExceptionCode.APP_BLANK.getMsg(),
//                WfExceptionCode.APP_BLANK.getCode()
//        );
//
//        adminAppMapper.updateSortScore(request.getId(), request.getSort(), System.currentTimeMillis());
//    }

}
