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
<div style="margin:5px 5px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="newDlg()">导入</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="removeDlg()">删除</a>
</div>
<table id="dg" title="导入历史" class="easyui-datagrid" style="width:100%;height:500px;"
       data-options="
                url: '<%=request.getContextPath()%>/import/get',
                method: 'get',
                idField: 'id',
                showFooter: true,
                fitColumns: true,
                collapsible: false,
                animate: true,
                rownumbers: true,
                iconCls: 'icon-ok',
                pagination: false,
                singleSelect: true,
                ">
    <thead>
    <tr>
        <th data-options="field:'fileName'" width="20%">文件名</th>
        <th data-options="field:'size'" width="20%">文件大小</th>
        <th data-options="field:'createBy'" width="20%">创建人</th>
        <th data-options="field:'createDate'" width="20%">创建时间</th>
        <th data-options="field:'state'" width="20%">状态</th>
    </tr>
    </thead>
</table>
<div id="upload" class="easyui-dialog" style="width:400px;height:140px;padding:10px 20px" closed="true" buttons="#dlg-upButtons">
    <form id="uploadfrm" method="post" enctype="multipart/form-data">
        <div class="fitem">
            <label>上传文件:</label>
            <input type="file" name="textFile"  required="true">
        </div>
        <div id="dlg-upButtons">
            <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="upload()" style="width:90px">导入</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#upload').dialog('close')" style="width:90px">取消</a>
        </div>
    </form>
</div>

<script>
    var url;

    //显示新增菜单窗口
    function newDlg() {
        $("#upload").dialog('open').dialog('center').dialog('setTitle','导入文件');
        $('#uploadfrm').form('clear');
    }

    function upload() {

    }

    //显示编辑菜单窗口
    function editDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑机构');
            $('#fm').form('clear').form('load',row);
            url = '<%=request.getContextPath()%>/org/update';
        }
    }

    //删除菜单
    function removeDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确定','您确定删除该机构吗?',function(r){
                if (r){
                    var url = "<%=request.getContextPath()%>/org/delete?orgId=" + row.id;
                    $.get(url, function(result) {
                        var result = eval('('+result+')');
                        if (result.errorMsg) {
                            $.messager.alert('操作失败', result, 'error');
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
                if (result.errorMsg){
                    $.messager.alert('操作失败', result.errorMsg, 'error');
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
