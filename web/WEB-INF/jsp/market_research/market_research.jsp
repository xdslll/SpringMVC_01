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
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_go'" plain="true" onclick="checkPublish()">发布</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-2012080412301'" plain="true" onclick="checkCallback()">撤回</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_form_magnify'" plain="true" onclick="showDeptDetail()">查看市调门店</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_form_magnify'" plain="true" onclick="showCodDetail()">查看市调商品</a>
</div>
<div class="main_table">
    <table id="dg" title="市调清单列表" class="easyui-datagrid"
           data-options="
                url: '<%=request.getContextPath()%>/market_research/get?status=4',
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
                <th data-options="field:'name'" width="10%">市调名称</th>
                <th data-options="field:'billNumber'" width="10%">市调单号</th>
                <th data-options="field:'remark'" width="15%">备注</th>
                <th data-options="field:'startDt'" width="10%">开始时间</th>
                <th data-options="field:'endDt'" width="10%">结束时间</th>
                <th data-options="field:'confirmDate'" width="10%">确认时间</th>
                <th data-options="field:'createUsername'" width="10%">创建人</th>
                <th data-options="field:'createDt'" width="10%">创建时间</th>
                <th data-options="field:'stateDesp'" width="10%">状态</th>
            </tr>
        </thead>
    </table>
</div>

<!=============================
    市调部门信息
==============================!>
<div id="dept" class="easyui-dialog" style="width: 90%;height: 550px;"
    closed="true" buttons="#dept-buttons">
    <table id="dept-dg" class="easyui-datagrid" style="width: 100%"
         data-options="
            url: '<%=request.getContextPath()%>/market_research/dept/get',
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
            toolbar: '#dept-toolbar',
            nowrap: false,
            striped: true,
            pageSize: 15,
            pageList: [10, 15, 30],
            border: false,
            fit: true
         ">
        <thead><tr>
            <th data-options="field:'resName'" width="30%">市调名称</th>
            <th data-options="field:'exeName'" width="30%">执行门店</th>
            <th data-options="field:'compName'" width="30%">调研门店</th>
            <th data-options="field:'haveFinished'" width="10%">已完成</th>
        </tr></thead>
    </table>
</div>
<div id="dept-toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_osx_go'" plain="true" onclick="exportResearch()">导出</a>
    <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-2012080412301'" plain="true" onclick="checkCallback()">撤回</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_form_magnify'" plain="true" onclick="showDeptDetail()">查看市调门店</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_form_magnify'" plain="true" onclick="showCodDetail()">查看市调商品</a>--%>
</div>
<div id="dept-buttons">
    <%--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="showDept()" style="width:90px">确定</a>--%>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dept').dialog('close')" style="width:90px">关闭</a>
</div>

<!=============================
    市调商品信息
==============================!>
<div id="cod" class="easyui-dialog" style="width: 90%;height: 550px;"
     closed="true" buttons="#cod-buttons">
    <table id="cod-dg" class="easyui-datagrid" style="width: 100%"
           data-options="
            url: '<%=request.getContextPath()%>/market_research/cod/get',
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
            toolbar: '#cod-toolbar',
            nowrap: false,
            striped: true,
            pageSize: 15,
            pageList: [10, 15, 30],
            border: false,
            fit: true
         ">
        <thead><tr>
            <th data-options="field:'resName'" width="15%">市调名称</th>
            <th data-options="field:'codCategory'" width="15%">品类</th>
            <th data-options="field:'codName'" width="15%">品名</th>
            <th data-options="field:'codSize'" width="15%">规格</th>
            <th data-options="field:'codUnit'" width="15%">单位</th>
            <th data-options="field:'codBarCode'" width="25%">条形码</th>
        </tr></thead>
    </table>
</div>
<div id="cod-toolbar">
    <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_go'" plain="true" onclick="checkPublish()">发布</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-2012080412301'" plain="true" onclick="checkCallback()">撤回</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_form_magnify'" plain="true" onclick="showDeptDetail()">查看市调门店</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-application_form_magnify'" plain="true" onclick="showCodDetail()">查看市调商品</a>--%>
</div>
<div id="cod-buttons">
    <%--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="showDept()" style="width:90px">确定</a>--%>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#cod').dialog('close')" style="width:90px">关闭</a>
</div>

<!=============================
    发布市调清单
==============================!>
<div id="publish" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">选择发布时间</div>
    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label>市调名称:</label>
            <input name="name" class="easyui-textbox" editable="false">
        </div>
        <div class="fitem">
            <label>起始时间:</label>
            <input name="startDt" class="easyui-datebox" required="true" editable="false">
        </div>
        <div class="fitem">
            <label>结束时间:</label>
            <input name="endDt" class="easyui-datebox" required="true" editable="false">
        </div>
        <div class="fitem">
            <input name="id" type="hidden">
        </div>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="publish()" style="width:90px">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#publish').dialog('close')" style="width:90px">取消</a>
</div>

<input id="contextpath" type="hidden" value="<%=request.getContextPath()%>">

<script>
    var url;

    /**
     * 判断是否可以发布
     */
    function checkPublish() {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            var startDt = row.startDt;
            var endDt = row.endDt;
            if (isNull(startDt) && isNull(endDt)) {
                showPublishDlg(row);
            } else {
                $.messager.alert("提示", "该市调已经发布!", "info");
            }
        }
    }

    /**
     * 显示发布对话框
     * @param row
     */
    function showPublishDlg(row) {
        $('#publish').dialog('open').dialog('center').dialog('setTitle','发布市调');
        $('#fm').form('clear').form('load',row);
        url = $("#contextpath").val() + '/market_research/publish';
    }

    function checkCallback() {
        $.messager.confirm('确认', '确认撤回市调吗?', function(r) {
            if (r) {
                var row = $('#dg').datagrid('getSelected');
                url = $("#contextpath").val() + '/market_research/callback'
                if (row) {
                    var json = JSON.stringify(row);
                    $.ajax({
                        type: 'post',
                        url: url,
                        data: 'data=' + json,
                        dataType: 'json',
                        beforeSend: function() {
                            ajaxLoading();
                        },
                        success: function(data) {
                            if (data.code != '0') {
                                $.messager.alert('操作失败', data.msg, 'error');
                            } else {
                                $.messager.alert('操作成功', data.msg, 'info');
                            }
                            ajaxLoadEnd();
                            $('#dg').datagrid('reload');
                        }
                    });
                }
            }
        })
    }

    function showDeptDetail() {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            $('#dept').dialog('open').dialog('center').dialog('setTitle', '市调门店信息');
            var dg = $('#dept-dg');
            //设置datagrid请求参数
            var queryParams = dg.datagrid('options').queryParams;
            queryParams.resId = row.id;
            dg.datagrid('options').queryParams = queryParams;
            dg.datagrid('reload');
        }
    }

    function showCodDetail() {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            $('#cod').dialog('open').dialog('center').dialog('setTitle', '市调商品信息');
            var dg = $('#cod-dg');
            //设置datagrid请求参数
            var queryParams = dg.datagrid('options').queryParams;
            queryParams.resId = row.id;
            dg.datagrid('options').queryParams = queryParams;
            dg.datagrid('reload');
        }
    }

    /**
     * 发布市调
     */
    function publish() {
        $('#fm').form('submit',{
            url: url,
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.code != '0'){
                    $.messager.alert('操作失败', result.msg, 'error');
                } else {
                    $('#publish').dialog('close');
                    $('#dg').datagrid('reload');
                }
            }
        });
    }

    function exportResearch() {
        var row = $('#dept-dg').datagrid('getSelected');
        if (row) {
            var resId = row.resId;
            var deptId = row.exeId;
            url = $("#contextpath").val() + '/market_research/export?resId='+resId+'&deptId='+deptId;
            window.location.href = url;
        }
    }
</script>
</body>
</html>
