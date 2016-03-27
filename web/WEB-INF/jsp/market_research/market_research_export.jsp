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
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_go'" plain="true" onclick="exportReserch()">导出</a>
</div>
<div class="main_table">
    <table id="dg" title="市调清单列表" class="easyui-datagrid"
           data-options="
                url: '<%=request.getContextPath()%>/market_research/get',
                method: 'get',
                idField: 'id',
                showFooter: true,
                fitColumns: true,
                collapsible: false,
                animate: true,
                rownumbers: true,
                iconCls: 'icon-ok',
                pagination: true,
                singleSelect: true,
                toolbar: '#toolbar',
                nowrap: false,
                striped: true,
                pageSize: 15,
                pageList: [10, 15, 30],
                border: false,
                fit: true
                ">
        <thead>
            <tr>
                <th data-options="field:'name'" width="30%">市调名称</th>
                <th data-options="field:'startDt'" width="15%">开始时间</th>
                <th data-options="field:'endDt'" width="15%">结束时间</th>
                <th data-options="field:'createUsername'" width="15%">创建人</th>
                <th data-options="field:'createDt'" width="15%">创建时间</th>
                <th data-options="field:'stateDesp'" width="10%">状态</th>
            </tr>
        </thead>
    </table>
</div>
<!=============================
市调部门信息(按区域划分)
==============================!>
<div id="dept" class="easyui-dialog" style="width: 90%;height: 550px;"
     closed="true" buttons="#dept-buttons">
    <table id="dept-dg" title="区域列表" class="easyui-treegrid" style="width:100%;"
           data-options="
                    url: '<%=request.getContextPath()%>/area/get',
                    method: 'get',
                    idField: 'id',
                    treeField: 'name',
                    showFooter: true,
                    fitColumns: true,
                    collapsible: true,
                    animate: true,
                    rownumbers: true,
                    iconCls: 'icon-ok',
                    fit: true,
                    toolbar: '#dept-toolbar'
                    ">
        <thead>
        <tr>
            <th data-options="field:'name'" width="50%">名称</th>
            <th data-options="field:'code', align:'left'" width="50%">部门编码</th>
        </tr>
        </thead>
    </table>
</div>
<div id="dept-toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain="true" onclick="exportExcel()">开始导出</a>
</div>

<input id="contextpath" type="hidden" value="<%=request.getContextPath()%>">
<input id="res_id" type="hidden">
<input id="dept_id" type="hidden">

<script>
    function exportReserch() {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            //记录resId
            $('#res_id').val(row.id);
            $('#dept').dialog('open').dialog('center').dialog('setTitle', '市调门店信息');
            var dg = $('#dept-dg');
            //设置treegrid请求参数
            var queryParams = dg.treegrid('options').queryParams;
            queryParams.resId = row.id;
            dg.treegrid('options').queryParams = queryParams;
            dg.treegrid('reload');
        }
    }

    function exportExcel() {
        var tree = $('#dept-dg');
        var row = tree.treegrid('getSelected');
        var children = tree.treegrid('getChildren', row.id);
        if (children.length == 0) {
            //导出单条记录
            var resId = $('#res_id').val();
            var deptCode = row.code;
            url = $("#contextpath").val() + '/market_research/export?resId='+resId+'&deptCode='+deptCode;
            window.location.href = url;
        } else {
            //导出单条记录
            var resId = $('#res_id').val();
            var areaName = row.name;
            url = $("#contextpath").val() + '/market_research/export_area?resId='+resId+'&areaName='+encodeURI(areaName);
            window.location.href = url;
            /*$.ajax({
                type: 'get',
                url: url,
                beforeSend: function() {
                    $.messager.progress({
                        title:'请稍候',
                        msg:'处理过程时间较长,请耐心等待...'
                    });
                },
                success: function(data) {
                    data = eval('('+data+')');
                    if (data.code != '0') {
                        $.messager.alert('操作失败', data.msg, 'error');
                    } else {
                        $.messager.alert('操作成功', data.msg, 'info');
                    }
                    $.messager.progress('close');
                }
            });*/
        }
        /*if (isNull(children)) {
            //导出单条记录
            var resId = $('#res_id').val();
            var deptCode = row.code;
            alert(resId + "," + deptCode);
            //url = $("#contextpath").val() + '/market_research/export?resId='+resId+'&deptId='+deptId;
            //window.location.href = url;
        } else {
            //导出该区域内的所有记录

        }*/
    }
</script>
</body>
</html>
