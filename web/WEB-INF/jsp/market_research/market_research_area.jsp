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
    <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_go'" plain="true" onclick="exportReserch()">导出</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_get'" plain="true" onclick="sync()">同步</a>--%>
    <span plain="true">状态: </span><select name="state" label="状态:" labelPosition="top" onchange="selectState(this.options[this.options.selectedIndex].value)">
        <option value="-1">全部</option>
        <option value="0">未开始</option>
        <%--<option value="1">已确认</option>--%>
        <option value="2">已结束</option>
        <%--<option value="3">已撤销</option>--%>
        <option value="4" selected>已开始</option>
    </select>
    <input class="easyui-textbox" data-options="prompt:'请输入门店编号或门店名称'" style="width:25%;height:28px;" id="search_name">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchMarketResearch()">搜索</a>
</div>
<div class="main_table">
    <table id="dg" title="市调清单列表" class="easyui-treegrid"
           data-options="
                url: '<%=request.getContextPath()%>/area/dept/list',
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
                pagination: true
                ">
        <thead>
            <tr>
                <th data-options="field:'name'" width="20%">市调名称</th>
                <th data-options="field:'billNumber'" width="10%">市调单号</th>
                <th data-options="field:'startDt'" width="20%">开始时间</th>
                <th data-options="field:'endDt'" width="20%">结束时间</th>
                <th data-options="field:'stateDesp'" width="10%">执行状态</th>
                <th data-options="field:'confirmDesp'" width="10%">确认情况</th>
                <th data-options="field:'finishPercent'" width="10%">完成百分比</th>
            </tr>
        </thead>
    </table>
</div>
<input id="contextpath" type="hidden" value="<%=request.getContextPath()%>">

<script>
    function searchMarketResearch() {
        var dg = $('#dg');
        var keyword = $("#search_name").val();
        //设置关键字
        var queryParams = dg.treegrid('options').queryParams;
        queryParams.keyword = encodeURI(keyword);
        dg.treegrid('options').queryParams = queryParams;
        dg.treegrid('reload');
    }

    function selectState(v) {
        var dg = $('#dg');
        var param = dg.treegrid('options').queryParams;
        param.state = v;
        dg.treegrid('options').queryParams = param;
        dg.treegrid('reload');
    }

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

    function sync() {
        url = $("#contextpath").val() + '/market_research/sync';
        $.ajax({
            type: 'get',
            url: url,
            beforeSend: function() {
                $.messager.progress({
                    title:'请稍候',
                    msg:'正在同步市调清单,请稍候...'
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
        });
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
