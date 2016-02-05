package com.demo.service.impl;

import com.demo.dao.*;
import com.demo.model.*;
import com.demo.service.ResearchMarketService;
import com.demo.util.Config;
import com.demo.util.ExcelUtil;
import com.demo.util.StringUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/3
 */
@Service("marketService")
public class ResearchMarketSerivceImpl implements ResearchMarketService {

    @Resource
    EnfordProductCategoryMapper categoryMapper;

    @Resource
    EnfordProductCommodityMapper productMapper;

    @Resource
    EnfordProductSupplierMapper supplierMapper;

    @Resource
    EnfordProductPriceMapper priceMapper;

    @Resource
    EnfordMarketResearchMapper researchMapper;

    @Resource
    EnfordMarketResearchCommodityMapper researchCommodityMapper;

    @Resource
    EnfordProductCityMapper cityMapper;

    @Resource
    EnfordProductCompetitorsMapper competitorsMapper;

    @Resource
    EnfordProductDepartmentMapper deptMapper;

    @Override
    public int countCategory(EnfordProductCategory category) {
        return categoryMapper.countByCode(category.getCode());
    }

    @Override
    public int addCategory(EnfordProductCategory category) {
        return categoryMapper.insert(category);
    }

    @Override
    public int updateCategory(EnfordProductCategory category) {
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int deleteCategory(int id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int addProductCommodity(EnfordProductCommodity product) {
        return productMapper.insert(product);
    }

    @Override
    public int updateProductCommodity(EnfordProductCommodity product) {
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public int deleteProductCommodity(int id) {
        return productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<EnfordProductCommodity> selectCommodityByParam(Map<String, Object> param) {
        return productMapper.selectByParam(param);
    }

    @Override
    public int addSupplier(EnfordProductSupplier supplier) {
        return supplierMapper.insert(supplier);
    }

    @Override
    public int updateSupplier(EnfordProductSupplier supplier) {
        return supplierMapper.updateByPrimaryKeySelective(supplier);
    }

    @Override
    public int deleteSupplier(int id) {
        return supplierMapper.deleteByPrimaryKey(id);
    }

    @Override
    public EnfordProductSupplier selectSupplierByCode(String code) {
        return supplierMapper.selectByCode(code);
    }

    @Override
    public int addPrice(EnfordProductPrice price) {
        return priceMapper.insert(price);
    }

    @Override
    public int updatePrice(EnfordProductPrice price) {
        return priceMapper.updateByPrimaryKeySelective(price);
    }

    @Override
    public int deletePrice(EnfordProductPriceKey key) {
        return priceMapper.deleteByPrimaryKey(key);
    }

    @Override
    public int addResearch(EnfordMarketResearch research) {
        return researchMapper.insert(research);
    }

    @Override
    public int updateResearch(EnfordMarketResearch research) {
        return researchMapper.updateByPrimaryKeySelective(research);
    }

    @Override
    public int deleteResearch(int id) {
        return researchMapper.deleteByPrimaryKey(id);
    }

    @Override
    public EnfordMarketResearch selectByImportId(int importId) {
        return researchMapper.selectByImportId(importId);
    }

    @Override
    public int addResearchCommodity(EnfordMarketResearchCommodity researchCommodity) {
        return researchCommodityMapper.insert(researchCommodity);
    }

    @Override
    public int updateResearchCommodity(EnfordMarketResearchCommodity researchCommodity) {
        return researchCommodityMapper.updateByPrimaryKeySelective(researchCommodity);
    }

    @Override
    public int delteResearchCommodity(int id) {
        return researchCommodityMapper.deleteByPrimaryKey(id);
    }

    /**
     * 将Excel数据导入数据库
     *
     * @param importHistory
     * @param user
     */
    @Transactional
    @Override
    public boolean importMarketResearchData(
            EnfordProductImportHistory importHistory,
            EnfordSystemUser user,
            boolean ifCoverData) {
        boolean ret = false;
        //获取Excel文件对象
        File excelFile = Config.getFilePath(importHistory,
                Config.getUploadFilePath());
        //判断文件是否存在
        if (excelFile.exists()) {
            Workbook workbook = null;
            try {
                workbook = ExcelUtil.read(excelFile);
                Sheet sheet = workbook.getSheetAt(0);
                //获取总行数
                int rowNum = sheet.getLastRowNum() + 1;
                //去除标题和列名
                int firstRow = sheet.getFirstRowNum() + 2;
                //获取user id
                int userId = user.getId();
                int orgId = user.getOrgId();
                //获取导入文件的id
                int importId = importHistory.getId();
                //获取当前时间
                Date date = new Date();
                //转换当前时间格式
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
                String dateStr = sdf.format(date);
                EnfordMarketResearch research;
                //判断市调清单是否存在
                research = selectByImportId(importId);
                if (research == null) {
                    //新建一条市调清单
                    research = new EnfordMarketResearch();
                    //设置市调清单数据
                    research.setName(importHistory.getFileName());
                    research.setCreateBy(userId);
                    research.setCreateDt(date);
                    research.setImportId(importHistory.getId());
                    //将市调清单插入数据库
                    addResearch(research);
                    research = selectByImportId(importId);
                }

                //解析导入数据
                for (int index = firstRow; index < rowNum; index++) {
                    //获取行
                    Row row = sheet.getRow(index);
                    //获取二级品类编码
                    Cell cellCategoryCode = getCell(row, 0);
                    //获取二级品类名称
                    Cell cellCategoryName = getCell(row, 1);
                    //加入分类
                    addCategory(cellCategoryCode, cellCategoryName,
                            0, userId, date, ifCoverData);
                    //获取四级品类编码
                    Cell cellCategoryCode2 = getCell(row, 2);
                    //获取四级品类名称
                    Cell cellCategoryName2 = getCell(row, 3);
                    //加入分类
                    addCategory(cellCategoryCode2, cellCategoryName2,
                            Integer.valueOf(cellCategoryCode.getStringCellValue()),
                            userId, date, ifCoverData);
                    //记录商品信息
                    EnfordProductCommodity productCod = new EnfordProductCommodity();
                    //记录供应商信息
                    EnfordProductSupplier supplier = new EnfordProductSupplier();
                    //记录商品价格信息
                    EnfordProductPrice price = new EnfordProductPrice();
                    //记录市调清单和商品的对应关系
                    EnfordMarketResearchCommodity researchCommodity = new EnfordMarketResearchCommodity();
                    //设置商品的品类编码
                    productCod.setCategoryCode(
                            Integer.valueOf(cellCategoryCode2.getStringCellValue()));
                    //获取商品编码
                    Cell cellCodCode = getCell(row, 4);
                    productCod.setCode(cellCodCode.getStringCellValue());
                    //获取商品货号
                    Cell cellArtNo = getCell(row, 5);
                    productCod.setArtNo(cellArtNo.getStringCellValue());
                    //获取商品名称
                    Cell cellCodName = getCell(row, 6);
                    productCod.setpName(cellCodName.getStringCellValue());
                    //获取商品规格
                    Cell cellCodSize = getCell(row, 7);
                    productCod.setpSize(cellCodSize.getStringCellValue());
                    //获取商品规格单位
                    Cell cellCodUnit = getCell(row, 8);
                    productCod.setUnit(cellCodUnit.getStringCellValue());
                    //获取商品条形码
                    Cell cellCodBarCode = getCell(row, 9);
                    productCod.setBarCode(cellCodBarCode.getStringCellValue());
                    //获取供应商编码
                    Cell cellSupCode = getCell(row, 10);
                    productCod.setSupplierCode(cellSupCode.getStringCellValue());
                    supplier.setCode(cellSupCode.getStringCellValue());
                    //获取供应商名称
                    Cell cellSupName = getCell(row, 11);
                    supplier.setName(cellSupName.getStringCellValue());
                    //获取商品进价
                    Cell cellPurchasePrice = getCell(row, 12);
                    try {
                        float purchasePrice = Float.valueOf(cellPurchasePrice.getStringCellValue());
                        price.setPurchasePrice(purchasePrice);
                    } catch (Exception ex) {
                        price.setPurchasePrice(0.0f);
                    }
                    //获取商品我司零价
                    Cell cellRetailPrice = getCell(row, 13);
                    try {
                        float retailPrice = Float.valueOf(cellRetailPrice.getStringCellValue());
                        price.setRetailPrice(retailPrice);
                    } catch (Exception ex) {
                        price.setRetailPrice(0.0f);
                    }
                    try {
                        //将供应商信息插入数据库
                        addSupplier(supplier);
                    } catch (Exception ex) {
                        if (ifCoverData) {
                            updateSupplier(supplier);
                        }
                    }
                    //将商品信息插入数据库
                    productCod.setCreateBy(userId);
                    productCod.setCreateDt(date);
                    //查询该商品是否已经存在
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("pName", productCod.getpName());
                    param.put("pSize", productCod.getpSize());
                    param.put("unit", productCod.getUnit());
                    param.put("supplierCode", productCod.getSupplierCode());
                    List<EnfordProductCommodity> productCodList = selectCommodityByParam(param);
                    EnfordProductCommodity productCod1 = null;
                    if (productCodList != null && productCodList.size() > 0) {
                        productCod1 = productCodList.get(0);
                    }
                    //商品id
                    int codId = -1;
                    if (productCod1 == null) {
                        //将商品信息插入数据库
                        addProductCommodity(productCod);
                        //获取商品id
                        codId = getProductCodId();
                        productCod.setId(codId);
                    } else {
                        if (ifCoverData) {
                            productCod.setId(productCod1.getId());
                            updateProductCommodity(productCod);
                        }
                        codId = productCod.getId();
                    }
                    //插入价格信息
                    price.setOrgId(orgId);
                    price.setCreateBy(userId);
                    price.setCreateDt(date);
                    price.setComId(codId);
                    price.setImportId(importId);
                    try {
                        //将价格信息插入数据库
                        addPrice(price);
                    } catch (Exception ex) {

                    }
                    //插入市调清单
                    researchCommodity.setResId(research.getId());
                    researchCommodity.setCodId(codId);
                    try {
                        //将市调清单和商品的对应关系插入数据库
                        addResearchCommodity(researchCommodity);
                    } catch (Exception ex) {

                    }
                }
                ret = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Transactional
    @Override
    public boolean importDeptData(EnfordProductImportHistory importHistory, boolean ifCoverData) {
        boolean ret = false;
        //获取Excel文件对象
        File excelFile = Config.getFilePath(importHistory,
                Config.getUploadFilePath());
        //判断文件是否存在
        if (excelFile.exists()) {
            Workbook workbook = null;
            try {
                workbook = ExcelUtil.read(excelFile);
                Sheet sheet = workbook.getSheetAt(0);
                //获取总行数
                int rowNum = sheet.getLastRowNum() + 1;
                //去除标题和列名
                int firstRow = sheet.getFirstRowNum() + 1;
                for (int index = firstRow; index < rowNum; index++) {
                    //获取所在城市
                    Row row = sheet.getRow(index);
                    //获取城市数据
                    Cell cityCell = getCell(row, 0);
                    String cityName = cityCell.getStringCellValue();
                    EnfordProductCity city;
                    //查询城市数据是否已经添加
                    Map<String, Object> cityParam = new HashMap<String, Object>();
                    cityParam.put("name", cityName);
                    List<EnfordProductCity> cities = cityMapper.selectByParam(cityParam);
                    //导入城市数据
                    if (cities != null && cities.size() > 0) {
                        city = cities.get(0);
                    } else {
                        city = new EnfordProductCity();
                        city.setName(cityName);
                        int size = cityMapper.insert(city);
                        if (size > 0) {
                            city.setId(cityMapper.maxId());
                        }
                    }

                    //获取部门编码
                    Cell deptCodeCell = getCell(row, 1);
                    String deptCode = deptCodeCell.getStringCellValue();
                    //获取部门名称
                    Cell deptNameCell = getCell(row, 2);
                    String deptName = deptNameCell.getStringCellValue();
                    //获取竞争对手名称
                    Cell compNameCell = getCell(row, 3);
                    String compName = compNameCell.getStringCellValue();
                    int compId = -1;
                    //判断竞争对手是否为空
                    if (!StringUtil.isEmpty(compName)) {
                        //查询竞争对手数据是否已经录入系统
                        EnfordProductCompetitors comp;
                        Map<String, Object> compParam = new HashMap<String, Object>();
                        compParam.put("name", compName);
                        compParam.put("city_id", city.getId());
                        List<EnfordProductCompetitors> comps = competitorsMapper.selectByParam(compParam);
                        //导入竞争对手数据
                        if (comps != null && comps.size() > 0) {
                            comp = comps.get(0);
                        } else {
                            comp = new EnfordProductCompetitors();
                            //将竞争对手数据导入数据库
                            comp.setName(compName);
                            comp.setCityId(city.getId());
                            int size = competitorsMapper.insert(comp);
                            if (size > 0) {
                                comp.setId(competitorsMapper.maxId());
                            } else {
                                throw new RuntimeException("插入竞争对手数据失败!");
                            }
                        }
                        compId = comp.getId();
                    }

                    if (!StringUtil.isEmpty(deptCode) &&
                            !StringUtil.isEmpty(deptName)) {
                        //查询部门数据是否录入系统
                        EnfordProductDepartment dept;
                        Map<String, Object> deptParam = new HashMap<String, Object>();
                        deptParam.put("code", deptCode);
                        //查询竞争对手数据是否已经录入系统
                        List<EnfordProductDepartment> depts = deptMapper.selectByParam(deptParam);
                        if (depts != null && depts.size() > 0) {
                            dept = depts.get(0);
                        } else {
                            dept = new EnfordProductDepartment();
                            //对部门编号进行处理
                            try {
                                int deptCodeInt = Integer.valueOf(deptCode);
                                if (deptCodeInt > 0 && deptCodeInt < 10) {
                                    deptCode = "000" + deptCodeInt;
                                } else if (deptCodeInt > 10 && deptCodeInt < 100) {
                                    deptCode = "00" + deptCodeInt;
                                } else if (deptCodeInt > 100 && deptCodeInt < 1000) {
                                    deptCode = "0" + deptCodeInt;
                                }
                                dept.setCode(deptCode);
                            } catch (Exception ex) {
                                dept.setCode(deptCode);
                            }
                            dept.setName(deptName);
                            dept.setCompId(compId);
                            dept.setCityId(city.getId());
                            int size = deptMapper.insert(dept);
                            if (size <= 0) {
                                throw new RuntimeException("插入部门对手数据失败!");
                            }
                        }
                    }
                }
                ret = true;
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    private int getProductCodId() {
        return productMapper.maxId();
    }

    private Cell getCell(Row row, int index) {
        Cell cell = row.getCell(index);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return cell;
    }

    /**
     * 根据传入的信息,判断该品类是否存在,如果不存在,则插入数据库
     *
     * @param cellCode
     * @param cellName
     * @param userId
     * @param date
     * @param ifCover
     *
     * @return true - 插入新品类, false - 该品类已经存在
     */
    private boolean addCategory(Cell cellCode, Cell cellName,
                                int parent, int userId, Date date, boolean ifCover) {
        //将数据存入分类
        EnfordProductCategory category = new EnfordProductCategory();
        //设置品类编码
        category.setCode(Integer.valueOf(cellCode.getStringCellValue()));
        //设置品类名称
        category.setName(cellName.getStringCellValue());
        //设置创建人
        category.setCreateBy(userId);
        //设置创建时间
        category.setCreateDt(date);
        //设置父品类
        category.setParent(parent);
        //根据二级码查询该分类是否存在
        int queryCatCount = countCategory(category);
        //如果数据库中没有该条数据,则立即插入
        if (queryCatCount == 0) {
            //插入数据库
            addCategory(category);
            return true;
        } else {
            //判断是否覆盖原数据
            if (ifCover) {
                updateCategory(category);
            }
            return false;
        }
    }
}
