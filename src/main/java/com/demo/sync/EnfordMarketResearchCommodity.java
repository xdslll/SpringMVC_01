package com.demo.sync;

public class EnfordMarketResearchCommodity {

    public static final String colId = "id";
    public static final String colResId = "res_id";
    public static final String colCodId = "cod_id";
    public static final String colBillNumber = "bill_number";
    public static final String colInsideId = "inside_id";
    public static final String colGoodsSpec = "goods_spec";
    public static final String colGoodsName = "goods_name";
    public static final String colBaseBarCode = "base_bar_code";
    public static final String colBaseMeasureUnit = "base_measure_unit";
    public static final String colMrBeginDate = "mr_begin_date";
    public static final String colMrEndDate = "mr_end_date";

    private Integer id;

    private Integer resId;

    private Integer codId;

    private String bill_number;

    private int inside_id;

    private String goods_spec;

    private String goods_name;

    private String base_bar_code;

    private String base_measure_unit;

    private String mr_begin_date;

    private String mr_end_date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getCodId() {
        return codId;
    }

    public void setCodId(Integer codId) {
        this.codId = codId;
    }

    public String getBill_number() {
        return bill_number;
    }

    public void setBill_number(String bill_number) {
        this.bill_number = bill_number;
    }

    public int getInside_id() {
        return inside_id;
    }

    public void setInside_id(int inside_id) {
        this.inside_id = inside_id;
    }

    public String getGoods_spec() {
        return goods_spec;
    }

    public void setGoods_spec(String goods_spec) {
        this.goods_spec = goods_spec;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getBase_bar_code() {
        return base_bar_code;
    }

    public void setBase_bar_code(String base_bar_code) {
        this.base_bar_code = base_bar_code;
    }

    public String getBase_measure_unit() {
        return base_measure_unit;
    }

    public void setBase_measure_unit(String base_measure_unit) {
        this.base_measure_unit = base_measure_unit;
    }

    public String getMr_begin_date() {
        return mr_begin_date;
    }

    public void setMr_begin_date(String mr_begin_date) {
        this.mr_begin_date = mr_begin_date;
    }

    public String getMr_end_date() {
        return mr_end_date;
    }

    public void setMr_end_date(String mr_end_date) {
        this.mr_end_date = mr_end_date;
    }
}