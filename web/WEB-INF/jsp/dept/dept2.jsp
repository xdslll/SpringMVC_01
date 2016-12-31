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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/IconExtension.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/layout.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/layout.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
</head>
<body>
<div id="toolbar">
    <input class="easyui-textbox" data-options="prompt:'请输入门店名称或编号'" style="width:25%;height:28px;" id="search_name">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="searchArea()"></a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="newDlg()">新增</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="editDlg()">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="removeDlg()">删除</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-20130406011050744_easyicon_net_16'" onclick="generateDlg()">生成员工账户</a>
</div>
<!=============================
    门店信息列表
==============================!>
<div class="main_table">
    <table id="dg" title="门店列表" class="easyui-datagrid"
           data-options="
                    url: '<%=request.getContextPath()%>/dept/get',
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
                    ">
        <thead>
        <tr>
            <th data-options="field:'code'" width="20%">门店编码</th>
            <th data-options="field:'name'" width="20%">门店名称</th>
            <th data-options="field:'cityName'" width="20%">所在城市</th>
            <th data-options="field:'compName'" width="20%">竞争门店</th>
            <th data-options="field:'areaName'" width="20%">所属区域</th>
        </tr>
        </thead>
    </table>
</div>

<!=============================
    新增门店信息
==============================!>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">填写区域信息</div>
    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label>门店编码:</label>
            <input name="code" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>门店名称:</label>
            <input name="name" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>所在城市:</label>
            <input name="cityName" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>竞争门店:</label>
            <input name="compName" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <input name="id" type="hidden">
        </div>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="save()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
</div>
<!=============================
    自动生成门店下的用户信息
==============================!>
<div id="dlg2" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons2">
    <div class="ftitle">填写区域信息</div>
    <form id="fm2" method="post" novalidate>
        <div class="fitem">
            <label>角色:</label>
            <input name="roleId" class="easyui-textbox" style="width: 90px;" id="role_id" editable="false">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onClick="showSelectRole();" style="margin-left:10px;">选择</a>
        </div>
        <div class="fitem">
            <label>账号数量:</label>
            <input name="num" class="easyui-textbox" style="width: 90px;" id="num" editable="true">
        </div>
        <div class="fitem">
            <input name="deptId" type="hidden">
        </div>
    </form>
</div>
<div id="dlg-buttons2">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="generateUser()" style="width:90px">生成</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg2').dialog('close')" style="width:90px">取消</a>
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

<script>
    var url;

    function selectRole() {
        var row = $('#dg_select_role').datagrid('getSelected');
        if (row) {
            var roleId = row.id;
            $("#role_id").textbox('setValue', roleId);
            $('#dlg_select_role').dialog('close');
        }
    }

    //显示选择父结点的对话框
    function showSelectRole() {
        $('#dlg_select_role').dialog('open').dialog('center').dialog('setTitle','选择账号对应的角色');
    }

    //展示生成用户对话框
    function generateDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            var deptId = row.id;
            $('#dlg2').dialog('open').dialog('center').dialog('setTitle', '生成门店账号');
            $('#fm2').form('clear').form('load', row);
            url = '<%=request.getContextPath()%>/dept/user/generate?deptId=' + deptId;
        }
    }

    //执行生成用户的请求
    function generateUser() {
        $('#fm2').form('submit',{
            url: url,
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                result = eval('('+result+')');
                if (result.code != '0'){
                    $.messager.alert('操作失败', result.msg, 'error');
                } else {
                    $.messager.alert('操作成功', result.msg, 'info');
                    $('#dlg2').dialog('close');        // close the dialog
                    $('#dg').datagrid('reload');    // reload the user data
                }
            }
        });
    }

    //显示新增菜单窗口
    function newDlg() {
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增门店');
        $('#fm').form('clear');
        url = '<%=request.getContextPath()%>/dept/add';
    }

    //显示编辑菜单窗口
    function editDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑门店');
            $('#fm').form('clear').form('load',row);
            url = '<%=request.getContextPath()%>/dept/update';
        }
    }

    //删除菜单
    function removeDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确定','您确定删除该门店吗?',function(r){
                if (r){
                    var url = "<%=request.getContextPath()%>/dept/delete?id=" + row.id;
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

    function searchArea() {
        var dg = $('#dg');
        //设置关键字
        var queryParams = dg.datagrid('options').queryParams;
        queryParams.name = encodeURI($("#search_name").val());
        dg.datagrid('options').queryParams = queryParams;
        //设置起始页数
        var options = dg.datagrid('getPager').data("pagination").options;
        options.pageNumber = 1;
        dg.datagrid('reload');
    }

</script>
</body>
</html>
