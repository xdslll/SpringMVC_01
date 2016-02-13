package com.demo.service.impl;

import com.demo.dao.EnfordProductSupplierMapper;
import com.demo.model.EnfordProductCategory;
import com.demo.model.EnfordProductSupplier;
import com.demo.service.SupplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiads
 * @date 16/2/5
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

    @Resource
    EnfordProductSupplierMapper supplierMapper;

    @Override
    public List<EnfordProductSupplier> select() {
        return supplierMapper.select();
    }
}
