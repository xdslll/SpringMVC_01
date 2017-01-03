<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if(request.getSession().getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/IconExtension.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/layout.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/layout.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
</head>
<body>
<!=============================
    市调清单列表
==============================!>
<div id="toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="showDeptInfo()">查看部门信息</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="showCatInfo()">查看分类信息</a>
</div>
<div class="main_table">
    <table id="dg" title="区域市调统计" class="easyui-datagrid"
           data-options="
                url: '<%=request.getContextPath()%>/area/stats',
                method: 'get',
                idField: 'id',
                treeField: 'name',
                showFooter: true,
                fitColumns: true,
                collapsible: false,
                animate: true,
                rownumbers: false,
                iconCls: 'icon-ok',
                fit: true,
                toolbar: '#toolbar',
                pageSize: 10,
                pageList: [10, 15, 20],
                pagination: false,
                singleSelect: true
                ">
        <thead>
            <tr>
                <th data-options="field:'name'" width="15%">对应区域</th>
                <th data-options="field:'countByMonth'" width="15%">当月档期</th>
                <th data-options="field:'finishPercentByMonth'" width="15%">月度完成百分比</th>
                <th data-options="field:'countByYear'" width="15%">年度累计</th>
                <th data-options="field:'finishPercentByYear'" width="15%">年度完成百分比</th>
                <th data-options="field:'cci'" width="15%">CCI</th>
                <th data-options="field:'cost'" width="15%">投入</th>
            </tr>
        </thead>
    </table>
</div>

<!=============================
    查看部门信息
==============================!>
<div id="dept" class="easyui-dialog" style="width: 90%;height: 550px;"
     closed="true" buttons="#dept-buttons">
    <table id="dept-dg" class="easyui-datagrid" style="width: 100%"
           data-options="
            url: '<%=request.getContextPath()%>/area/stats/dept',
            method: 'get',
            idField: 'id',
            showFooter: true,
            fitColumns: true,
            collapsible: false,
            animate: true,
            rownumbers: true,
            iconCls: 'icon-ok',
            singleSelect: true,
            toolbar: '#dept-toolbar',
            nowrap: false,
            striped: true,
            border: false,
            fit: true
         ">
        <thead><tr>
            <th data-options="field:'code'" width="11%">门店编码</th>
            <th data-options="field:'name'" width="11%">门店名称</th>
            <th data-options="field:'areaName'" width="11%">所属区域</th>
            <th data-options="field:'countByMonth'" width="11%">当月档期</th>
            <th data-options="field:'finishPercentByMonth'" width="11%">月度完成百分比</th>
            <th data-options="field:'countByYear'" width="11%">年度累计</th>
            <th data-options="field:'finishPercentByYear'" width="11%">年度完成百分比</th>
            <th data-options="field:'cci'" width="11%">CCI</th>
            <th data-options="field:'cost'" width="11%">投入</th>
        </tr></thead>
    </table>
</div>
<div id="dept-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dept').dialog('close')" style="width:90px">关闭</a>
</div>
<!=============================
    分类市调统计
==============================!>
<div id="cat" class="easyui-dialog" style="width: 90%;height: 550px;"
     closed="true" buttons="#cat-buttons">
    <table id="cat-dg" class="easyui-datagrid" style="width: 100%"
           data-options="
            url: '<%=request.getContextPath()%>/area/stats/cat',
            method: 'get',
            idField: 'id',
            showFooter: true,
            fitColumns: true,
            collapsible: false,
            animate: true,
            rownumbers: true,
            iconCls: 'icon-ok',
            singleSelect: true,
            toolbar: '#cat-toolbar',
            nowrap: false,
            striped: true,
            border: false,
            fit: true
         ">
        <thead><tr>
            <th data-options="field:'parentCode'" width="12%">父分类编号</th>
            <th data-options="field:'parentName'" width="12%">父分类名称</th>
            <th data-options="field:'code'" width="12%">分类编号</th>
            <th data-options="field:'name'" width="12%">分类名称</th>
            <th data-options="field:'codCount'" width="12%">月度商品总数</th>
            <th data-options="field:'finishCountByMonth'" width="12%">月度完成进度</th>
            <th data-options="field:'codCountByYear'" width="12%">年度商品总数</th>
            <th data-options="field:'finishCountByYear'" width="12%">年度完成进度</th>
        </tr></thead>
    </table>
</div>
<div id="cat-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#cat').dialog('close')" style="width:90px">关闭</a>
</div>

<input id="contextpath" type="hidden" value="<%=request.getContextPath()%>">
<script>

    function showDeptInfo() {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            $('#dept').dialog('open').dialog('center').dialog('setTitle', '门店信息统计');
            var dg = $('#dept-dg');
            //设置datagrid请求参数
            var queryParams = dg.datagrid('options').queryParams;
            queryParams.areaId = row.id;
            dg.datagrid('options').queryParams = queryParams;
            dg.datagrid('reload');
            dg.datagrid('clearSelections');
        }
    }

    function showCatInfo() {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            $('#cat').dialog('open').dialog('center').dialog('setTitle', '分类市调统计');
            var dg = $('#cat-dg');
            //设置datagrid请求参数
            var queryParams = dg.datagrid('options').queryParams;
            queryParams.areaId = row.id;
            dg.datagrid('options').queryParams = queryParams;
            dg.datagrid('reload');
            dg.datagrid('clearSelections');
        }
    }
</script>
</body>
</html>
