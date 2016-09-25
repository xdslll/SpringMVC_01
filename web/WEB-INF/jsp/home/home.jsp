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
<div class="easyui-layout" id="main">
    <div data-options="region:'north',border:false" id="north">
        <ul>
            <li>
                <div id="north_title_div">
                    <span id="north_title_span">${product_name}</span>
                </div>
            </li>
            <li>
                <div id="north_username_div">
                    <span>${user.name}</span>
                    <% if(request.getSession().getAttribute("user") != null) { %>
                        <a id="change_pwd" style="text-decoration:underline;cursor:pointer;">修改密码</a>
                        <a href='<%=request.getContextPath()%>/logout'>退出登录</a>
                    <% } %>
                </div>
            </li>
        </ul>
    </div>
    <div data-options="region:'west',split:true" id="west" title="菜单">
        <ul id="menu" class="easyui-tree" data-options="
            <c:if test="${user.roleId==0}">
                url:'<%=request.getContextPath()%>/menu/get',
            </c:if>
            <c:if test="${user.roleId!=0}">
                url:'<%=request.getContextPath()%>/role_menu/get?roleId=' + ${user.roleId},
            </c:if>
            method:'get',
            animate:true
            ">
        </ul>
    </div>
    <div data-options="region:'center'" id="center" >
        <iframe id="main_frame" style="height:100%;width:100%" scrolling="no" frameborder="0"></iframe>
    </div>
    <div data-options="region:'south', border:false" id="south">
        <div id="south_title_div">
            <span id="south_title_span">${foot_info}</span>
        </div>
    </div>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">修改密码</div>
    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label>原密码:</label>
            <input name="origin_pwd" class="easyui-textbox" required="true" type="password">
        </div>
        <div class="fitem">
            <label>新密码:</label>
            <input name="new_pwd" class="easyui-textbox" required="true" type="password">
        </div>
        <div class="fitem">
            <label>确认密码:</label>
            <input name="confirm_pwd" class="easyui-textbox" required="true" type="password">
        </div>
        <div class="fitem">
            <input type="password" style="display:none">
        </div>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="save()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
</div>
<script>
    $('#menu').tree({
        onClick: function(node) {
            var url = node.url;
            var startIndex = url.indexOf('http://');
            if (startIndex != 0) {
                $("#main_frame").attr('src', '<%=request.getContextPath()%>' + url);
            } else {
                $("#main_frame").attr('src', url);
            }
        }
    });
    $('#change_pwd').click(function(){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','修改密码');
        $('#fm').form('clear');
        url = '<%=request.getContextPath()%>/user/changePwd?userId=' + ${user.id};
    })

    function save() {
        $('#fm').form('submit',{
            url: url,
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.code != '0'){
                    $.messager.alert('修改失败', result.msg, 'error');
                } else {
                    $.messager.alert('修改成功', result.msg, 'info');
                    $('#dlg').dialog('close');        // close the dialog
                }
            }
        });
        //alert($("input[name='origin_pwd']").val() + "," + $("input[name='new_pwd']").val() + "," + $("input[name='confirm_pwd']").val());
    }
</script>
</body>
</html>