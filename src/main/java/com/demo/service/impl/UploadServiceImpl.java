package com.demo.service.impl;

import com.demo.dao.EnfordProductImportHistoryMapper;
import com.demo.model.EnfordProductImportHistory;
import com.demo.service.UploadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiads
 * @date 16/2/3
 */
@Service("uploadService")
public class UploadServiceImpl implements UploadService {

    @Resource
    EnfordProductImportHistoryMapper importMapper;

    @Override
    public int addImportHistory(EnfordProductImportHistory commodityImport) {
        return importMapper.insert(commodityImport);
    }

    @Override
    public List<EnfordProductImportHistory> getImportHistory() {
        return importMapper.select();
    }


}
