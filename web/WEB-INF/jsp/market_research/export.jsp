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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/layout.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/layout.js"></script>
</head>
<body>
<div id="toolbar">
    <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="newDlg()">新增</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain="true" onclick="editDlg()">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true" onclick="removeDlg()">删除</a>--%>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain="true" onclick="exportExcel()">导出</a>
</div>
<div class="main_table">
    <table id="dg" title="区域列表" class="easyui-treegrid" style="width:100%;height:500px;"
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
                    toolbar: '#toolbar'
                    ">
        <thead>
            <tr>
                <th data-options="field:'name'" width="50%">名称</th>
                <th data-options="field:'code', align:'left'" width="50%">部门编码</th>
            </tr>
        </thead>
    </table>
</div>
<div id="dlg_select_parent" class="easyui-dialog" style="width:400px;height:280px;"
     closed="true" buttons="#dlg_select_parent_buttons">
    <table id="dlg_select_parent_grid" class="easyui-treegrid" style="width:100%;height:100%;"
            data-options="
            url: '<%=request.getContextPath()%>/menu/get/root',
            method: 'get',
            idField: 'id',
            treeField: 'text',
            showFooter: true,
            fitColumns: true,
            ">
        <thead>
        <tr>
            <th data-options="field:'text', editor:'text'">菜单名称</th>
            <th data-options="field:'id', editor:'numberbox'">菜单ID</th>
        </tr>
        </thead>
    </table>
</div>
<div id="dlg_select_parent_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="selectParent()" style="width:90px">选择</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_select_parent').dialog('close')" style="width:90px">取消</a>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">填写菜单信息</div>
    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label>菜单ID:</label>
            <input name="id" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>菜单名称:</label>
            <input name="text" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>菜单打开状态:</label>
            <select class="easyui-combobox" name="state">
                <option value="open" selected="selected">open</option>
                <option value="closed">closed</option>
            </select>
        </div>
        <div class="fitem">
            <label>父菜单ID:</label>
            <input name="parent" class="easyui-textbox" style="width: 90px;" id="parent_id" editable="false">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onClick="showSelectParent();" style="margin-left:10px;">选择</a>
        </div>
        <div class="fitem">
            <label>菜单URL:</label>
            <input name="url" class="easyui-textbox">
        </div>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="save()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
</div>
<input id="contextpath" type="hidden" value="<%=request.getContextPath()%>">

<script>
    var url;

    //显示新增菜单窗口
    function newDlg() {
        //展示新增对话框
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增菜单');
        //清除表单信息
        $('#fm').form('clear');
        url = '<%=request.getContextPath()%>/menu/add';
        //获取当前选中的行
        var row = $('#dg').treegrid('getSelected');
        //设置父结点ID
        $("#parent_id").textbox('setValue', row.id);
    }

    //显示编辑菜单窗口
    function editDlg() {
        var row = $('#dg').treegrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑菜单');
            $('#fm').form('clear').form('load',row);
            url = '<%=request.getContextPath()%>/menu/update';
        }
    }

    //删除菜单
    function removeDlg() {
        var row = $('#dg').treegrid('getSelected');
        if (row){
            $.messager.confirm('确认','您确定删除该菜单吗?',function(r){
                if (r){
                    var url = "<%=request.getContextPath()%>/menu/delete?menuId=" + row.id;
                    $.get(url, function(result) {
                        var result = eval('('+result+')');
                        if (result.code != '0') {
                            $.messager.alert('操作失败', result, 'error');
                        } else {
                            $('#dg').treegrid('reload');
                        }
                    })
                }
            });
        }
    }

    //保存新增和编辑菜单的结果
    function save() {
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
                    $('#dlg').dialog('close');        // close the dialog
                    $('#dg').treegrid('reload');    // reload the user data
                }
            }
        });
    }

    //显示选择父结点的对话框
    function showSelectParent() {
        $('#dlg_select_parent').dialog('open').dialog('center').dialog('setTitle','选择父节点');
    }

    //选择父结点
    function selectParent() {
        var row = $('#dlg_select_parent_grid').treegrid('getSelected');
        var id = row.id;
        $("#parent_id").textbox('setValue', id);
        $('#dlg_select_parent').dialog('close');
    }

    function exportExcel() {
        var row = $('#dg').treegrid('getSelected');
        var children = row.children;
        if (isNull(children)) {
            //导出单条记录
            var resId = row.resId;
            var deptId = row.exeId;
            alert(resId + "," + deptId);
            //url = $("#contextpath").val() + '/market_research/export?resId='+resId+'&deptId='+deptId;
            //window.location.href = url;
        } else {
            //导出该区域内的所有记录

        }
    }
</script>
</body>
</html>