package com.demo.service;

import com.demo.model.EnfordProductImportHistory;

import java.util.List;

/**
 * @author xiads
 * @date 16/2/3
 */
public interface UploadService {

    int addImportHistory(EnfordProductImportHistory commodityImport);

    List<EnfordProductImportHistory> getImportHistory();

}
