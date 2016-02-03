package com.demo.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ErrorMsg
{

    public static final Logger logger = Logger.getLogger(ErrorMsg.class);

    public static final String MSG_QUERYALLCLASSIFY_SUCCESS = "获取全部分类成功";

    public static final String MSG_QUERYALLCLASSIFY_FAILED = "获取全部分类失败";

    public static final String MSG_SEARCH_EBOOKLIST_SUCCESS = "查询图书列表成功";

    public static final String MSG_SEARCH_EBOOKLIST_FAILED = "查询图书列表失败";

    public static final String MSG_QUERY_SUBTOPIC_LIST_SUCCESS = "查询子专题成功";

    public static final String MSG_QUERY_SUBTOPIC_LIST_FAILED = "查询子专题失败";

    public static final String MSG_SEARCHMATCH_SUCCESS = "搜索匹配成功";

    public static final String MSG_SEARCHMATCH_FAILED = "搜索匹配失败";

    public static final String MSG_LIKE_SUCCESS = "点赞成功";

    public static final String MSG_LIKE_FAILED = "点赞失败";

    public static final String MSG_READEBOOK_SUCCESS = "已阅图书成功";

    public static final String MSG_READEBOOK_FAILED = "已阅图书获取失败";

    public static final String MSG_SEARCH_CONTENTS_SUCCESS = "查询目录成功";

    public static final String MSG_SEARCH_CONTENTS_FAILED = "查询目录失败";

    public static final String MSG_QUERY_HOTTAG_SUCCESS = "查询最热关键词成功";

    public static final String MSG_QUERY_HOTTAG_FAILED = "查询最热关键词失败";

    public static final String MSG_QUERY_HOTEBOOK_SUCCESS = "查找最热图书成功";

    public static final String MSG_QUERY_HOTEBOOK_FAILED = "查找最热图书失败";

    public static final String MSG_READ_EBOOKS_SUCCESS = "更新读书记录成功";

    public static final String MSG_READ_EBOOKS_FAILED = "更新读书记录失败";

    public static final String MSG_QUERY_RECOMM_TOPIIClIST_SUCCESS = "查找推荐书单成功";

    public static final String MSG_QUERY_RECOMM_TOPIIClIST_FAILED = "查找推荐书单失败";

    public static final String MSG_QUERY_RECOMM_BOOKLIST_SUCCESS = "查询推荐图书成功";

    public static final String MSG_QUERY_RECOMM_BOOKLIST_FAILED = "查询推荐图书失败";

    public static final String MSG_REMOVE_BOOKCARD_SUCCESS = "从书架中移除成功";

    public static final String MSG_REMOVE_BOOKCARD_FAILED = "从书架中移除失败";

    public static final String MSG_LOGINUSER_FAILED = "登录失败";

    public static final String MSG_LOGINUSER_SUCCESS = "登录成功";

    public static final String MSG_QUERYINTERESTLIST_SUCCESS = "获取专题关注列表成功";

    public static final String MSG_QUERYINTERESTLIST_FAILED = "获取专题关注列表失败";

    public static final String MSG_REMOVEINTEREST_SUCCESS = "取消专题关注成功";

    public static final String MSG_REMOVEINTEREST_FAILED = "取消专题关注失败";

    public static final String MSG_QUERYEBOOKREVIEW_SUCCESS = "查询图书评价成功";

    public static final String MSG_QUERYEBOOKREVIEW_FAILED = "查询图书评价失败";

    public static final String MSG_DELETEEBOOKREVIEW_SUCCESS = "删除该书评价成功";

    public static final String MSG_DELETEEBOOKREVIEW_FAILED = "删除该书评价失败";

    public static final String MSG_QUERYEBOOKCARD_SUCCESS = "书架查询成功";

    public static final String MSG_QUERYEBOOKCARD_FAILED = "书架查询失败";

    public static final String MSG_EBBOKRECOMMEND_SUCCESS = "图书推荐成功！";

    public static final String MSG_EBBOKRECOMMEND_FAILED = "图书推荐失败！";

    public static final String MSG_UPDATEUSER_SUCCESS = "更新用户成功";

    public static final String MSG_CREATEUSER_SUCCESS = "创建用户成功";

    public static final String MSG_ADDEBOOKCARD_SUCCESS = "加入书架成功";

    public static final String MSG_ADDEBOOKCARD_FAILED = "加入书架失败";

    public static final String MSG_QUERYSELECTEDTOPIC_SUCCESS = "获取精选书单成功";

    public static final String MSG_QUERYSELECTEDTOPIC_FAILED = "获取精选书单失败";

    public static final String MSG_TOPICDISLIKE_SUCCESS = "专题点赞成功";

    public static final String MSG_TOPICDISLIKE_FAILED = "专题点赞失败";

    public static final String MSG_EBOOKDISLIKE_SUCCESS = "图书点赞成功";

    public static final String MSG_EBOOKDISLIKE_FAILED = "图书点赞失败";

    public static final String MSG_ADDEBOOKCOMMENT_SUCCESS = "增加图书评论成功";

    public static final String MSG_ADDEBOOKCOMMENT_FAILED = "增加图书评论失败";

    public static final String MSG_CREATEUSER_FAILED = "创建用户失败";

    public static final String MSG_UPDATEUSER_FAILED = "更新用户失败";

    public static final String MSG_FETCHAMAZONDATA_SUCCESS = null;

    public static final String MSG_FETCHAMAZONDATA_FAILED = null;

    public static final String MSG_SETVERIFYCODE_SUCCESS = "设置验证码成功";

    public static final String MSG_SETVERIFYCODE_FAILED = "设置验证码失败";

    public static final String MSG_RESETPASSWORD_SUCCESS = "重置密码成功";

    public static final String MSG_RESETPASSWORD_FAILED = "重置密码失败";

    public static final String MSG_CHANGEPASSWORD_FAILED = "修改密码失败";

    public static final String MSG_CHANGEPASSWORD_SUCCESS = "修改密码成功";

    public static final String MSG_CREATE_CARDGROUP_SUCCESS = "创建书签分组成功";

    public static final String MSG_CREATE_CARDGROUP_FAILED = "创建书签分组失败";

    public static final String MSG_DELETE_CARDGROUP_SUCCESS = "删除书架分组成功";

    public static final String MSG_DELETE_CARDGROUP_FAILED = "删除书架分组失败";

    public static final String MSG_ADDCLASSIFY_SUCCESS = "新增分类成功";

    public static final String MSG_CHECK_TOPIC_INTEREST_SUCCESS = "检查兴趣列表成功";

    public static final String MSG_CHECK_TOPIC_INTEREST_FAILED = "检查兴趣列表失败";

    public static final String MSG_UPDATE_CARDGROUP_SUCCESS = "更新书架分组成功";

    public static final String MSG_UPDATE_CARDGROUP_FAILED = "更新书架分组失败";

    public static final String MSG_ADD_BOOKMARK_SUCCESS = "新增标签成功";

    public static final String MSG_ADD_BOOKMARK_FAILED = "新增标签失败";

    public static final String MSG_DELETE_EBOOKMARK_SUCCESS = "删除书签成功";

    public static final String MSG_DELETE_EBOOKMARK_FAILED = "删除书签失败";

    public static final String MSG_UPDATE_READPROGRESS_SUCCESS = "更新阅读进度成功";

    public static final String MSG_UPDATE_READPROGRESS_FAILED = "更新阅读进度失败";

    public static final String MSG_CHANGE_CARDGROUPSORT_SUCCESS = "修改书架分组顺序成功";

    public static final String MSG_CHANGE_CARDGROUPSORT_FAILED = "修改书架分组顺序失败";

    public static final String MSG_CHANGE_EBOOKCARDSORT_SUCCESS = "修改书架中显示顺序成功";

    public static final String MSG_CHANGE_EBOOKCARDSORT_FAILED = "修改书架中显示顺序失败";

    public static final String MSG_DELETEUSER_SUCCESS = "删除用户成功";

    public static final String MSG_DELETEUSER_FAILED = "删除用户失败";

    public static final String MSG_ADDUSER_SUCCESS = "新增用户成功";

    public static final String MSG_ADDUSER_FAILED = "新增用户失败";

    public static final String MSG_DELETEROLE_FAILED = "删除角色失败";

    public static final String MSG_DELETEROLE_SUCCESS = "删除角色成功";

    public static final String MSG_UPDATEROLE_SUCCESS = "更新角色成功";

    public static final String MSG_UPDATEROLE_FAILED = "更新角色失败";

    public static final String MSG_ADDUSERROLE_SUCCESS = "新增角色成功";

    public static final String MSG_ADDUSERROLE_FAILED = "新增角色失败";

    public static final String MSG_PUSHTOPIC_SUCCESS = "推送专题成功";

    public static final String MSG_PUSHTOPIC_FAILED = "推送专题失败";

    public static final String MSG_QUERY_INDEX_CONFIG_SUCCESS = "查询首页配置成功";

    public static final String MSG_QUERY_INDEX_CONFIG_FAILED = "查询首页配置失败 ";

    public static final String MSG_CHANGE_CARDGROUP_SUCCESS = "修改书架成功";

    public static final String MSG_CHANGE_CARDGROUP_FAILED = "修改书架分组失败";

    public static final String MSG_QUERY_APPLIST_SUCCESS = "查询应用配置成功";

    public static final String MSG_QUERY_APPLIST_FAILED = "查询应用配置失败";

    public static final String MSG_ADDAPP_SUCCESS = "创建接口应用成功";

    public static final String MSG_ADDAPP_FAILED = "创建接口应用失败";

    public static final String MSG_OUTER_INVOKE_INTERFACE_SUCCESS = "外部调用接口成功";

    public static final String MSG_OUTER_INVOKE_INTERFACE_FAILED = "外部调用接口失败";

    public static final String MSG_DELETEAPP_FAILED = null;

    public static final String MSG_DELETEAPP_SUCCESS = null;

    public static final String MSG_UPDATEAPP_SUCCESS = null;

    public static final String MSG_UPDATEAPP_FAILED = null;

    public static final String MSG_QUERY_ORGALIST_SUCCESS = "查询机构列表成功";

    public static final String MSG_QUERY_ORGALIST_FAILED = "查询机构列表失败";

    public static final String MSG_QUERY_TOPSEARCHKEY_SUCCESS = "查询热门搜索关键字成功";

    public static final String MSG_QUERY_TOPSEARCHKEY_FAILED = "查询热门搜索关键字失败";

    public static final String MSG_ADDPUBLISH_SUCCESS = "新增发布成功";

    public static final String MSG_ADDPUBLISH_FAILED = "新增发布失败";

    public static final String MSG_UPDATEPUBLISH_SUCCESS = "编辑发布成功";

    public static final String MSG_UPDATEPUBLISH_FAILED = "编辑发布失败";

    public static final String MSG_DELETEPUBLISH_SUCCESS = "删除发布成功";

    public static final String MSG_DELETEPUBLISH_FAILED = "删除发布失败";

    public static final String MSG_CHECK_USERBOOKCARD_SUCCESS = "检查图书是否在书架中成功";
    
    public static final String MSG_CHECK_USERBOOKCARD_FAILED = "检查图书是否在书架中失败";

    public static final String MSG_TRACKUSER_SUCCESS = "记录用户登录信息成功";
    
    public static final String MSG_TRACKUSER_FAILED = "记录用户登录信息失败";

    public static final String MSG_QUERY_GROUPLIST_SUCCESS = "查找机构列表成功";
    
    public static final String MSG_QUERY_GROUPLIST_FAILED = "查找机构列表失败 ";

    public static Properties prop = null;

    public static void init()
    {
        prop = new Properties();
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(ErrorMsg.class.getResource("/").getPath() + "/error.properties");
            prop.load(fis);
        }
        catch (Exception ex)
        {
            logger.error(ex);

        }
        finally
        {
            try
            {
                fis.close();
            }
            catch (Exception ex)
            {
                logger.error(ex);
            }

        }
    }

    public static String getProperty(String key)
    {
        if (prop == null)
        {
            init();
        }
        return prop.getProperty(key);
    }

}
