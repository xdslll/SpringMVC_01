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
<div id="user-toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="newDlg()">新增</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain="true" onclick="editDlg()">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true" onclick="removeDlg()">删除</a>
</div>
<table id="dg" title="用户列表" class="easyui-datagrid" style="width:90%;height:500px;"
       data-options="
                url: '<%=request.getContextPath()%>/user/get',
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
                pageSize: 15,
                pageList: [10, 15, 30],
                toolbar: '#toolbar',
                border: false,
                fit: true,
                nowrap: false,
                striped: true,
                toolbar: '#user-toolbar'
                ">
    <thead>
    <tr>
        <th data-options="field:'username'" width="20%">用户名</th>
        <th data-options="field:'name'" width="20%">姓名</th>
        <th data-options="field:'roleName'" width="20%">角色</th>
        <th data-options="field:'deptName'" width="20%">门店</th>
        <th data-options="field:'resCount'" width="20%">上报数据量</th>
    </tr>
    </thead>
</table>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">填写用户信息</div>
    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label>用户名:</label>
            <input name="username" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>密码:</label>
            <input name="password" class="easyui-textbox" required="true" type="password">
        </div>
        <div class="fitem">
            <label>姓名:</label>
            <input name="name" class="easyui-textbox">
        </div>
        <div class="fitem">
            <label>角色:</label>
            <input name="roleName" class="easyui-textbox" style="width: 90px;" id="role_name" editable="false">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onClick="showSelectRole();" style="margin-left:10px;">选择</a>
        </div>
        <div class="fitem">
            <label>门店:</label>
            <input name="deptName" class="easyui-textbox" style="width: 90px;" id="dept_name" editable="false">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onClick="showSelectDept();" style="margin-left:10px;">选择</a>
        </div>
        <div class="fitem">
            <label>邮箱:</label>
            <input name="email" class="easyui-textbox">
        </div>
        <div class="fitem">
            <input name="id" type="hidden">
            <input name="roleId" type="hidden" id="role_id">
            <input name="deptId" type="hidden" id="dept_id">
        </div>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="save()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
</div>
<!=============================
    展示权限窗口
==============================!>
<div id="dlg_select_role" class="easyui-dialog" style="width:400px;height:280px;"
     closed="true" buttons="#dlg_select_role_buttons">
    <table id="dg_select_role" class="easyui-datagrid" style="width:100%;height:100%;"
           data-options="
            url: '<%=request.getContextPath()%>/role/get',
            method: 'get',
            idField: 'id',
            showFooter: true,
            fitColumns: true,
            collapsible: false,
            animate: true,
            rownumbers: true,
            iconCls: 'icon-ok',
            singleSelect: true,
            toolbar: '#dlg_select_role_buttons',
            border: false,
            fit: true,
            nowrap: false,
            striped: true,
            ">
        <thead>
        <tr>
            <th data-options="field:'id'" width="50%">权限ID</th>
            <th data-options="field:'name'" width="50%">权限名称</th>
        </tr>
        </thead>
    </table>
</div>
<div id="dlg_select_role_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="selectRole()" style="width:90px">选择</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_select_role').dialog('close')" style="width:90px">取消</a>
</div>
<!=============================
    展示门店窗口
==============================!>
<div id="dlg_select_dept" class="easyui-dialog" style="width:400px;height:280px;"
     closed="true" buttons="#dlg_select_dept_buttons">
    <table id="dg_select_dept" class="easyui-datagrid" style="width:100%;height:100%;"
           data-options="
            url: '<%=request.getContextPath()%>/dept/get2',
            method: 'get',
            idField: 'id',
            showFooter: true,
            fitColumns: true,
            collapsible: false,
            animate: true,
            rownumbers: true,
            iconCls: 'icon-ok',
            singleSelect: true,
            toolbar: '#dlg_select_role_buttons',
            border: false,
            fit: true,
            nowrap: false,
            striped: true,
            ">
        <thead>
        <tr>
            <th data-options="field:'code'" width="33%">门店编码</th>
            <th data-options="field:'name'" width="33%">门店名称</th>
            <th data-options="field:'areaName'" width="33%">区域名称</th>
        </tr>
        </thead>
    </table>
</div>
<div id="dlg_select_dept_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="selectDept()" style="width:90px">选择</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_select_dept').dialog('close')" style="width:90px">取消</a>
</div>
<script>
    var url;

    function selectDept() {
        var row = $('#dg_select_dept').datagrid('getSelected');
        if (row) {
            var deptId = row.id;
            var deptName = row.name;
            $("#dept_name").textbox('setValue', deptName);
            $("#dept_id").val(deptId);
            $('#dlg_select_dept').dialog('close');
        }
    }

    //显示选择父结点的对话框
    function showSelectDept() {
        $('#dlg_select_dept').dialog('open').dialog('center').dialog('setTitle','选择账号对应的门店');
    }

    function selectRole() {
        var row = $('#dg_select_role').datagrid('getSelected');
        if (row) {
            var roleId = row.id;
            var roleName = row.name;
            $("#role_name").textbox('setValue', roleName);
            $("#role_id").val(roleId);
            $('#dlg_select_role').dialog('close');
        }
    }

    //显示选择父结点的对话框
    function showSelectRole() {
        $('#dlg_select_role').dialog('open').dialog('center').dialog('setTitle','选择账号对应的角色');
    }

    //显示新增菜单窗口
    function newDlg() {
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增用户');
        $('#fm').form('clear');
        url = '<%=request.getContextPath()%>/user/add';
    }

    //显示编辑菜单窗口
    function editDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑用户');
            $('#fm').form('clear').form('load',row);
            url = '<%=request.getContextPath()%>/user/update';
        }
    }

    //删除菜单
    function removeDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确定','您确定删除该用户吗?',function(r){
                if (r){
                    var url = "<%=request.getContextPath()%>/user/delete?userId=" + row.id;
                    $.get(url, function(result) {
                        result = eval('('+result+')');
                        if (result.code != '0') {
                            $.messager.alert('操作失败', result.msg, 'error');
                        } else {
                            $('#dg').datagrid('reload');
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
                    $('#dg').datagrid('reload');    // reload the user data
                }
            }
        });
    }

</script>
</body>
</html>
