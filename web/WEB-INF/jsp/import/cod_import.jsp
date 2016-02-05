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
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
</head>
<body>
<div style="margin:5px 5px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-20130406125647919_easyicon_net_16'" onclick="newDlg()">上传</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-20130406011050744_easyicon_net_16'" onclick="importExcel()">导入</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="removeDlg()">删除</a>
    <!--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="testImport()">测试</a>-->
</div>
<table id="dg" title="导入历史" class="easyui-datagrid" style="width:100%;height:500px;"
       data-options="
                url: '<%=request.getContextPath()%>/import/get?type=1',
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
        <th data-options="field:'size'" width="20%">文件大小(KB)</th>
        <th data-options="field:'createUsername'" width="20%">创建人</th>
        <th data-options="field:'createDt'" width="20%">创建时间</th>
        <th data-options="field:'stateDesp'" width="20%">状态</th>
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
            <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="upload()" style="width:90px">上传</a>
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
        $('#uploadfrm').form('submit',{
            url: '<%=request.getContextPath()%>/import/excel?type=1',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(data){
                result =  eval('(' + data + ')');
                if (result.code != '0'){
                    $.messager.alert('操作失败', result.msg, 'error');
                } else {
                    $('#upload').dialog('close');
                    $('#dg').datagrid('reload');
                }
            }
        });
    }

    //执行导入操作
    function importExcel() {
        $.messager.confirm('确定','是否开始导入?',
            function(r) {
                var ifCover = 0;
                if (r) {
                    ifCover = 1;
                } else {
                    ifCover = 0;
                }
                if (ifCover == 1) {
                    var row = $('#dg').datagrid('getSelected');
                    var json = JSON.stringify(row);
                    url = '<%=request.getContextPath()%>/import/do?ifCover=' + ifCover;
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
                    })
                }
        });
    }

    //删除菜单
    function removeDlg() {
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('确定','您确定删除该记录吗?',function(r) {
                if (r){
                    var url = "<%=request.getContextPath()%>/import/delete?id=" + row.id;
                    $.get(url, function(result) {
                        var result = eval('('+result+')');
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

    function testImport() {
        $.get("<%=request.getContextPath()%>/import/test",
            function(r) {
                if (r) {
                    alert(r);
                }
            });
    }

</script>
</body>
</html>
