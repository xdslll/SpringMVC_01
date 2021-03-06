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
    请选择月份:<input id="dt" class="easyui-datebox" label="请选择月份" labelPosition="top"
                 data-options="formatter:myformatter,parser:myparser,buttons:buttons,onSelect:onSelect">
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
                singleSelect: true,
                nowrap: false
                ">
        <thead>
            <tr>
                <th data-options="field:'name'" width="10%">对应区域</th>
                <th data-options="field:'countByMonth'" width="8%">当月累计(次)</th>
                <th data-options="field:'codCountByMonth'" width="9%">当月需调研(件)</th>
                <th data-options="field:'finishByMonth'" width="9%">当月已调研(件)</th>
                <th data-options="field:'notFinishByMonth'" width="9%">当月未调研(件)</th>
                <th data-options="field:'finishPercentByMonth'" width="8%">月完成百分比</th>
                <th data-options="field:'countByYear'" width="8%">年度累计(次)</th>
                <th data-options="field:'codCountByYear'" width="9%">年度需调研(件)</th>
                <th data-options="field:'finishByYear'" width="9%">年度已调研(件)</th>
                <th data-options="field:'notFinishByYear'" width="9%">年度未调研(件)</th>
                <th data-options="field:'finishPercentByYear'" width="8%">年完成百分比</th>
                <th data-options="field:'cci'" width="5%">CCI</th>
                <th data-options="field:'cost'" width="5%">投入</th>
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
            <th data-options="field:'code'" width="8%">门店编码</th>
            <th data-options="field:'name'" width="8%">门店名称</th>
            <th data-options="field:'areaName'" width="8%">所属区域</th>
            <th data-options="field:'countByMonth'" width="7%">当月累计</th>
            <th data-options="field:'codCountByMonth'" width="7%">当月需调研</th>
            <th data-options="field:'finishByMonth'" width="7%">当月已调研</th>
            <th data-options="field:'notFinishByMonth'" width="7%">当月未调研</th>
            <th data-options="field:'finishPercentByMonth'" width="8%">月完成百分比</th>
            <th data-options="field:'countByYear'" width="7%">年度累计</th>
            <th data-options="field:'codCountByYear'" width="7%">年度需调研</th>
            <th data-options="field:'finishByYear'" width="7%">年度已调研</th>
            <th data-options="field:'notFinishByYear'" width="7%">年度未调研</th>
            <th data-options="field:'finishPercentByYear'" width="8%">年完成百分比</th>
            <th data-options="field:'cci'" width="5%">CCI</th>
            <th data-options="field:'cost'" width="5%">投入</th>
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
            queryParams.year = year;
            queryParams.month = month;
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
            queryParams.year = year;
            queryParams.month = month;
            dg.datagrid('options').queryParams = queryParams;
            dg.datagrid('reload');
            dg.datagrid('clearSelections');
        }
    }

    function myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        return y+'-'+(m<10?('0'+m):m);
    }

    function myparser(s){
        if (!s) return new Date();
        var ss = (s.split('-'));
        var y = parseInt(ss[0],10);
        var m = parseInt(ss[1],10);
        if (!isNaN(y) && !isNaN(m)){
            return new Date(y,m-1);
        } else {
            return new Date();
        }
    }

    var buttons = $.extend([], $.fn.datebox.defaults.buttons);
    buttons.splice(1, 0, {
        text: 'Clear',
        handler: function(target){
            $('#dt').combo('setText','');
            var dg = $('#dg');
            //设置datagrid请求参数
            var queryParams = dg.datagrid('options').queryParams;
            queryParams.year = '';
            queryParams.month = '';
            dg.datagrid('options').queryParams = queryParams;
            dg.datagrid('reload');
            dg.datagrid('clearSelections');
        }
    });

    var year, month;

    function onSelect(date){
        //$('#dt').text(date);
        year = date.getFullYear();
        month = date.getMonth() + 1;
        var dg = $('#dg');
        //设置datagrid请求参数
        var queryParams = dg.datagrid('options').queryParams;
        queryParams.year = year;
        queryParams.month = month;
        dg.datagrid('options').queryParams = queryParams;
        dg.datagrid('reload');
        dg.datagrid('clearSelections');
    }
</script>
</body>
</html>
