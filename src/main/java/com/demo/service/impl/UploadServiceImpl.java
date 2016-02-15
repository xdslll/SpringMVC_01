package com.demo.service.impl;

import com.demo.dao.EnfordProductImportHistoryMapper;
import com.demo.dao.EnfordSystemUserMapper;
import com.demo.model.EnfordProductImportHistory;
import com.demo.model.EnfordSystemUser;
import com.demo.service.UploadService;
import com.demo.util.Config;
import com.demo.util.Consts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * @author xiads
 * @date 16/2/3
 */
@Service("uploadService")
public class UploadServiceImpl implements UploadService {

    @Resource
    EnfordProductImportHistoryMapper importMapper;

    @Resource
    EnfordSystemUserMapper userMapper;

    @Override
    public int addImportHistory(EnfordProductImportHistory commodityImport) {
        return importMapper.insert(commodityImport);
    }

    @Override
    public List<EnfordProductImportHistory> getImportHistory(int type) {
        List<EnfordProductImportHistory> importList = importMapper.selectByType(type);
        for (EnfordProductImportHistory importHistory : importList) {
            //写入用户姓名
            int userId = importHistory.getCreateBy();
            EnfordSystemUser user = userMapper.selectByPrimaryKey(userId);
            importHistory.setCreateUsername(user.getName());
            //写入状态名称
            switch (importHistory.getState()) {
                case Consts.IMPORT_STATE_NOT_IMPORT:
                    importHistory.setStateDesp("未导入");
                    break;
                case Consts.IMPORT_STATE_HAVE_IMPORT:
                    importHistory.setStateDesp("已导入");
                    break;
                case Consts.IMPORT_STATE_IMPORT_FAILED:
                    importHistory.setStateDesp("导入失败");
                    break;
            }
        }
        return importList;
    }

    @Override
    public int deleteImportHistory(int id) {
        return importMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteImportHistoryAndFile(int id) {
        EnfordProductImportHistory importHistory = importMapper.selectByPrimaryKey(id);
        File file = Config.getFilePath(importHistory,
                Config.getUploadFilePath());
        if (file.exists()) {
            file.delete();
        }
        return deleteImportHistory(id);
    }

    @Override
    public int updateImportHistory(EnfordProductImportHistory importHistory) {
        return importMapper.updateByPrimaryKeySelective(importHistory);
    }

    @Override
    public EnfordProductImportHistory getImportHistoryById(int importId) {
        return importMapper.selectByPrimaryKey(importId);
    }

}
