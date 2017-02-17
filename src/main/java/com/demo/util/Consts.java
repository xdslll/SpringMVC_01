package com.demo.util;

/**
 * @author xiads
 * @date 16/1/30
 */
public interface Consts {

    public static final String HTTP_URL = "http://192.168.1.180:8080/";
    //public static final String HTTP_URL = "http://weixin.enford.com.cn/sg/";

    public static final String MENU_STATE_OPEN = "open";
    public static final String MENU_STATE_CLOSED = "closed";

    public static final String SUCCESS = "0";
    public static final String FAILED = "-1";

    public static final String PRODUCT_NAME = "后台管理";
    public static final String FOOT_INFO = "2015-2016@copyright";

    public static final int IMPORT_STATE_NOT_IMPORT = 0;
    public static final int IMPORT_STATE_HAVE_IMPORT = 1;
    public static final int IMPORT_STATE_IMPORT_FAILED = 2;

    public static final int IMPORT_TYPE_PRODUCT = 1;
    public static final int IMPORT_TYPE_DEPARTMENT = 2;

    public static final int RESEARCH_STATE_NOT_PUBLISH = 0;
    public static final int RESEARCH_STATE_HAVE_PUBLISHED = 1;
    public static final int RESEARCH_STATE_HAVE_FINISHED = 2;
    public static final int RESEARCH_STATE_CANCELED = 3;
    public static final int RESEARCH_STATE_HAVE_STARTED = 4;

    public static final int RESEARCH_CONFIRM_TYPE_SYSTEM = 1;
    public static final int RESEARCH_CONFIRM_TYPE_APP = 2;
    public static final int RESEARCH_CONFIRM_TYPE_ERROR = -1;
    public static final int RESEARCH_CONFIRM_TYPE_APP_MISTAKE = 3;
    public static final int RESEARCH_CONFIRM_TYPE_EMPTY = 4;

    /**
     * 市调执行中
     */
    public static final int BILL_RESEARCH_STARTED = 0;

    /**
     * 市调被手机app确认
     */
    public static final int BILL_RESEARCH_PHONE = 1;

    /**
     * 市调被ERP取消
     */
    public static final int BILL_RESEARCH_CANCEL_BY_ERP = 2;

    /**
     * 市调被ERP回传
     */
    public static final int BILL_RESEARCH_CONFIRMED_BY_ERP = 3;

    /**
     * 市调数据为空的情况
     */
    public static final int BILL_RESEARCH_EMPTY = 4;

    /**
     * 市调被手机app误确认
     */
    public static final int BILL_RESEARCH_PHONE_MISTAKE = 5;

    public static final int MISS_TAG_NOT_MISS = 0;
    public static final int MISS_TAG_MISS = 1;

    public static final String TOKEN = "weiphp";

    public static final String APP_ID = "wx2eec10e84259d4f9";
    public static final String APP_SECRET_KEY = "237e76067bf76223ad18940e4036dc82";
    public static final String ENCODING_AES_KEY = "3aAdCcbkSuz9Jw32pAsaUjvU0UALLivMxRtzBMmMFOk";

    /**
     * 日志状态——ERP同步
     */
    public static final int LOG_TYPE_SYNC = 0;

    /**
     * 日志状态——刷新市调状态
     */
    public static final int LOG_TYPE_STATE = 1;

    /**
     * 日志结果——成功
     */
    public static final int LOG_RESULT_SUCCESS = 0;

    /**
     * 日志结果——失败
     */
    public static final int LOG_RESULT_FAILED = 1;
}
